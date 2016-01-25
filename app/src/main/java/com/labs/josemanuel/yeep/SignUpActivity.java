package com.labs.josemanuel.yeep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

    /**
     * Metodo que inicializa la actividad y que le da la funcionalidad al boton de registro.
     * @param savedInstanceState
     * @see #makeSignUp(View)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // elimina la barra superior
        //    getSupportActionBar().hide();

        TextView mSingUpTextView = (TextView) findViewById(R.id.signBtn);
        mSingUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSignUp(v);
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

    /**
     * Método que realiza las comprobaciones y realiza el registro de un nuevo usuario. Este
     * método es el cual le da la funcionalidad al boton correspondiente.
     * @param view
     * @see #checkInputInformation()
     * @see #getInputInfo()
     */
    public void makeSignUp(View view){
        String[] info = getInputInfo();
        ParseUser user = new ParseUser();
        user.setUsername(info[0]);
        user.setPassword(info[1]);
        user.setEmail(info[2]);
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
    /**
     * Método utilizado para mostrar un cuadro de dialogo con un mensaje en caso de no se pueda
     * realizar un correcto inicio de sesión.
     * @param log_message String utilizado con fines de depuración segun su utilización en el codigo.
     */
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

    /**
     * Metodo que comprueba cada campo del registro. Se busca que se ha ingresado informacion en todos
     * los campos y que se ha ingresado un correo electronico valido.
     * @return Verdadero si hay información en todos los campos y se ingreso un correo electronico
     * valido. Falso en cualquier otro caso.
     * @see #isValidEmail(CharSequence)
     * @see #getInputInfo()
     */
    private boolean checkInputInformation(){
        String[] info = getInputInfo();
        if(info[0].matches("")){
            Log.i("SignUpActivity","USER EMPTY.");
            return false;
        }else{
            Log.i("SignUpActivity","USER NOT EMPTY.");
            if(info[1].matches("")){
                Log.i("SignUpActivity","PASS EMPTY.");
                return false;
            }else{
                Log.i("SignUpActivity","PASS NOT EMPTY.");
                if(info[2].matches("")){
                    Log.i("SignUpActivity","EMAIL EMPTY.");
                    return false;
                }else{
                    if(!isValidEmail(info[2])){
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

    /**
     * Metodo para la comprobación de que se esta ingresando un correo electronico valido.
     * @param target texto a comprobar
     * @return Verdadero si es un correo electronico valido y false en caso contrario
     */
    private boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /**
     * Metodo que obtiene los tres campos necesarios para poder realizar el registro y los devuelve
     * en forma de un array de tres posiciones.
     * @return Array de String con la informacion de entrada.
     */
    private String[] getInputInfo(){
        String[] output = new String[3];
        EditText user = (EditText) findViewById(R.id.userFieldSign);
        EditText pass = (EditText) findViewById(R.id.password_sing);
        EditText email = (EditText) findViewById(R.id.emailFieldSign);
        output[0] = user.getText().toString();
        output[1] = pass.getText().toString();
        output[2] = email.getText().toString();
        return output;
    }

}

