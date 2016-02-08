package com.labs.josemanuel.yeep;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;
import com.labs.josemanuel.yeep.LoginActivity;

/**
 * Clase que realiza las pruebas de la pantalla de Login
 * Created by bufigol on 1/02/16.
 */
public class LoginTestCase extends ActivityInstrumentationTestCase2<LoginActivity>{
    private static final String USERNAME = "Bufigol";
    private static final String PASSWORD = "1 2 3";
    private static final String VACIO = "";
    private LoginActivity actividad;
    private Button loginBtn;
    private EditText usuario;
    private EditText password;

    public LoginTestCase() {
        super(LoginActivity.class);

    }
    protected void setUp() throws Exception {
        super.setUp();
        actividad = getActivity();
        usuario = (EditText) actividad.findViewById(R.id.userFieldSign);
        password = (EditText) actividad.findViewById(R.id.passwordField);
        loginBtn = (Button) actividad.findViewById(R.id.loginBtn);

    }

    protected void tearDown() throws Exception {
        super.tearDown();
        if(ParseUser.getCurrentUser()!=null){
            ParseUser.logOut();
        }
    }
    // Pre-condición de campos no vacios
    public void test1() {
        assertNotNull(usuario);
        assertNotNull(password);
    }
    public void test2() {
        //on value 1 entry
        TouchUtils.tapView(this, usuario);
        getInstrumentation().sendStringSync(VACIO);
        // now on value2 entry
        TouchUtils.tapView(this, password);
        getInstrumentation().sendStringSync(VACIO);
        // now on Add button
        TouchUtils.clickView(this, loginBtn);
        ParseUser parseUser = ParseUser.getCurrentUser();
        assertNull(parseUser);
    }
    public void test3() {

        //on value 1 entry
        TouchUtils.tapView(this, usuario);
        getInstrumentation().sendStringSync(USERNAME);

        // now on value2 entry
        TouchUtils.tapView(this, password);
        sendKeys(PASSWORD);

        // now on Add button
        TouchUtils.clickView(this, loginBtn);

        String usuario = ParseUser.getCurrentUser().getUsername();

        assertTrue("Add result should be...", usuario.equals(USERNAME));


    }
}
