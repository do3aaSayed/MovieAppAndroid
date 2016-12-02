package com.example.doaa.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements SelectedMovieListener{

    boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MainActivityFragment mMainFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mMainFragment.setListener(this);
        if(findViewById(R.id.details)!= null){
            mTwoPane = true;
        }
    }


    @Override
    public void setSelectedMovie(Movie selectedMovie) {
        // case phone
        DatabaseHelper db = new DatabaseHelper(this);
        if(!mTwoPane){
            Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
            boolean isFavorite = db.isFavorite(selectedMovie.getMovieId());
            intent.putExtra("id",selectedMovie.getMovieId());
            intent.putExtra("name",selectedMovie.getMovieName());
            intent.putExtra("rate",selectedMovie.getMovieUserRating());
            intent.putExtra("release date",selectedMovie.getMovieReleaseDate());
            intent.putExtra("overview",selectedMovie.getMovieOverView());
            intent.putExtra("poster",selectedMovie.getMoviePoster());
            intent.putExtra("favorite",isFavorite);
            startActivity(intent);
        }
        else{

        }
    }
}
