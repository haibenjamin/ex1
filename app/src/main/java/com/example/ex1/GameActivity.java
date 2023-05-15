package com.example.ex1;

import com.example.ex1.Interface.CallBackPlaySound;
import com.example.ex1.Interface.CallBackTilt;
import com.example.ex1.Interface.CallBackUpdatePoints;
import com.example.ex1.Interface.gameOverCallable;
import com.example.ex1.Logic.DataManager;
import com.example.ex1.Logic.GameManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.Glide;
import com.example.ex1.Logic.StepDetector;
import com.example.ex1.Utillities.MyMediaPlayer;
import com.example.ex1.Utillities.MySharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

public class GameActivity extends AppCompatActivity  {

    public static final String NAME ="KEY_NAME" ;
    public static final String SPEED = "KEY_SPEED";
    public static final String MODE = "KEY_MODE";
    final int ARROWS_MODE=0;
    final int SENSORS_MODE=1;
    final int ROWS=5;
     final int COLS=5;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] player;
    private ShapeableImageView[][] obstacles;
    private ShapeableImageView[][] collecetables;
    private FloatingActionButton[] fab;
    private AppCompatTextView currPoints;
    int currPlayerPos;
    private GameManager gm;
    private Timer1 timer;
    private String name;
    private int mode;
    private double speed;
    private int score=0;
    private gameOverCallable gameOverCallable;
    private CallBackPlaySound callBackPlaySound;
    private StepDetector sensorManager;
    private CallBackUpdatePoints callBackUpdatePoints;

    public void setGameOverCallback(gameOverCallable gameOverCallable) {
        this.gameOverCallable = gameOverCallable;
    }
    public void setPlaySoundCallBack(CallBackPlaySound callBackPlaySound) {
        this.callBackPlaySound = callBackPlaySound;
    }
    public void setUpdateUICallBack(CallBackUpdatePoints callBackUpdatePoints){
        this.callBackUpdatePoints=callBackUpdatePoints;
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
                .placeholder(R.drawable.floor);

        initScreen(obstacles,player,main_IMG_hearts);
        setPlaySoundCallBack(new CallBackPlaySound() {
            @Override
            public void playSound() {
                MyMediaPlayer mp = new MyMediaPlayer();
                mp.playAudioFile(getApplicationContext(),R.raw.tom_laughing);
            }
        });
        setGameOverCallback(new gameOverCallable() {
            @Override
            public void GameOver() {
                timer.stopTime();
                finish();
               // ActivityCompat.requestPermissions(this, new String[]
              //          {Manifest.permission.}, 0);
               // LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
              //  @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                startActivity(new Intent(GameActivity.this, MainActivity.class));
                Log.d("SP:", MySharedPreferences.getInstance().getString("scoresList","-1"));
                if(DataManager.getInstance()!=null) {
                    String scoresJson = new Gson().toJson(DataManager.getInstance().getScores());
                    MySharedPreferences.getInstance().putString("scoresList", scoresJson);
                    String test=MySharedPreferences.getInstance().getString("scoresList","");
                    int i=5;
                }
            }
        });
        setUpdateUICallBack(new CallBackUpdatePoints() {
            @Override
            public void updatePointsUI(int points) {
                String newText = ""+points;
                currPoints.setText(newText);

            }
        });
        Intent prevIntent = getIntent();
        speed=prevIntent.getDoubleExtra("KEY_SPEED",1.0);
        name=prevIntent.getStringExtra("KEY_NAME");
        mode=prevIntent.getIntExtra("KEY_MODE",0);
        setMode(mode);
        if(name==null|| name.equals(""))
            name="player";
        gm= new GameManager(ROWS,COLS,gameOverCallable,callBackPlaySound,callBackUpdatePoints);
        gm.setName(name);
        timer=new Timer1(gm, ROWS, COLS,player,collecetables, obstacles,main_IMG_hearts,speed);
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

    private void setMode(int mode) {
        if(mode==ARROWS_MODE){
            fab[0].setVisibility(View.VISIBLE);
            fab[1].setVisibility(View.VISIBLE);
            if(sensorManager!=null)
                sensorManager.stop();

        }
        else{
            //mode==SENSOR_MODE
            sensorManager=new StepDetector(this, new CallBackTilt() {
                @Override
                public void updatePlayerPos( ) {

                    currPlayerPos= sensorManager.getPlayerPos();
                    gm.setCurrPlayerPos(currPlayerPos);
                    timer.refreshUI();
                    Log.d("playerpos",""+currPlayerPos);



                }

            });

            fab[0].setVisibility(View.GONE);
            fab[1].setVisibility(View.GONE);
            sensorManager.start();

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.stopTime();
        if(sensorManager!=null)
            sensorManager.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.startTime();
        if(sensorManager!=null)
            sensorManager.start();
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
        currPoints=findViewById(R.id.main_Text_score);




    }
    public void setSpeed(double speed){
        this.speed=speed;
    }


}
