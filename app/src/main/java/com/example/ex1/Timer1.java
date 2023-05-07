package com.example.ex1;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.content.Context;

import com.example.ex1.Logic.gameManager;
import com.google.android.material.imageview.ShapeableImageView;

public class Timer1 {

    private long startTime = 0;
    private final int DELAY = 1000;

    private final Handler handler = new Handler();

    private gameManager gm;
    private Integer rows;

    private Integer cols;

    private ShapeableImageView[][] obstacles;
    private ShapeableImageView[] main_IMG_hearts;

    public Timer1(gameManager gm, Integer ROWS, Integer COLS, ShapeableImageView[][] obstacles,ShapeableImageView[] main_IMG_hearts) {
        this.gm = gm;
        this.rows = ROWS;
        this.cols = COLS;
        this.obstacles = obstacles;
        this.main_IMG_hearts=main_IMG_hearts;
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, DELAY); //Do it again in a second
            if(gm.isHit()) {
                Log.d("isHIT", "hit" );
                gm.crash();
            }



            gm.goDown();
            refreshUI();
        }
    };
    private Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, DELAY*2); //Do it again in a second
            gm.addObstacle(gm.rollCol());
            refreshUI();
        }
    };
    Integer refreshUI() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (gm.getMat()[i][j] == 1) {
                    obstacles[i][j].setVisibility(View.VISIBLE);
                } else
                    obstacles[i][j].setVisibility((View.INVISIBLE));
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
    }

    private void stopTime() {
        handler.removeCallbacks(runnable);
    }
}

