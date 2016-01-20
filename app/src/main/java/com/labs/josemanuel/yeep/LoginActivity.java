package com.labs.josemanuel.yeep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    protected TextView mSingUpTextView;
    EditText user, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.usernameField);
        pass = (EditText) findViewById(R.id.passwordField);
        // elimina la barra superior
        getSupportActionBar().hide();

        mSingUpTextView = (TextView) findViewById(R.id.signBtn);
        mSingUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    public void check(View view){

        if(user.getText().toString().matches("")){
            Log.i("LoginActivity", "USER EMPTY.");
        }else{
            Log.i("LoginActivity","USER NOT EMPTY.");
        }
        if(pass.getText().toString().matches("")){
            Log.i("LoginActivity","PASS EMPTY.");
        }else{
            Log.i("LoginActivity","PASS NOT EMPTY.");
        }

    }

}
