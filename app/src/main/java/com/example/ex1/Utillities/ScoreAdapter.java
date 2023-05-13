package com.example.ex1.Utillities;

import com.example.ex1.Interface.CallBackList;

import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex1.Interface.CallBackList;
import com.example.ex1.R;
import com.example.ex1.Score;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private ArrayList<Score> scores;

    public ScoreAdapter(ArrayList<Score> scores) {

        this.scores = scores;
    }
    private CallBackList callBackList;

    public void setScoreCallback(CallBackList callBackList) {
        this.callBackList = callBackList;
    }


    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        ScoreViewHolder scoreViewHolder = new ScoreViewHolder(view);
        return scoreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = getItem(position);
        holder. score_TXT_name.setText(""+score.getName());
        holder. score_TXT_points.setText(""+score.getPoints());
        holder. score_TXT_distance.setText(""+score.getDistance() + "");



    }

    private Score getItem(int position) {
        return this.scores.get(position);
    }

    @Override
    public int getItemCount() {
        return this.scores == null ? 0 : this.scores.size();
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {

    private AppCompatTextView score_TXT_name;
    private AppCompatTextView score_TXT_distance;
    private AppCompatTextView score_TXT_points;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            score_TXT_name =    itemView.findViewById(R.id.scoreName);
            score_TXT_distance = itemView.findViewById(R.id.scoreDistance);
            score_TXT_points=itemView.findViewById(R.id.scorePoints);

            itemView.setOnClickListener(v -> {
                if (callBackList != null)
                   callBackList.rowSelected(getItem(getAdapterPosition()).getLongitude(),getItem(getAdapterPosition()).getLongitude(),getItem(getAdapterPosition()).getName());
            });

        }
        }


}




