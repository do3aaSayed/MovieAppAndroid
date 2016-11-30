package com.example.doaa.movieapp;

/**
 * Created by doaa on 11/24/2016.
 */

public class Movie {

    private  int movieId;
    private String movieName;
    private String moviePoster;
    private String movieOverView;
    private Double movieUserRating;
    private String movieReleaseDate;


    public String getMovieName() {
        return movieName;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieOverView() {
        return movieOverView;
    }

    public Double getMovieUserRating() {
        return movieUserRating;
    }

    public String  getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public void setMovieUserRating(Double movieUserRating) {
        this.movieUserRating = movieUserRating;
    }
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setMovieOverView(String movieOverView) {
        this.movieOverView = movieOverView;
    }

    public void setMovieReleaseDate(String  movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

}
