package com.labs.josemanuel.yeep;

import android.app.ActionBar;
import android.app.Activity;
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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    final static String TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // elimina la barra superior
     //   ActionBar actionBar =getActionBar();
     //   actionBar.hide();
    //    getSupportActionBar().hide();

        TextView mSingUpTextView = (TextView) findViewById(R.id.signBtn);
        mSingUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarDatos(v);
            }
        });


        Button cancelButton = (Button)findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    public void comprobarDatos(View view){
        EditText usuario = (EditText) findViewById(R.id.userFieldSign);
        String usernameSign = usuario.getText().toString();

        EditText password=(EditText) findViewById(R.id.password_sing);
        String passwordSign = password.getText().toString();

        EditText mail = (EditText) findViewById(R.id.emailFieldSign);
        String emailSign = mail.getText().toString();

        ParseUser user = new ParseUser();
        user.setUsername(usernameSign.toString());
        user.setPassword(passwordSign.toString());
        user.setEmail(emailSign.toString());

        // other fields can be set just like with ParseObject
        // user.put("phone", "650-253-0000");

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // cerramos registro al salir del layout
                    // Hooray! Let them use the app now.
                } else {
                    Log.d(TAG,e.toString());
                    final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(SignUpActivity.this);
                    Log.d(TAG, " -*- El popup Dialog se ha creado -*-");
                    alertaSimple.setTitle("Sign Up Error");
                    alertaSimple.setMessage("Hay algún error en el registro.? \n " +
                            "Verifíque los campos e intentelo de nuevo");

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

                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong

            }
        });
    }



}



/*

String usernameSign = findViewById(R.id.usernameField).toString();
String passwordSign = findViewById(R.id.passwordField).toString();
String emailSign = findViewById(R.id.emailField).toString();


ParseUser user = new ParseUser();
user.setUsername(usernameSign);
        user.setPassword(passwordSign);
        user.setEmail(emailSign);

        // other fields can be set just like with ParseObject
        // user.put("phone", "650-253-0000");

        user.signUpInBackground(new SignUpCallback() {
public void done(ParseException e) {
        if (e == null) {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        // Hooray! Let them use the app now.
        } else {

final AlertDialog.Builder alertaSimple = new AlertDialog.Builder(SignUpActivity.this);
        Log.d(TAG, " -*- El popup Dialog se ha creado -*-");
        alertaSimple.setTitle("Sign Up Error");
        alertaSimple.setMessage("Hay algún error en el registro.? \n " +
        "Verifíque los campos e intentelo de nuevo");

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

        // Sign up didn't succeed. Look at the ParseException
        // to figure out what went wrong

        }
        });*/
