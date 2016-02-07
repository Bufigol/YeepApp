package com.labs.josemanuel.yeep;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;

/**
 * Created by JoseManuel on 01/02/2016.
 */
public class LoginTestCase extends ActivityInstrumentationTestCase2<LoginActivity> {

    // Instanciamos los componentes
    private LoginActivity actividad;
    private EditText usuario;
    private EditText password;
    private Button loginBtn;


    // Inicializamos las variables que se van a pasar para la prueba
    private static final String USERNAME = "c";
    private static final String PASSWORD = "c";


    public LoginTestCase() {
//		super("com.example.calc", MainActivity.class);
        super(LoginActivity.class);
    }

    //  inicialización común a todos los métodos de prueba
    protected void setUp() throws Exception {
        super.setUp();
        actividad = getActivity();
        usuario = (EditText) actividad.findViewById(R.id.userFieldSign);
        password = (EditText) actividad.findViewById(R.id.passwordField);
        loginBtn = (Button) actividad.findViewById(R.id.loginBtn);
    }
    
  /*  // Pre-condición de campos no vacios
    public void testPreconditions() {
        assertNotNull(usuario);
        assertNotNull(password);
    }*/

    //  Ejecuta después de ejecutar cada test
    protected void tearDown() throws Exception {
        super.tearDown();
        if (ParseUser.getCurrentUser() != null)
            ParseUser.logOut();

    }

    // métodos de prueba
    public void testAddValues() {

        //on value 1 entry
        TouchUtils.tapView(this, usuario);
        getInstrumentation().sendStringSync(USERNAME);

        // now on value2 entry
        TouchUtils.tapView(this, password);
        getInstrumentation().sendStringSync(PASSWORD);

        // now on Add button
        TouchUtils.clickView(this, loginBtn);

        String usuario = ParseUser.getCurrentUser().getUsername();

        assertTrue("Add result should be...", usuario.equals(USERNAME));


    }


}
