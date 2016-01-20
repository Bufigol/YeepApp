package com.labs.josemanuel.yeep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

       user = (EditText) findViewById(R.id.usernameField);
        pass = (EditText) findViewById(R.id.password_sing);

        // elimina la barra superior
        getSupportActionBar().hide();

    }

    public void


}
