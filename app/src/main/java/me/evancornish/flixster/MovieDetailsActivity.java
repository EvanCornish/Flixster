package me.evancornish.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import org.parceler.Parcels;

import me.evancornish.flixster.models.Movie;

public class MovieDetailsActivity extends AppCompatActivity {
    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movie= Parcels.unwrap(getIntent().getParcelableExtra((Movie.class.getSimpleName())));
        tvTitle=findViewById(R.id.tvTitle);
        tvOverview=findViewById(R.id.tvOverview);
        rbVoteAverage=findViewById(R.id.rbVoteAverage);
        Log.d("MovieDetailsActivity",String.format("Showing details for '%s'",movie.getTitle()));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        rbVoteAverage.setRating(movie.getVoteAverage().floatValue()/2);
    }
}
