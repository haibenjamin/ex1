package com.example.ex1;

import android.app.Application;

import com.example.ex1.Logic.DataManager;
import com.example.ex1.Utillities.MyMediaPlayer;
import com.example.ex1.Utillities.MySharedPreferences;
import com.example.ex1.Utillities.SignalGenerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SignalGenerator.init(this);
        MySharedPreferences.init(this);

        loadScores();
    }


    private void loadScores() {
        String test = MySharedPreferences.getInstance().getString("test","");
        String fromSP = MySharedPreferences.getInstance().getString("scoresList", null);
        if (fromSP != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Score>>() {
            }.getType();
            ArrayList<Score> scoreListFromJson = new Gson().fromJson(fromSP, type);
            if (scoreListFromJson != null && scoreListFromJson.size() > 0) {
                DataManager.setList(scoreListFromJson);
            }
        }

    }
}
