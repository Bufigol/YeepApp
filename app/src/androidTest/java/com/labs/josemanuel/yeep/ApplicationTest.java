package com.labs.josemanuel.yeep;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.test.ActivityInstrumentationTestCase2;

import com.parse.ParseUser;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private EditText user;
    private EditText pass;
    private Button login;
    private LoginActivity actividad;
    private String USER="c", PASS="c";

    public ApplicationTest() {
        super(LoginActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        actividad = getActivity();
        user = (EditText) actividad.findViewById(R.id.userFieldSign);
        pass = (EditText) actividad.findViewById(R.id.password_sing);
        login = (Button) actividad.findViewById(R.id.loginBtn);
    }


    protected void tearDown() throws Exception {
        super.tearDown();
        if(ParseUser.getCurrentUser()!=null)
            ParseUser.logOut();
    }


    public void testValues() {
        //on value 1 entry
        TouchUtils.tapView(this, user);
        sendKeys(USER);
        // now on value2 entry
        TouchUtils.tapView(this, pass);
        sendKeys(PASS);
        // now on Multiply button
        TouchUtils.clickView(this, login);

    }






}





