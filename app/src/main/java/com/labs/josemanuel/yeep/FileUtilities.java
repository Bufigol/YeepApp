package com.labs.josemanuel.yeep;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by JoseManuel on 07/02/2016.
 */
public class FileUtilities {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String APP_NAME = "Wick";

    /**
     * comprobamos mediante la constante  MEDIA_MOUNTED
     * si está disponible el almacenamiento externo,
     * esto puede ser una tarjeta SD o una partición en la memoria
     * interna.
     *
     *
     */
    public static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }else{
            return false;
        }
    }

    // indicaremos a la cámara donde poner la imagen.
    // lo primero comprobamos que exista lugar para almacenamiento externo.
    public static Uri getOutputMediaFileUri(int mediaType) {
        if (isExternalStorageAvailable()) {
            File mediaStorageDir = null;
            switch (mediaType){
                case MEDIA_TYPE_IMAGE:
                    mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    break;
                case MEDIA_TYPE_VIDEO:
                    mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                    break;
            }
            if(!mediaStorageDir.exists()){
                Log.d(TAG,mediaStorageDir.getAbsolutePath() +" not exists");
                if(!mediaStorageDir.mkdirs()){
                    Log.d(TAG,"Directory " +mediaStorageDir.getAbsolutePath()+ " not created");
                    return null;
                }
            }
        }
        return null;


    }

}
