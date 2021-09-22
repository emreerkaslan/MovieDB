package com.erkaslan.moviedb;

import android.app.Application;
import android.media.MediaRouter;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.erkaslan.moviedb.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class Repository implements MovieInterface, SingleMovieInterface {
    private static final String TAG = "REP";
    private Application application;
    private ArrayList<Movie> movieList = new ArrayList<>();
    private Movie singleMovie = new Movie("Title", "2020", "poster", "123");

    public Repository(Application application){
        this.application = application;
    }

    @Override
    public void onSuccess(ArrayList<Movie> movieList) {
    }

    @Override
    public void onSingleSuccess(Movie movie) {
    }

    //Sets url for omdb api gets the list and sends to interface on success
    public void setMovieList(String search, final MovieInterface movieInterface){
        String url = "https://www.omdbapi.com/?apikey=bc3334c&s=" + search.trim().replace(" ", "+");
        Log.v(TAG, "****" +url);

        movieList.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("Search");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject movie = jsonArray.getJSONObject(i);

                                String title = movie.getString("Title");
                                String year = movie.getString("Year");
                                String id = movie.getString("imdbID");
                                String poster = movie.getString("Poster");
                                movieList.add(new Movie(title, year, poster, id));
                            }
                            movieInterface.onSuccess(movieList);
                            Log.v(TAG, "****list set");
                            Log.v(TAG, "****list size" + String.valueOf(movieList.size()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        MovieApi.getInstance(application).addToRequestQueue(request);
    }


    //Sets url for omdb api gets the list and sends to interface on success
    public void setSingleMovie(String imdbID, final SingleMovieInterface singleMovieInterface){

        String url = "https://www.omdbapi.com/?apikey=bc3334c&i=" + imdbID.trim();
        Log.v(TAG, "****" +url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            singleMovie.setType(response.getString("Genre"));
                            singleMovie.setDirector(response.getString("Director"));
                            singleMovie.setTitle(response.getString("Title"));
                            singleMovie.setYear(response.getString("Year"));
                            singleMovie.setPoster(response.getString("Poster"));
                            singleMovie.setPlot(response.getString("Plot"));
                            singleMovie.setImdbID(imdbID);

                            singleMovieInterface.onSingleSuccess(singleMovie);

                            Log.v(TAG, "****movie set");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        MovieApi.getInstance(application).addToRequestQueue(request);
    }
}
