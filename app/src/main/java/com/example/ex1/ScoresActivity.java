package com.example.ex1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.ex1.Logic.DataManager;
import com.example.ex1.Utillities.ScoreAdapter;

import java.util.Set;

public class ScoresActivity extends AppCompatActivity {

private RecyclerView rv;

private Set<Score> scores;
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

    }

    private void findViews() {
        rv=findViewById(R.id.scores_LST_scores);
    }
    private void initViews() {
        ScoreAdapter scoreAdapter = new ScoreAdapter(DataManager.getInstance().getScores());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setAdapter(scoreAdapter);
        rv.setLayoutManager(linearLayoutManager);
    }

    public void addScore(Score score){
        this.scores.add((score));
    }


}