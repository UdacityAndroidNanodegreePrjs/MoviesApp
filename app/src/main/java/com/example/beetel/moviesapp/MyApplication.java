package com.example.beetel.moviesapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by beetel on 7/03/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
