package com.example.ex1.Logic;

import com.example.ex1.Interface.gameOverCallable;
import com.example.ex1.Score;
import com.google.android.material.imageview.ShapeableImageView;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class gameManager extends AppCompatActivity {
    int cols,rows,currPlayerPos,currObsPos,currCollectablePos,mid;
    final int COLLECTABLE_VALUE=10;
    final int METER_VALUE=1;
    private int points;
    private String name;
    private int distance;

    final int MID =1;
    final int LIVES=3;
    final int LEFT_BOUND=0;
    final int RIGHT_BOUND=4;
    ShapeableImageView[] player;
    ShapeableImageView[][] obstacles;
    Random rnd;
    Context context;
    Vibrator v;

    private int mat[][];
    private Score score;
   private int wrong;

   private gameOverCallable gameOverCallable;

    public gameManager(int rows, int cols, gameOverCallable gameOverCallable){
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
        name="";
        this.gameOverCallable= gameOverCallable;

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

    public void setContext(Context context) {
        this.context = context;
    }
    public boolean isGameOver(){
        if(this.wrong==LIVES)
            return true;
        else return false;
    }
    public void gameOverIfNeeded(){
        if(isGameOver()){
            gameOverCallable.GameOver();
            score = new Score(name,distance,points,new Point(0,0));
            DataManager.getInstance().addScore(score);
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
        CharSequence text = "crashed";
        int duration = Toast.LENGTH_SHORT;


        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
        wrong++;




    }

    public void setVibrator(Vibrator v) {
        this.v=v;
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
}



