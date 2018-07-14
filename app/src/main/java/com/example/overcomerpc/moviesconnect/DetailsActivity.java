package com.example.overcomerpc.moviesconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    // The views
    @BindView(R.id.title_content)
    TextView tv_movie_title;
    @BindView(R.id.release_date_content) TextView tv_release_date;
    @BindView(R.id.vote_average_content) TextView tv_average_vote;
    @BindView(R.id.overview_content) TextView tv_overview;
    @BindView(R.id.image_posterpath)
    ImageView iv_movie_poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setupDetailsUI();
    }

    // Method for setting up the details display
    private void setupDetailsUI(){
        // Receive intent from main activity
        Intent intent = getIntent();
        String posterUrl = intent.getStringExtra(Constants.POSTER_PATH);
        String movieTitle = intent.getStringExtra(Constants.TITLE);
        String voteAverage = intent.getStringExtra(Constants.VOTE_AVERAGE);
        String releaseDate = intent.getStringExtra(Constants.RELEASE_DATE);
        String synopsis = intent.getStringExtra(Constants.OVERVIEW);

        // Fill the texts and images with values from intent
        Glide.with(this)
                .load(posterUrl)
                .into(iv_movie_poster);

        tv_movie_title.setText(movieTitle);
        tv_average_vote.setText(voteAverage);
        tv_release_date.setText(releaseDate);
        tv_overview.setText(synopsis);
    }
}
