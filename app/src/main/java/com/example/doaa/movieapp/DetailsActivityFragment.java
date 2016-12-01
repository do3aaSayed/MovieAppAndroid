package com.example.doaa.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
public class DetailsActivityFragment extends Fragment {

    public DetailsActivityFragment() {
    }

    Bundle receivedBundle;
    JSONParser parser = new JSONParser();
    ListView trailersList,reviewsList;
    String apiKey = "api_key=cd816dc09b72c3ddab5b84c0949d0bdf";
    String baseURL = "https://api.themoviedb.org/3/movie/";
    List<Trailer> mTrailers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        receivedBundle = getBundle();
        final ImageView favorite = (ImageView) view.findViewById(R.id.favorite_image) ;
        final ImageView notFavorite = (ImageView) view.findViewById(R.id.not_favorite_image) ;
        ImageView poster = (ImageView)view.findViewById(R.id.selected_movie_poster);
        TextView name = (TextView)view.findViewById(R.id.movie_name);
        TextView date = (TextView)view.findViewById(R.id.movie_date);
        TextView rate = (TextView)view.findViewById(R.id.movie_rate);
        TextView overview = (TextView)view.findViewById(R.id.movie_overview);
        trailersList = (ListView) view.findViewById(R.id.movie_trailers);
        reviewsList = (ListView) view.findViewById(R.id.movie_reviews);

        Picasso.with(getActivity()).load(receivedBundle.get("poster").toString()).into(poster);
        name.setText(receivedBundle.get("name").toString());
        date.setText(receivedBundle.get("release date").toString());
        rate.setText(receivedBundle.get("rate").toString());
        overview.setText(receivedBundle.get("overview").toString());
        new FetchMovieTrailer().execute();
        new FetchMovieReviews().execute();
        favorite.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                notFavorite.setVisibility(View.VISIBLE);
                favorite.setVisibility(View.INVISIBLE);
            }
        });

        notFavorite.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                notFavorite.setVisibility(View.INVISIBLE);
                favorite.setVisibility(View.VISIBLE);
            }
        });

        trailersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTrailers.get(i).getTrailerUrl()));
                startActivity(intent);
            }
        });

        return view;
    }

    private Bundle getBundle() {
        Bundle b = getArguments();
        if(b == null){
            b = getActivity().getIntent().getExtras();
        }
        return b;
    }


    public class FetchMovieTrailer extends AsyncTask<String,Void,List<Trailer>> {

        @Override
        protected List<Trailer> doInBackground(String... strings) {

            String trailerJSONString = null;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(baseURL.concat(receivedBundle.get("id").toString()).concat("/videos?").concat(apiKey));

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                Log.v("JSON response", inputStream.toString());
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
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                trailerJSONString = buffer.toString();
                mTrailers = parser.parsingJSONResponseForTrailers(trailerJSONString);

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
            return mTrailers;
        }

        @Override
        protected void onPostExecute(List<Trailer> result) {
            super.onPostExecute(result);
            trailersList.setAdapter(new MoviesAdapter(getActivity(),result,true));
        }

    }

    public class FetchMovieReviews extends AsyncTask<String,Void,List<Reviews>> {

        @Override
        protected List<Reviews> doInBackground(String... strings) {

            List<Reviews> mReviews = new ArrayList<>();
            String reviewsJSONString = null;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(baseURL.concat(receivedBundle.get("id").toString()).concat("reviews?").concat(apiKey));

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

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
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                reviewsJSONString = buffer.toString();
                mReviews = parser.parsingJSONResponseForReviews(reviewsJSONString);

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
            return mReviews;
        }

        @Override
        protected void onPostExecute(List<Reviews> result) {
            super.onPostExecute(result);
            reviewsList.setAdapter(new MoviesAdapter(getActivity(),result,"true"));
        }
    }
}
