package com.example.ex1;

import com.example.ex1.Interface.gameOverCallable;
import com.example.ex1.Logic.DataManager;
import com.example.ex1.Logic.gameManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Vibrator;
import android.view.View;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

public class GameActivity extends AppCompatActivity  {

    public static final String NAME ="KEY_NAME" ;
    public static final String SPEED = "KEY_SPEED";
    final int ROWS=5;
     final int COLS=5;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] player;
    private ShapeableImageView[][] obstacles;
    private ShapeableImageView[][] collecetables;
    private FloatingActionButton fabLeft,fabRight;
    private FloatingActionButton[] fab;
    int currPlayerPos;
    private gameManager gm;
    private Timer1 timer;
    private String name;



    private double speed;
    private int score=0;
    private gameOverCallable gameOverCallable;

    public void setGameOverCallback(gameOverCallable gameOverCallable) {
        this.gameOverCallable = gameOverCallable;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        Glide
                .with(this)
                .load("https://images.pexels.com/photos/1450082/pexels-photo-1450082.jpeg")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background);

        initScreen(obstacles,player,main_IMG_hearts);
        setGameOverCallback(new gameOverCallable() {
            @Override
            public void GameOver() {
                timer.stopTime();
                finish();
                startActivity(new Intent(GameActivity.this, MainActivity.class));

            }
        });
        Intent prevIntent = getIntent();
        speed=prevIntent.getDoubleExtra("KEY_SPEED",1.0);
        name=prevIntent.getStringExtra("KEY_NAME");
        if(name==null|| name.equals(""))
            name="player";
        gm= new gameManager(ROWS,COLS,gameOverCallable);
        gm.setContext(getApplicationContext());
        gm.setName(name);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gm.setVibrator(v);
        timer=new Timer1(gm, ROWS, COLS,collecetables, obstacles,main_IMG_hearts,speed);
        timer.startTime();




        fab[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gm.goLeft();
                movePlayer(gm.getCurrPlayerPos());


            }
        });
        fab[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gm.goRight();
                movePlayer(gm.getCurrPlayerPos());

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.stopTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.startTime();
    }

    private void movePlayer(int currPlayerPos) {
        for (int i = 0; i < player.length; i++) {
            if (currPlayerPos==i){
                player[i].setVisibility(View.VISIBLE);
            }
            else{
                player[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initScreen(ShapeableImageView[][] obstacles,ShapeableImageView[] player,ShapeableImageView[] hearts) {

           for (int i = 0; i <ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                obstacles[i][j].setVisibility(View.INVISIBLE);
                collecetables[i][j].setVisibility(View.INVISIBLE);

            }
        }
        player[0].setVisibility(View.INVISIBLE);
        player[1].setVisibility(View.INVISIBLE);
        player[3].setVisibility(View.INVISIBLE);
        player[4].setVisibility(View.INVISIBLE);
        currPlayerPos=1;

    }

    private void findViews() {

        fab=new FloatingActionButton[]{
                findViewById(R.id.left),
                findViewById((R.id.right))};

        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)

        };

        player = new ShapeableImageView[]{
                findViewById(R.id.player1),
                findViewById(R.id.player2),
                findViewById(R.id.player3),
                findViewById(R.id.player4),
                findViewById(R.id.player5),
        };
        obstacles =new ShapeableImageView[][] {
                {
                    findViewById(R.id.obstacle00),
                findViewById(R.id.obstacle01),
                findViewById(R.id.obstacle02),
                        findViewById(R.id.obstacle03),
                        findViewById(R.id.obstacle04) },
                { findViewById(R.id.obstacle10),
                        findViewById(R.id.obstacle11),
                        findViewById(R.id.obstacle12),
                        findViewById(R.id.obstacle13),
                        findViewById(R.id.obstacle14) },
                { findViewById(R.id.obstacle20),
                        findViewById(R.id.obstacle21),
                        findViewById(R.id.obstacle22),
                        findViewById(R.id.obstacle23),
                        findViewById(R.id.obstacle24) },
                { findViewById(R.id.obstacle30),
                        findViewById(R.id.obstacle31),
                        findViewById(R.id.obstacle32),
                        findViewById(R.id.obstacle33),
                        findViewById(R.id.obstacle34) }
        ,{ findViewById(R.id.obstacle40),
                findViewById(R.id.obstacle41),
                findViewById(R.id.obstacle42),
                findViewById(R.id.obstacle43),
                findViewById(R.id.obstacle44) }};
        collecetables =new ShapeableImageView[][] {
                { findViewById(R.id.cheese00),
                        findViewById(R.id.cheese01),
                        findViewById(R.id.cheese02),
                        findViewById(R.id.cheese03),
                        findViewById(R.id.cheese04) },
                { findViewById(R.id.cheese10),
                        findViewById(R.id.cheese11),
                        findViewById(R.id.cheese12),
                        findViewById(R.id.cheese13),
                        findViewById(R.id.cheese14) },
                { findViewById(R.id.cheese20),
                        findViewById(R.id.cheese21),
                        findViewById(R.id.cheese22),
                        findViewById(R.id.cheese23),
                        findViewById(R.id.cheese24) },
                {       findViewById(R.id.cheese30),
                        findViewById(R.id.cheese31),
                        findViewById(R.id.cheese32),
                        findViewById(R.id.cheese33),
                        findViewById(R.id.cheese34) }
                ,{
                findViewById(R.id.cheese40),
                findViewById(R.id.cheese41),
                findViewById(R.id.cheese42),
                findViewById(R.id.cheese43),
                findViewById(R.id.cheese44) }};




    }
    public void setSpeed(double speed){
        this.speed=speed;
    }


}
