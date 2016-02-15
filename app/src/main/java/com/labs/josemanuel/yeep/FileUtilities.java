package com.labs.josemanuel.yeep;

import android.app.Application;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by JoseManuel on 07/02/2016.
 */
public class FileUtilities {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;


    /**
     * comprobamos mediante la constante  MEDIA_MOUNTED
     * si está disponible el almacenamiento externo,
     * esto puede ser una tarjeta SD o una partición en la memoria
     * interna.
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

        Uri uri;

        if (isExternalStorageAvailable()) {
            // get URI

            //1 Obtener el directorio del almacenamieto externo
            String appName = String.valueOf(R.string.app_name);

            File mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    appName);


            //   File mediaStorageDir = null;
            switch (mediaType) {
                case MEDIA_TYPE_IMAGE:
                    mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    break;
                case MEDIA_TYPE_VIDEO:
                    mediaStorageDir = Environment.
                            getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                    break;
            }

            //2 Crear nuestro subdirectorio
            if (!mediaStorageDir.exists()) {
                Log.d(TAG, mediaStorageDir.getAbsolutePath() + " not exist");
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(TAG, "Directory " + mediaStorageDir.getAbsolutePath() + " not created");
                    return null;
                }
            }


            //3 Crear un  nombre del fichero
            String fileName = "";
            Date now = new Date();
            String timestamp = new SimpleDateFormat(
                    "yyyyMMdd_HHmmss", new Locale("es", "ES")).format(now);
            switch (mediaType) {
                case MEDIA_TYPE_IMAGE:
                    fileName = "IMG_" + timestamp + ".jpg";
                    break;
                case MEDIA_TYPE_VIDEO:
                    fileName = "VID_" + timestamp + ".mp4";
                    break;
            }

            //4 Crear un objeto File con el nombre del fichero
            String pathFile = mediaStorageDir.getAbsolutePath() +
                    File.separator +
                    fileName;
            File mediaFile = new File(pathFile);
            Log.d(TAG, "File : " + mediaFile.getAbsolutePath());


            //5 Devolver el URI del fichero

            return Uri.fromFile(mediaFile);
        }
        return null;


    }


}
