package com.labs.josemanuel.yeep;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Bundle;
import android.widget.TextView;


public class Splash_Screen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        cambiarFont();
        openApp(true);
    }

    /**
     * Metodo Utilizado para establecer un nuevo tipo de letra en el titulo y subtitulo existentes
     * en el layout del splash screen.
     */
    private void cambiarFont() {
        TextView myTitle = (TextView)findViewById(R.id.Title);
        TextView mySubtitle = (TextView)findViewById(R.id.subTitle);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Gagalin-Regular.otf");
        myTitle.setTypeface(myFont);
        mySubtitle.setTypeface(myFont);
    }

    /**
      * Metodo que tiene como finalidad de realizar el intent al layout para el inicio de sesión luego
     * de 2 segundos.
     * @param locationPermission
    */
    private void openApp(boolean locationPermission) {

        Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash_Screen
                            .this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
    }
}

