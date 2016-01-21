package com.labs.josemanuel.yeep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    private EditText user, pass, email;
    final static String TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.user = (EditText) findViewById(R.id.usernameField);
        this.pass = (EditText) findViewById(R.id.password_sing);
        this.email = (EditText) findViewById(R.id.emailFieldSign);

        // elimina la barra superior
    //    getSupportActionBar().hide();

        TextView mSingUpTextView = (TextView) findViewById(R.id.signBtn);
        mSingUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSignUp(v);
            }
        });

    }
    public void makeSignUp(View view){
        String usernameSign = this.user.getText().toString();
        String passwordSign = this.pass.getText().toString();
        String emailSign = this.email.getText().toString();

        ParseUser user = new ParseUser();
        user.setUsername(usernameSign);
        user.setPassword(passwordSign);
        user.setEmail(emailSign);
        // other fields can be set just like with ParseObject
        // user.put("phone", "650-253-0000");
        if(checkInputInformation()) {
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // cerramos registro al salir del layout
                        // Hooray! Let them use the app now.
                    } else {
                        mensajeAlerta(e.toString());
                    }

                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong

                }
            });
        }else{
            mensajeAlerta("checkInputInformation() returned false");
        }
    }

    private void mensajeAlerta(String log_message) {
        Log.d(TAG, log_message);
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

    private boolean checkInputInformation(){
        if(this.user.getText().toString().matches("")){
            Log.i("SignUpActivity","USER EMPTY.");
            return false;
        }else{
            Log.i("SignUpActivity","USER NOT EMPTY.");
            if(this.pass.getText().toString().matches("")){
                Log.i("SignUpActivity","PASS EMPTY.");
                return false;
            }else{
                Log.i("SignUpActivity","PASS NOT EMPTY.");
                if(this.email.getText().toString().matches("")){
                    Log.i("SignUpActivity","EMAIL EMPTY.");
                    return false;
                }else{
                    if(!isValidEmail(this.email.getText().toString())){
                        Log.i("SignUpActivity","WRONG EMAIL.");
                        return false;
                    }else{
                        Log.i("SignUpActivity","EMAIL OK.");
                        Log.i("SignUpActivity","EVERYTHING OK.");
                        return true;
                    }
                }
            }
        }
    }
    private boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
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
