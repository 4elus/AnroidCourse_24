package com.alexander.roomdbproject;

import android.app.Application;


public class InitDb extends Application {
    public static AppDatabase appDatabase;


    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = AppDatabase.getDatabase(this);
    }


}

