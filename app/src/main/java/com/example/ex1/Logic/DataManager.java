package com.example.ex1.Logic;

import android.provider.ContactsContract;

import com.example.ex1.Score;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

public final class DataManager {


        private static DataManager INSTANCE;
        private static ArrayList<Score> scores;

        private DataManager() {
            scores=new ArrayList<>();
        }

        public static DataManager getInstance() {
            if(INSTANCE == null) {
                INSTANCE = new DataManager();
            }

            return INSTANCE;
        }

    public static void setList(ArrayList scoreList) {

            scores=new ArrayList<>();
            scores.addAll(scoreList);


        }


    // getters and setters
    public ArrayList<Score> getScores(){
            return this.scores;
    }
    public void addScore(Score score){
            this.scores.add(score);
    }
    public void Sort(){
        this.getScores().sort(new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o2.getPoints()-o1.getPoints();
            }
        });
    }


    @Override
    public String toString() {
        return "DataManager{" +
                "scores=" + scores +
                '}';
    }
    }


