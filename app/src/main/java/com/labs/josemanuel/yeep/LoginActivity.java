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

        Button buttonSend = (Button)findViewById(R.id.loginBtn);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userField = (EditText) findViewById(R.id.userFieldSign);
                String usernameLogin = userField.getText().toString();
                EditText passField = (EditText) findViewById(R.id.passwordField);
                String passLogin = passField.getText().toString();

                ParseUser.logInInBackground(usernameLogin, passLogin, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {

                              Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                              startActivity(intent);
                              finish(); // cerramos login al salir del layout
                            // Hooray! The user is logged in.
                        } else {
                            // Signup failed. Look at the ParseException to see what happened.
                            Toast toast = Toast.makeText(getApplicationContext(),"Error, ingrese de nuevo sus datos",Toast.LENGTH_SHORT);
                        }
                    }
                });


/*              EditText userField = (EditText) findViewById(R.id.userFieldSign);
                String usernameLogin = userField.getText().toString();
                EditText passField = (EditText) findViewById(R.id.passwordField);
                String passLogin = passField.getText().toString();*/


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
}

/*
ParseUser.logInInBackground("Jerry", "showmethemoney", new LogInCallback() {
public void done(ParseUser user, ParseException e) {
        if (user != null) {
        // Hooray! The user is logged in.
        } else {
        // Signup failed. Look at the ParseException to see what happened.
        }
        }*/
