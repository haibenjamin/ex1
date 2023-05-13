package com.example.ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ex1.Fragments.ListFragment;
import com.example.ex1.Fragments.MapFragment;
import com.example.ex1.Interface.CallBackList;
import com.example.ex1.Logic.DataManager;
import com.example.ex1.Utillities.ScoreAdapter;
import com.example.ex1.Utillities.SignalGenerator;

import java.util.ArrayList;
import java.util.Set;

public class ScoresActivity extends AppCompatActivity {

private RecyclerView rv;
    private ListFragment fragment_list;
    private MapFragment fragment_map;
    private FrameLayout scores_FRAME_map;
    //private FrameLayout listStar_LAY_list;
    private CallBackList callBackList;

    public void setCallBack_sendClick(CallBackList callBackList) {
        this.callBackList = callBackList;
    }
    private void sendScoreCoordinates(double latitude,double longitude,String name) {
        if (callBackList != null)
            callBackList.rowSelected(longitude,latitude,name);
    }
private ArrayList<Score> scores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Glide
                .with(this)
                .load("https://images.pexels.com/photos/1450082/pexels-photo-1450082.jpeg")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background);

        findViews();
        initViews();
        fragment_list = new ListFragment();
        fragment_map = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.scores_FRAME_map,fragment_map).commit();





    }



    private void findViews() {
        rv=findViewById(R.id.scores_LST_scores);

        scores_FRAME_map = findViewById(R.id.scores_FRAME_map);
      //  listStar_LAY_list = findViewById(R.id.listStar_LAY_list);
    }
    private void initViews() {
        ScoreAdapter scoreAdapter = new ScoreAdapter(DataManager.getInstance().getScores());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setAdapter(scoreAdapter);
        rv.setLayoutManager(linearLayoutManager);
        setCallBack_sendClick(callBackList);
        scoreAdapter.setScoreCallback(new CallBackList() {
            @Override
            public void rowSelected(double longitude, double latitude, String playerName) {
                SignalGenerator.getInstance().toast(""+playerName+longitude+ " "+latitude, Toast.LENGTH_SHORT);
             sendScoreCoordinates(longitude, latitude, playerName);
            }

            @Override
            public void clearListClicked() {

            }
        });
    }

    public void addScore(Score score){
        this.scores.add((score));
    }


}