package com.labs.josemanuel.yeep;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
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

      //  hideProgressBar();
        // elimina la barra superior
     //   ActionBar actionBar =getActionBar();
      //  actionBar.hide();
         getSupportActionBar().hide();

        Button buttonSend = (Button) findViewById(R.id.loginBtn);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUsuario(v);

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

    public void loginUsuario(View view) {

        /* ProgressDialog centrado
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();*/

        // instanciación de los componentes y almacenamiento del ingreso
        EditText userField = (EditText) findViewById(R.id.userFieldSign);
        final String usernameLogin = userField.getText().toString();
        EditText passField = (EditText) findViewById(R.id.passwordField);
        final String passLogin = passField.getText().toString();


        //Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        //view.startAnimation(shake);

            // Toast No username
        if (usernameLogin.isEmpty()) {
            getSupportActionBar().hide();
            hideProgressBar();
            Toast toast = Toast.makeText(getApplicationContext(), "Debe introducir un nombre de usuario", Toast.LENGTH_SHORT);
            toast.show();
            //dialog.hide();
        }   // Toast No passLogin
        if (passLogin.isEmpty()) {
            getSupportActionBar().hide();
            hideProgressBar();
            Toast toast = Toast.makeText(getApplicationContext(), "Debe introducir su contraseña", Toast.LENGTH_SHORT);
            toast.show();
            //dialog.hide();
        } else {
            getSupportActionBar().show();
            showProgressBar();
            ParseUser.logInInBackground(usernameLogin, passLogin, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {

                    if (user != null) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish(); // cerramos login al salir del layout
                        // Hooray! The user is logged in.
                    } else {
                        // Signup failed. Look at the ParseException to see what happened.
                        Toast toast = Toast.makeText(getApplicationContext(), "Error, ingrese de nuevo sus datos", Toast.LENGTH_SHORT);
                        toast.show();
                        //dialog.hide();
                    }

                }
            });
        }

    }

}
