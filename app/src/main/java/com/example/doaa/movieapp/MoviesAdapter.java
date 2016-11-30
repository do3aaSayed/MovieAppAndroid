package com.example.doaa.movieapp;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by doaa on 10/21/2016.
 */

public class MoviesAdapter extends BaseAdapter {

    private List<Movie> mMoviesList;

    Context context;
    List<Trailer> mTrailerList;
    List<Reviews> mReviewsList;
    Boolean isTrailer = false;
    String isReviews = "false";
    LayoutInflater inflater;


    public MoviesAdapter(){

    }

    public MoviesAdapter(FragmentActivity context, List<Movie> Movies){
        this.context = context;
        mMoviesList = Movies;
        inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public MoviesAdapter(FragmentActivity context,List<Trailer> Trailers,Boolean isTrailer){
        this.context = context;
        mTrailerList = Trailers;
        this.isTrailer = isTrailer;
        inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public MoviesAdapter(FragmentActivity context,List<Reviews> Reviews,String isReviews){
        this.context = context;
        mReviewsList = Reviews;
        this.isReviews = isReviews;
        inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(isTrailer == false && isReviews.equals("false"))
            return mMoviesList.size();
        else if(isTrailer)
            return mTrailerList.size();
        else if(isReviews.equals("true")){
            return mReviewsList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(isTrailer == false && isReviews.equals("false"))
            return mMoviesList.get(i).getMoviePoster();
        else if(isTrailer)
            return mTrailerList.get(i).getName();
        else if(isReviews.equals("true"))
            return mReviewsList.get(i);
        else
            return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(isTrailer == false && isReviews.equals("false")) {
            if (view == null) {
                view = inflater.inflate(R.layout.movies_grid_item, viewGroup, false);
            }
            ImageView poster = (ImageView) view.findViewById(R.id.movie_poster);
            Picasso.with(context).load(getItem(i).toString()).into(poster);
        }
        else if(isTrailer){
            view = inflater.inflate(R.layout.trailers_list_item, viewGroup, false);
            TextView trailerName = (TextView)view.findViewById(R.id.trailer_name);
            trailerName.setText(getItem(i).toString());
        }
        else if(isReviews.equals("true")){
            view = inflater.inflate(R.layout.reviews_list_item, viewGroup, false);
            TextView reviewerName = (TextView)view.findViewById(R.id.reviewer_name);
            TextView reviewContent = (TextView)view.findViewById(R.id.reviewer_name);
            reviewerName.setText(mReviewsList.get(i).getAuthorName());
            reviewContent.setText(mReviewsList.get(i).getContent());
        }
        return view;
    }
}
