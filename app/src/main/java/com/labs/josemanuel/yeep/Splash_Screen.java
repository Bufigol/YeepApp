package com.labs.josemanuel.yeep;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;


public class Splash_Screen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        cambiarFont();
        openApp();
    }

    /**
     * Metodo Utilizado para establecer un nuevo tipo de letra en el titulo y subtitulo existentes
     * en el layout del splash screen.
     */
    private void cambiarFont() {
        TextView myTitle = (TextView)findViewById(R.id.Title);
        TextView mySubtitle = (TextView)findViewById(R.id.subTitle);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Nickainley-Normal.otf");
        myTitle.setTypeface(myFont);
        mySubtitle.setTypeface(myFont);
    }

    /**
      * Metodo que tiene como finalidad de realizar el intent al layout para el inicio de sesión luego
     * de 2 segundos.
     *
    */
    private void openApp() {
        Handler handler = new Handler();
        if(ParseUser.getCurrentUser() == null){

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash_Screen
                            .this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash_Screen
                            .this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
    }

}

