package com.example.ex1.Logic;

import com.google.android.material.imageview.ShapeableImageView;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class gameManager extends AppCompatActivity {
    int cols,rows,currPlayerPos,currObsPos;

    final int MID =1;
    final int LEFT_BOUND=0;
    final int RIGHT_BOUND=4;
    ShapeableImageView[] player;
    ShapeableImageView[][] obstacles;
    Context context;
    Vibrator v;

    int mat[][];
   private int wrong;


    public gameManager(int rows,int cols){
        this.cols=cols;
        this.rows=rows;
        wrong=0;
        currPlayerPos=MID;
        currObsPos=-1;
        mat=new int[rows][cols];
        initMat();

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
        Random rnd = new Random();
        return rnd.nextInt(cols);
    }
    public void addObstacle(int col){
        mat[0][col]=1;
    }

    public void setCurrObsPos() {
        for (int i = 0; i < cols; i++) {
            if (mat[rows - 1][i] == 1)
                currObsPos = i;


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
        setCurrObsPos();

        }
        public boolean isHit(){
        if(currPlayerPos==currObsPos)
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
}



