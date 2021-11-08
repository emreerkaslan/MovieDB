package com.erkaslan.moviedb;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.erkaslan.moviedb.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Singleton Volley class returns Volley instance and lasts for app lifetime
public class MovieApi {

    private static MovieApi instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    //TextView title, TextView director, TextView year
    private MovieApi(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MovieApi getInstance(Context context) {
        if (instance == null) {
            instance = new MovieApi(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    private static final String TAG = "MovieApi";


    public ArrayList<Movie> getMovieList(String url){

        ArrayList<Movie> movieList = new ArrayList<>();

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

        instance.addToRequestQueue(request);
        return movieList;
    }

}
