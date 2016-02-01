package com.labs.josemanuel.yeep;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;
import com.labs.josemanuel.yeep.LoginActivity;

/**
 * Created by bufigol on 1/02/16.
 */
public class MyTestCase extends ActivityInstrumentationTestCase2<LoginActivity>{
    private static final String USUARIOUNO = "Bufigol";
    private static final String PASSUNO = "1 2 3";
    private static final String USUARIODOS = "c";
    private static final String PASSDOS = "c";
    private LoginActivity actividad;
    private Button iniciarSesion;
    private EditText usuario;
    private EditText password;

    public MyTestCase() {
        super(LoginActivity.class);
        if(ParseUser.getCurrentUser()!=null){
            ParseUser.logOut();
        }
    }
    protected void setUp() throws Exception {
        super.setUp();
        actividad = getActivity();
        usuario = (EditText) actividad.findViewById(R.id.userFieldSign);
        password = (EditText) actividad.findViewById(R.id.passwordField);
        iniciarSesion = (Button) actividad.findViewById(R.id.loginBtn);

    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
