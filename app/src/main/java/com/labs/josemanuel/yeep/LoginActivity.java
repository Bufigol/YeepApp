package com.labs.josemanuel.yeep;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    protected TextView mSingUpTextView;
    MenuItem miActionProgressItem;

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
// Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
// Extract the action-view from the menu item
        ProgressBar v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
// Return to finish
        return super.onPrepareOptionsMenu(menu);
    }


    public void showProgressBar() {
        miActionProgressItem.setVisible(true);
    }
    public void hideProgressBar() {
        miActionProgressItem.setVisible(false);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cambiarFont();

        //  hideProgressBar();
        // elimina la barra superior
        //   ActionBar actionBar =getActionBar();
        //  actionBar.hide();
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Button buttonSend = (Button) findViewById(R.id.loginBtn);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeLogin(v);

            }
        });


        mSingUpTextView = (TextView) findViewById(R.id.signBtn);
        mSingUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
    /**
     * Metodo utilizado para comprobar en primer lugar que el campo correspondiente al usuario y luego
     * el correspondiente a la contraseña no esten vacios. En caso de que alguno de los campos esten
     * vacios se mostrara un cuadro de dialogo informativo, en caso contrarario se intentara iniciar
     * sesión.
     *
     * @see #parseLogin()
     * @see #getUsernameString()
     * @see #getPasswordString()
     *
     */
    public void makeLogin(View view) {
        /* ProgressDialog centrado
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();*/


        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);
        shake.start();
        // Toast No username
        if (getUsernameString().isEmpty()) {
            if(getSupportActionBar() != null){
                getSupportActionBar().hide();
            }
            hideProgressBar();
            Toast toast = Toast.makeText(getApplicationContext(), "Debe introducir un nombre de usuario", Toast.LENGTH_SHORT);
            toast.show();
            //dialog.hide();
        }   // Toast No passLogin
        if (getPasswordString().isEmpty()) {
            if(getSupportActionBar() != null){
                getSupportActionBar().hide();
            }
            hideProgressBar();
            Toast toast = Toast.makeText(getApplicationContext(), "Debe introducir su contraseña", Toast.LENGTH_SHORT);
            toast.show();
            //dialog.hide();
        } else {
            if(getSupportActionBar() != null){
                getSupportActionBar().show();
            }
            showProgressBar();
            parseLogin();
        }

    }
    /**
     * Método que realiza el inicio de sesión y realiza un intent en caso que los campos esten correctos.
     * Este metodo es invocado exclusivamente cuando se ha comprobado de que los campos no estan vacios.
     * Una vez realizada dicha comprobación se llama ha este metodo para comparar la información
     * ingresada por el usuario con la base de datos albergada en el backend alojado en parse.
     * En caso de que este completamente correcto se realiza el intetn, en caso contrario se mostrara
     * un cuadro de dialogo informando del error.
     * @see #makeLogin(View view)
     */
    private void parseLogin() {
        ParseUser.logInInBackground(getUsernameString(), getPasswordString(),
                new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // If user exist and authenticated, send user to Welcome.class
                            Intent intent = new Intent(
                                    LoginActivity.this,
                                    MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),
                                    "Successfully Logged in",
                                    Toast.LENGTH_LONG).show();
                            LoginActivity.this.finish();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Error, ingrese de nuevo sus datos", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
    }
    /**
     * Metodo privado para obtener la información albergada en el espacio para el ingreso de la
     * contraseña.
     * @return string ingresado por el usuario.
     */
    @NonNull
    private String getPasswordString() {
        EditText password = (EditText) findViewById(R.id.passwordField);
        return password.getText().toString();
    }
    /**
     * Metodo privado para obtener la información albergada en el espacio para el ingreso del nombre
     * de usuario.
     * @return string ingresado por el usuario.
     */
    @NonNull
    private String getUsernameString() {
        EditText username = (EditText) findViewById(R.id.userFieldSign);
        return username.getText().toString();
    }

    /**
     * Metodo Utilizado para establecer un nuevo tipo de letra en el titulo y subtitulo existentes
     * en el layout del splash screen.
     */
    private void cambiarFont() {
        TextView myTitle = (TextView)findViewById(R.id.Title);
        TextView mySubtitle = (TextView)findViewById(R.id.subTitle);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Gagalin-Regular.otf");
        myTitle.setTypeface(myFont);
        mySubtitle.setTypeface(myFont);
    }
}