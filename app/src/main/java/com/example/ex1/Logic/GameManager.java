package com.example.ex1.Logic;

import com.example.ex1.Fragments.MapFragment;
import com.example.ex1.Interface.CallBackPlaySound;
import com.example.ex1.Interface.CallBackUpdatePoints;
import com.example.ex1.Interface.gameOverCallable;

import com.example.ex1.R;
import com.example.ex1.Score;
import com.example.ex1.ScoresActivity;
import com.example.ex1.Utillities.MyMediaPlayer;
import com.example.ex1.Utillities.MySharedPreferences;
import com.example.ex1.Utillities.SignalGenerator;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Random;

public class GameManager extends AppCompatActivity {
    int cols,rows,currPlayerPos,currObsPos,currCollectablePos,mid;
    final int COLLECTABLE_VALUE=10;
    final int METER_VALUE=1;
    private int points;
    private String name;
    private int distance;
    private double latitude;
    private double longtitude;

    final int MID =1;
    final int LIVES=3;
    final int LEFT_BOUND=0;
    final int RIGHT_BOUND=4;
    ShapeableImageView[] player;
    ShapeableImageView[][] obstacles;
    private MySharedPreferences mySharedPreferences;
    Random rnd;
    CallBackPlaySound callBackPlaySound;
    private MapFragment mapFragment;


    private int mat[][];
    private Score score;
   private int wrong;
   private SignalGenerator signalGenerator;

   private gameOverCallable gameOverCallable;
   private CallBackUpdatePoints callBackUpdatePoints;

    public GameManager(int rows, int cols, gameOverCallable gameOverCallable, CallBackPlaySound callBackPlaySound, CallBackUpdatePoints callBackUpdatePoints){
        this.cols=cols;
        this.rows=rows;
        wrong=0;
        currPlayerPos=MID;
        currObsPos=-1;
        currCollectablePos=-1;
        mat=new int[rows][cols];
        initMat();
        rnd=new Random();
        mid=cols/2;
        points=0;
        distance=0;
        longtitude=0;
        latitude=0;
        name="";
        this.gameOverCallable= gameOverCallable;
        this.callBackPlaySound=callBackPlaySound;
        this.callBackUpdatePoints=callBackUpdatePoints;
        mapFragment=new MapFragment();

    }
    public int[][] getMat(){
        return this.mat;
    }

    public int getWrong() {
        return wrong;
    }

    public int getRows(){
        return this.rows;
    }
    public int getCols(){
      return this.cols;
    }

    public boolean isGameOver(){
        if(this.wrong==LIVES)
            return true;
        else return false;
    }
    public void gameOverIfNeeded(){
        if(isGameOver()){
            //supposed to get current player location
            latitude=(rnd.nextFloat()/10)+32.116834782923036;
            longtitude=(rnd.nextFloat()/10)+34.815600891407804;
            score = new Score(name,distance,points, latitude,  longtitude);
            DataManager.getInstance().addScore(score);
            gameOverCallable.GameOver();

            Log.d( "data:",DataManager.getInstance().getScores().get(0).getName());
        }
    }

    public int getCurrPlayerPos() {
        return this.currPlayerPos;

    }


    public void initMat(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j]=0;
            }
        }
    }
    public int rollCol(){

        return rnd.nextInt(cols);
    }
    public int rollCol(int taken){
        if(taken<mid){
            return rnd.nextInt(mid)+mid;
        }
        return rnd.nextInt(taken);
    }
    public void addObstacle(int col){
        mat[0][col]=1;
    }
    public void addCollecetable(int col) {
        mat[0][col]=2;
    }

    public void setPos() {
        for (int i = 0; i < cols; i++) {
            if (mat[rows - 1][i] == 1)
                currObsPos = i;
            else if(mat[rows-1][i]==2)
                currCollectablePos=i;
        }
    }


    public int getCurrObsPos() {

        return currObsPos;
    }

    public void goDown(){
        for (int i = rows-2; i >=0; i--)
        {
            for (int j = cols-1; j >= 0; j--)
            {
                mat[i+1][j]=mat[i][j];


            }

            }
        for (int i = 0; i < cols; i++)
        {
            mat[0][i]=0;

        }
        setPos();

        }
        public boolean isHit(){
        if(currPlayerPos==currObsPos)
            return true;

        return false;
    }
    public boolean isCollect(){
        if( currPlayerPos==currCollectablePos)
            return true;
        return false;
    }


    public void goLeft() {

        if (currPlayerPos!=LEFT_BOUND){
            currPlayerPos--;

        }
    }

    public void goRight() {
        if (currPlayerPos!=RIGHT_BOUND){
            currPlayerPos++;

        }
    }

    public void crash() {
        String text = "crashed";
        int duration = Toast.LENGTH_SHORT;
        signalGenerator.getInstance().toast(text,duration);
        signalGenerator.getInstance().vibrate(500);
       callBackPlaySound.playSound();
        wrong++;




    }

    public void setScore(Score score){

    }


    public void collect() {
        this.points+=COLLECTABLE_VALUE;
    }
    public void incPoints(){
        this.points+=METER_VALUE;
    }

    public int getPoints() {
        return this.points;
    }

    public void incDistance() {
        this.distance++;
    }

    public int getDistance() {
        return this.distance;
    }
    public void setName(String name){
        this.name=name;
    }
    public String  getName(){
        return this.name;
    }
    public Score getScore(){
        return this.score;
    }

    public void setCurrPlayerPos(int currPlayerPos) {
        this.currPlayerPos=currPlayerPos;
    }

    public void updatePointsUI(){
        callBackUpdatePoints.updatePointsUI(points);

    }
}



