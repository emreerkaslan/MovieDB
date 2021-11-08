package com.erkaslan.moviedb.ui;

import android.content.ClipData;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.erkaslan.moviedb.MovieApi;
import com.erkaslan.moviedb.R;
import com.erkaslan.moviedb.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment{

    private static final String TAG = "Home";

    public Button buttonSearch;
    public TextView textTitle, textYear, textNotFound;
    public EditText inputMovie;
    public ProgressBar progressBar;

    private RecyclerView movie;
    private RecyclerView.Adapter movieAdapter;
    private RecyclerView.LayoutManager movieLayoutManager;

    ArrayList<Movie> movieList;


    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        buttonSearch = (Button) v.findViewById(R.id.buttonSearch);
        textTitle = (TextView) v.findViewById(R.id.textTitle);
        textYear = (TextView) v.findViewById(R.id.textYear);
        textNotFound = (TextView) v.findViewById(R.id.textNotFound);
        inputMovie = (EditText) v.findViewById(R.id.inputMovie);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        movie = (RecyclerView) v.findViewById(R.id.recylerMovie);

        initializeRecyclerView();
        SharedViewModel sharedViewModel = new SharedViewModel(this.getActivity().getApplication());

        //Search button click sets movie set
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Log.v(TAG, "CLICKED");
                if(inputMovie.getText().toString().isEmpty()) {
                    movie.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    textNotFound.setText("Type in something, millions of fascinating movies are out there");
                    textNotFound.setVisibility(View.VISIBLE);
                }else{
                    sharedViewModel.setMovieList(inputMovie.getText().toString());
                    textNotFound.setVisibility(View.INVISIBLE);
                    movie.setVisibility(View.VISIBLE);
                }
            }
        });


        //observer sets recylerview to movie set once movelist is changed
        sharedViewModel.getMovieList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> moviesAll) {

                progressBar.setVisibility(View.GONE);

                if (moviesAll.size() == 0) {
                    Log.v(TAG, "****movie not found");
                    movie.setVisibility(View.GONE);
                    textNotFound.setText("No movie found...are you sure about the title?");
                    textNotFound.setVisibility(View.VISIBLE);
                }else{
                    Log.v(TAG, "onChanged: XXX");
                    Log.v(TAG, "movies number: " + String.valueOf(moviesAll.size()));
                    textNotFound.setVisibility(View.INVISIBLE);

                    movieAdapter = new MovieListAdapter(getContext(), moviesAll);
                    movie.setAdapter(movieAdapter);
                    movie.setVisibility(View.VISIBLE);
                    movieAdapter.notifyDataSetChanged();
                }
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    //Sets up the movie, adapter etc.
    private void initializeRecyclerView() {
        movieList = new ArrayList<>( );
        movie.setNestedScrollingEnabled(false);
        movie.setHasFixedSize(false);
        movieLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        movie.setLayoutManager(movieLayoutManager);
        movieAdapter = new MovieListAdapter(getContext(), movieList);
        movie.setAdapter(movieAdapter);
        Log.v(TAG, "****adapter initialized");
    }

}