package com.example.doaa.movieapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doaa on 11/19/2016.
 */

public class JSONParser {

    public JSONParser() {

    }

    public List<Movie> parsingJSONResponse(String moviesJSONString) throws JSONException {

        JSONArray moviesArray;

        List<Movie> mMovies = new ArrayList<>();
        String moviePosterBaseUrl = "http://image.tmdb.org/t/p/w342";

        try {
            JSONObject mJSONObject = new JSONObject(moviesJSONString);
            moviesArray = mJSONObject.getJSONArray("results");

            for (int i = 0; i < moviesArray.length(); i++) {
                Movie mNewMovie = new Movie();
                JSONObject movieData = moviesArray.getJSONObject(i);
                mNewMovie.setMoviePoster(moviePosterBaseUrl.concat(movieData.getString("poster_path")));
                mNewMovie.setMovieId(movieData.getInt("id"));
                mNewMovie.setMovieName(movieData.getString("original_title"));
                mNewMovie.setMovieReleaseDate(movieData.getString("release_date"));
                mNewMovie.setMovieUserRating(movieData.getDouble("vote_average"));
                mNewMovie.setMovieOverView(movieData.getString("overview"));
                mMovies.add(mNewMovie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mMovies;
    }


    public List<Trailer> parsingJSONResponseForTrailers(String trailerJSONString) throws JSONException {

        JSONArray trailersArray;

        List<Trailer> mTrailers = new ArrayList<>();
        String movieTrailersBaseUrl = "https://www.youtube.com/watch?v=";

        try {
            JSONObject mJSONObject = new JSONObject(trailerJSONString);
            trailersArray = mJSONObject.getJSONArray("results");

            for (int i = 0; i < trailersArray.length(); i++) {
                Trailer mNewTrailer = new Trailer();
                JSONObject trailerData = trailersArray.getJSONObject(i);
                mNewTrailer.setTrailerKey(trailerData.getString("key"));
                mNewTrailer.setTrailerUrl(movieTrailersBaseUrl.concat(mNewTrailer.getTrailerKey()));
                mNewTrailer.setName(trailerData.getString("name"));
                mTrailers.add(mNewTrailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mTrailers;
    }

    public List<Reviews> parsingJSONResponseForReviews(String reviewsJSONString) throws JSONException {

        JSONArray reviewsArray;

        List<Reviews> mReviews = new ArrayList<>();

        try {
            JSONObject mJSONObject = new JSONObject(reviewsJSONString);
            reviewsArray = mJSONObject.getJSONArray("results");

            for (int i = 0; i < reviewsArray.length(); i++) {
                Reviews mNewReviews = new Reviews();
                JSONObject trailerData = reviewsArray.getJSONObject(i);
                mNewReviews.setAuthorName(trailerData.getString("author"));
                mNewReviews.setContent(trailerData.getString("content"));
                mReviews.add(mNewReviews);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mReviews;
    }
}
