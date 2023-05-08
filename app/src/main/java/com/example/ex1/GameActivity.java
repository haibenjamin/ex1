package com.example.ex1;

import com.example.ex1.Logic.gameManager;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

public class GameActivity extends AppCompatActivity {

     final int ROWS=5;
     final int COLS=5;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] player;
    private ShapeableImageView[][] obstacles;
    private FloatingActionButton fabLeft,fabRight;
    private FloatingActionButton[] fab;
    int currPlayerPos;
    private gameManager gm;
    private Timer1 timer;

    public static final String SPEED = "KEY_SPEED";
    private double speed;



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
        gm= new gameManager(ROWS,COLS);
        gm.setContext(getApplicationContext());
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gm.setVibrator(v);
        Intent prevIntent = getIntent();
        speed=prevIntent.getDoubleExtra("KEY_SPEED",1.0);
        timer=new Timer1(gm, ROWS, COLS, obstacles,main_IMG_hearts,speed);
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
                { findViewById(R.id.obstacle00),
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


        ;

    }
    public void setSpeed(double speed){
        this.speed=speed;
    }
}
