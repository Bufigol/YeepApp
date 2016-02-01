package com.labs.josemanuel.yeep;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

/**
 * Created by bufigol on 1/02/16.
 */
public class MyTestCase2 extends ActivityInstrumentationTestCase2<LoginActivity> {
    private static final String USUARIODOS = "c";
    private static final String PASSDOS = "c";
    private LoginActivity actividad;
    private Button iniciarSesion;
    private EditText usuario;
    private EditText password;

    public MyTestCase2() {
        super(LoginActivity.class);

    }
    protected void setUp() throws Exception {
        super.setUp();
        actividad = getActivity();
        usuario = (EditText) actividad.findViewById(R.id.userFieldSign);
        password = (EditText) actividad.findViewById(R.id.passwordField);
        iniciarSesion = (Button) actividad.findViewById(R.id.loginBtn);

    }
    public void testUsuarioDos() {
        //on value 1 entry
        TouchUtils.tapView(this, usuario);
        getInstrumentation().sendStringSync(USUARIODOS);
        // now on value2 entry
        TouchUtils.tapView(this, password);
        getInstrumentation().sendStringSync(PASSDOS);
        // now on Add button
        TouchUtils.clickView(this, iniciarSesion);

        String usuario = ParseUser.getCurrentUser().getUsername();

        assertTrue("Add result should be...", usuario.equals(USUARIODOS));

    }
}
