package com.labs.josemanuel.yeep;

import android.app.Application;

/**
 * Created by JoseManuel on 14/01/2016.
 */
public class YeepApp extends Application {

    public void onCreate(){

        super.onCreate();


        Parse.initialize(this);





    }




}
