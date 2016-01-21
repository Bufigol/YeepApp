package com.labs.josemanuel.yeep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    protected TextView mSingUpTextView;
    private EditText user;
    private EditText pass;
    final static String TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.user = (EditText) findViewById(R.id.usernameField);
        this.pass = (EditText) findViewById(R.id.passwordField);
        // elimina la barra superior
        // getSupportActionBar().hide();

        Button buttonSend = (Button)findViewById(R.id.loginBtn);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputInformation()){
                    makeLogIn(v);
                }
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
    private boolean checkInputInformation(){
        //Checks if itś empty or not.
        if(user.getText().toString().matches("")){
            Log.i("LoginActivity", "USER EMPTY.");
            return false;
        }else{
            Log.i("LoginActivity","USER NOT EMPTY.");
            if(pass.getText().toString().matches("")){
                Log.i("LoginActivity","PASS EMPTY.");
                return false;
            }else{
                Log.i("LoginActivity","PASS NOT EMPTY.");
                return true;
            }
        }

    }
    private void makeLogIn(View v){
        ParseUser.logInInBackground(this.user.getText().toString(), this.pass.getText().toString(), new LogInCallback() {
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

/*
ParseUser.logInInBackground("Jerry", "showmethemoney", new LogInCallback() {
public void done(ParseUser user, ParseException e) {
        if (user != null) {
        // Hooray! The user is logged in.
        } else {
        // Signup failed. Look at the ParseException to see what happened.
        }
        }*/
