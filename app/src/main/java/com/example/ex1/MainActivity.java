package com.example.ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ex1.Logic.DataManager;
import com.example.ex1.Utillities.MySharedPreferences;
import com.example.ex1.Utillities.SignalGenerator;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons;
    private AppCompatEditText main_ET_name;
    GameActivity game = new GameActivity();
    final double SLOW = 1;
    final double FAST = 0.4;
    final int ARROWS_MODE=0;
    final int SENSORS_MODE=1;
    private double speed=1.0;
    private int mode=ARROWS_MODE;
    private String name;
    private SignalGenerator signalGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();


        Glide
                .with(this)
                .load("https://images.pexels.com/photos/1450082/pexels-photo-1450082.jpeg")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background);
buttons[0].setOnClickListener(new View.OnClickListener() {
    //StartButton
    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, GameActivity.class);
        i.putExtra(GameActivity.SPEED,speed);
        i.putExtra(GameActivity.NAME,main_ET_name.getText().toString());
        i.putExtra(GameActivity.MODE,mode);
        startActivity(i);
    }
});
buttons[1].setOnClickListener(new View.OnClickListener() {
    //HighScoresButton
    @Override
    public void onClick(View v) {

        startActivity(new Intent(MainActivity.this, ScoresActivity.class));

    }
});
buttons[2].setOnClickListener(new View.OnClickListener() {
    //FastButton
    @Override
    public void onClick(View v) {
        speed=FAST;
        String text = "fast mode "+ speed ;
        int duration = Toast.LENGTH_SHORT;
        signalGenerator.getInstance().toast(text,duration);
    }
});

        buttons[3].setOnClickListener(new View.OnClickListener() {
            //SlowButton
            @Override
            public void onClick(View v) {
                speed=SLOW;
                String text = "slow mode "+speed;
                int duration = Toast.LENGTH_SHORT;
                signalGenerator.getInstance().toast(text,duration);
            }
        });
        buttons[5].setOnClickListener(new View.OnClickListener() {
            //SensorButton
            @Override
            public void onClick(View v) {
                mode=SENSORS_MODE;
               signalGenerator.getInstance().toast("sensors mode",Toast.LENGTH_SHORT);

            }
        });
        buttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode=ARROWS_MODE;
                signalGenerator.getInstance().toast("arrows mode",Toast.LENGTH_SHORT);

            }
        });



            }

    @Override
    protected void onStop() {
        super.onStop();


    }



    private void findViews() {

        buttons=new Button[]{
                findViewById(R.id.startGame),  //button[0]
                findViewById(R.id.highScores),//button[1]
                findViewById(R.id.fast)//button[2]
                ,findViewById(R.id.slow),//button[3]
                findViewById(R.id.arrowsMode),//button[4]
                findViewById(R.id.sensorMode)} ;//button[5]
        main_ET_name=findViewById(R.id.main_ET_name);

    }
}