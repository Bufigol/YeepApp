package com.labs.josemanuel.yeep;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class Splash_Sceen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__sceen);
        openApp(true);
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
                    Intent intent = new Intent(Splash_Sceen
                            .this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
}

