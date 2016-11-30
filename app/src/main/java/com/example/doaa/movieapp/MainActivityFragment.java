package com.example.doaa.movieapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    GridView gridView;
    String menuSelectedItem;
    JSONParser parser = new JSONParser();
    private SelectedMovieListener mListener;
    String moviesType = null;
    String firstTimeURL;
    List<Movie> mMoviesList = new ArrayList<>();

    public MainActivityFragment() {

    }

    public void setListener(SelectedMovieListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_most_popular:
                menuSelectedItem = "Most popular";
                break;
            case R.id.action_top_rated:
                menuSelectedItem = "Top rated";
                break;
            case R.id.action_favorite:
                menuSelectedItem = "Favorites";
                break;
        }
        new FetchMoviesPostersTask().execute();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) view.findViewById(R.id.movies_posters);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie selectedMovie = mMoviesList.get(i);
                mListener.setSelectedMovie(selectedMovie);
            }
        });
        new FetchMoviesPostersTask().execute();
        return view;
    }


    public class FetchMoviesPostersTask extends AsyncTask<String,Void,List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {

            String moviesJSONString = null;
            String apiKey = "api_key=cd816dc09b72c3ddab5b84c0949d0bdf";
            firstTimeURL = "https://api.themoviedb.org/3/movie/popular?api_key=cd816dc09b72c3ddab5b84c0949d0bdf";
            String baseURL = "https://api.themoviedb.org/3/movie/";

           // Uri uri = Uri.parse(baseURL).buildUpon().appendQueryParameter("api_key",BuildConfig.MyMovieAppApiKey).build();

            if (menuSelectedItem != null)
                firstTimeURL = baseURL.concat(setMoviesType(menuSelectedItem)).concat(apiKey);

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(firstTimeURL);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                //String response = urlConnection.getResponseMessage();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }

                moviesJSONString = buffer.toString();
                mMoviesList = parser.parsingJSONResponse(moviesJSONString);

            } catch (IOException e) {
                Log.e("Fragment is blank", "Error ", e);
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Fragment is blank", "Error closing stream", e);
                    }
                }
            }
            return mMoviesList;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            super.onPostExecute(result);
            gridView.setAdapter(new MoviesAdapter(getActivity(),result));
        }

        public String setMoviesType(String selectedType)
        {
            if(selectedType == "Most popular") {
                moviesType = "popular?";
            }
            else if(selectedType == "Top rated"){
                moviesType = "top_rated?";
            }

            return moviesType;
        }
    }



}
