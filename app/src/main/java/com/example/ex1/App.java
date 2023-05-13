package com.example.ex1;

import android.app.Application;

import com.example.ex1.Utillities.SignalGenerator;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SignalGenerator.init(this);
    }
}
