package com.labs.josemanuel.yeep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    protected TextView mSingUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // elimina la barra superior
        // getSupportActionBar().hide();

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

        EditText userField = (EditText) findViewById(R.id.userFieldSign);
        final String usernameLogin = userField.getText().toString();
        EditText passField = (EditText) findViewById(R.id.passwordField);
        final String passLogin = passField.getText().toString();

        if (usernameLogin == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Debe introducir un nombre de usuario", Toast.LENGTH_SHORT);
        }
        if (passLogin == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Debe introducir su contraseña", Toast.LENGTH_SHORT);
        } else {

            ParseUser.logInInBackground(usernameLogin, passLogin, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {

                    if (user != null) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // cerramos login al salir del layout
                        // Hooray! The user is logged in.
                    } else {
                        // Signup failed. Look at the ParseException to see what happened.
                        Toast toast = Toast.makeText(getApplicationContext(), "Error, ingrese de nuevo sus datos", Toast.LENGTH_SHORT);
                    }

                }
            });
        }
    }

}
