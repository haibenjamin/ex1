package com.example.ex1.Logic;

import android.provider.ContactsContract;

import com.example.ex1.Score;

import java.util.ArrayList;
import java.util.Set;

public final class DataManager {


        private static DataManager INSTANCE;
        private ArrayList<Score> scores;

        private DataManager() {
            scores=new ArrayList<>();
        }

        public static DataManager getInstance() {
            if(INSTANCE == null) {
                INSTANCE = new DataManager();
            }

            return INSTANCE;
        }

        // getters and setters
    public ArrayList<Score> getScores(){
            return this.scores;
    }
    public void addScore(Score score){
            this.scores.add(score);
    }

    }


