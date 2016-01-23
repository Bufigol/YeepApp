package com.labs.josemanuel.yeep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    final static String TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // elimina la barra superior
        //getSupportActionBar().hide();

        Button loginbutton = (Button) findViewById(R.id.loginBtn);
        loginbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (getUsernameString().matches("")) {
                    Log.i("LoginActivity", "USER EMPTY.");
                    mensajeAlerta("Error al intentar iniciar sesion");
                } else {
                    Log.i("LoginActivity", "USER NOT EMPTY.");
                    if (getPasswordString().matches("")) {
                        Log.i("LoginActivity", "PASS EMPTY.");
                        mensajeAlerta("Error al intentar iniciar sesion");
                    } else {
                        Log.i("LoginActivity", "PASS NOT EMPTY.");
                        makeLogin();
                    }
                }

            }
        });
        TextView mSingUpTextView = (TextView) findViewById(R.id.signBtn);
        mSingUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void makeLogin() {
        // Send data to Parse.com for verification
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
                            Toast.makeText(
                                    getApplicationContext(),
                                    "No such user exist, please signup",
                                    Toast.LENGTH_LONG).show();
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
        EditText username = (EditText) findViewById(R.id.usernameField);
        return username.getText().toString();
    }
    private void mensajeAlerta(String log_message) {
        Log.d(TAG, log_message);
        final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(LoginActivity.this);
        Log.d(TAG, " -*- El popup Dialog se ha creado -*-");
        alertaSimple.setTitle("Sign Up Error");
        alertaSimple.setMessage("Verifíque los campos e intentelo de nuevo");

        alertaSimple.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setContentView(R.layout.activity_sign_up);

            }
        });
        alertaSimple.setIcon(R.mipmap.ic_launcher);
        alertaSimple.create();
        alertaSimple.show();
    }
}

