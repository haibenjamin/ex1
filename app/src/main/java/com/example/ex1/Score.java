package com.example.ex1;

import android.graphics.Point;

import androidx.recyclerview.widget.RecyclerView;

public class Score  {

    private String name;
    private int distance;
    private int points;
    private Point location;

    public Score(String name,int distance, int points,Point location){
        this.name=name;
        this.distance=distance;
        this.points=points;
        this.location=location;
    }

    public String getName(){
        return this.name;
    }

    public int getDistance() {
        return this.distance;
    }

    public int getPoints() {
        return this.points;
    }
}
