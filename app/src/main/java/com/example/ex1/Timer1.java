package com.example.ex1;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.ex1.Logic.GameManager;
import com.google.android.material.imageview.ShapeableImageView;

public class Timer1 {

    private long startTime = 0;
    private final int DELAY = 1000;
    private double speed;
   // private int distance;

    private final Handler handler = new Handler();

    private GameManager gm;
    private int rows;

    private int cols;

    private ShapeableImageView[][] obstacles;
    private ShapeableImageView[][] collectables;
    private ShapeableImageView[] player;
    private ShapeableImageView[] main_IMG_hearts;

    public Timer1(GameManager gm, int ROWS, int COLS,ShapeableImageView[] player, ShapeableImageView[][] collectables, ShapeableImageView[][] obstacles, ShapeableImageView[] main_IMG_hearts, double speed) {
        this.gm = gm;
        this.rows = ROWS;
        this.cols = COLS;
        this.obstacles = obstacles;
        this.player=player;
        this.collectables=collectables;
        this.main_IMG_hearts=main_IMG_hearts;
        this.speed=speed;
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, (int)(DELAY*speed)); //Do it again in a second
            gm.incDistance();
            gm.incPoints();
            gm.updatePointsUI();
            if(gm.isHit()) {
                Log.d("isHIT", "hit" );
                gm.crash();
                gm.gameOverIfNeeded();



            }

            if(gm.isCollect()) {
            gm.collect();
                gm.updatePointsUI();
            Log.d("points","added");
            }
            Log.d("distance:", gm.getDistance()+" meters" );
            Log.d("points:",""+gm.getPoints());



            gm.goDown();
            refreshUI();
        }
    };
    private Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, (int)(DELAY*speed)); //Do it again in a second
            int rolled = gm.rollCol();
            gm.addObstacle(rolled);
          //  gm.addCollecetable(gm.rollCol(rolled));
            refreshUI();
        }
    };
    private Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, (int)(DELAY*speed*5)); //Do it again in a second
            int rolled = gm.rollCol();
            gm.addCollecetable(gm.rollCol(rolled));
            refreshUI();



        }
    };
    Integer refreshUI() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (gm.getMat()[i][j] == 1) {
                    obstacles[i][j].setVisibility(View.VISIBLE);
                    collectables[i][j].setVisibility(View.INVISIBLE);
                }
                else  if (gm.getMat()[i][j] == 2) {
                    collectables[i][j].setVisibility(View.VISIBLE);
                    obstacles[i][j].setVisibility(View.INVISIBLE);
                }


                 else{
                    obstacles[i][j].setVisibility((View.INVISIBLE));
                    collectables[i][j].setVisibility((View.INVISIBLE));

                }



            }

        }
        for (int i = 0; i <cols; i++) {
            if (gm.getCurrPlayerPos()==i){
                player[i].setVisibility(View.VISIBLE);

            }
            else{
                player[i].setVisibility(View.INVISIBLE);
            }

        }

        if (gm.getWrong() != 0&& gm.getWrong()<=3)
            main_IMG_hearts[main_IMG_hearts.length - gm.getWrong()].setVisibility(View.INVISIBLE);


        return 0;
    }
    public void startTime() {
        startTime = System.currentTimeMillis();
        handler.postDelayed(runnable,0);
        handler.postDelayed(runnable2,0);
        handler.postDelayed(runnable3,0);

    }

    public void stopTime() {
        handler.removeCallbacks(runnable);
        handler.removeCallbacks(runnable2);
        handler.removeCallbacks(runnable3);
    }

}

