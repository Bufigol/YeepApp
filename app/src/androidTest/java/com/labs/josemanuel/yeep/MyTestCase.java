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
 * Created by bufigol on 1/02/16.
 */
public class MyTestCase extends ActivityInstrumentationTestCase2<LoginActivity>{
    private static final String USUARIOUNO = "Bufigol";
    private static final String PASSUNO = "1 2 3";
    private LoginActivity actividad;
    private Button iniciarSesion;
    private EditText usuario;
    private EditText password;

    public MyTestCase() {
        super(LoginActivity.class);

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
        if(ParseUser.getCurrentUser()!=null){
            ParseUser.logOut();
        }
        //MyTestCase2 pruebados = new MyTestCase2();
        //pruebados.testUsuarioDos();
    }
   public void testUsuarioUno() {
        //on value 1 entry
        TouchUtils.tapView(this, usuario);
        getInstrumentation().sendStringSync(USUARIOUNO);
        // now on value2 entry
        TouchUtils.tapView(this, password);
       sendKeys(PASSUNO);
        // now on Add button
        TouchUtils.clickView(this, iniciarSesion);

        String usuario = ParseUser.getCurrentUser().getUsername();

        assertTrue("Add result should be...", usuario.equals(USUARIOUNO));

    }

}
