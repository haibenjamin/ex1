package com.example.ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ex1.Logic.sensorManager;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons;
    private AppCompatEditText main_ET_name;
    GameActivity game = new GameActivity();
    final double SLOW = 1;
    final double FAST = 0.4;
    private double speed=1.0;
    private String name;

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
    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, GameActivity.class);
        CharSequence text = ""+speed;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
        i.putExtra(GameActivity.SPEED,speed);
        i.putExtra(GameActivity.NAME,main_ET_name.getText().toString());
        startActivity(i);
    }
});
buttons[1].setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startActivity(new Intent(MainActivity.this, ScoresActivity.class));

    }
});
buttons[2].setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
speed=FAST;
        CharSequence text = "fast mode "+ speed ;
        int duration = Toast.LENGTH_SHORT;


        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }
});

        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speed=SLOW;
                CharSequence text = "slow mode "+speed;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            }
        });
        buttons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager sm = new sensorManager(getApplicationContext());

            }
        });



            }



    private void findViews() {

        buttons=new Button[]{findViewById(R.id.startGame),findViewById(R.id.highScores),findViewById(R.id.fast)
                ,findViewById(R.id.slow),findViewById(R.id.arrowsMode),findViewById(R.id.sensorMode)};
main_ET_name=findViewById(R.id.main_ET_name);

    }
}