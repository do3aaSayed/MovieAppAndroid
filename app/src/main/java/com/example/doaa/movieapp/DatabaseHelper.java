package com.example.doaa.movieapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doaa on 11/30/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    String moviesTable = "movies";
    String id = "ID";
    String moviePoster = "poster";
    String movieName = "movieName";
    String rate = "rating";
    String date = "releaseDate";
    String overview = "movieOverview";
    private static final String  databaseName = "favorite Movies";
    private static final int versionCode = 1;
    Context context;

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, versionCode);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS " + moviesTable + "(" + id + " integer primary key," + movieName + " text," + moviePoster
                + " text," + rate + " double," + date + " text," + overview + " text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query;
        query = "DROP TABLE IF EXISTS " + moviesTable;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public void addFavoriteMovie(Bundle movieBundle) {
        SQLiteDatabase fMovies = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(id, movieBundle.get("id").toString());
        values.put(movieName, movieBundle.get("name").toString());
        values.put(moviePoster, movieBundle.get("poster").toString());
        values.put(rate, movieBundle.get("rate").toString());
        values.put(date,movieBundle.get("release date").toString());
        values.put(overview, movieBundle.get("overview").toString());

        // Inserting Row
        fMovies.insert(moviesTable, null, values);
        fMovies.close(); // Closing database connection
    }

    public List<Movie> getAllMovies() {
        // Select All Query
        List<Movie> favourites = new ArrayList<>();
        SQLiteDatabase fMovies = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + moviesTable;
        Cursor cursor = fMovies.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setMovieId(cursor.getInt(0));
                movie.setMovieName(cursor.getString(1));
                movie.setMoviePoster(cursor.getString(2));
                movie.setMovieUserRating(cursor.getDouble(3));
                movie.setMovieReleaseDate(cursor.getString(4));
                movie.setMovieOverView(cursor.getString(5));
                favourites.add(movie);
            } while (cursor.moveToNext());
        }
        return favourites;
    }

    public void deleteMovieFromFavourites(int movieId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(moviesTable, id + " = ?",
                new String[] { String.valueOf(movieId)});
        sqLiteDatabase.close();
    }

    public boolean isFavorite(int id){
        String query = "SELECT * FROM "+moviesTable+" WHERE id="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount()!=0)
            return true;
        else
            return false;
    }
}
