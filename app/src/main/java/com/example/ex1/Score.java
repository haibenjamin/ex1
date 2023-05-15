package com.example.ex1;

import android.graphics.Point;

import androidx.recyclerview.widget.RecyclerView;

public class Score implements Comparable<Score>  {

    private String name;
    private int distance;
    private int points;
    private double longitude;
    private double latitude;

    public Score(String name,int distance, int points,double longitude,double latitude){
        this.name=name;
        this.distance=distance;
        this.points=points;
        this.longitude=longitude;
        this.latitude=latitude;

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
    public double getLongitude(){
        return this.longitude;
    }
    public double getLatitude(){return this.latitude;}

    @Override
    public int compareTo(Score otherScore) {
        return name.compareTo(otherScore.name);
    }
}
