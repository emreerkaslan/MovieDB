package com.erkaslan.moviedb.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.erkaslan.moviedb.MovieInterface;
import com.erkaslan.moviedb.Repository;
import com.erkaslan.moviedb.SingleMovieInterface;
import com.erkaslan.moviedb.data.Movie;

import java.util.ArrayList;

public class SharedViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<Movie>> moviesAll = new MutableLiveData<ArrayList<Movie>>();
    MutableLiveData<Movie> singleMovie = new MutableLiveData<Movie>();
    private static final String TAG = "HomeVM";
    private Repository repository;


    public SharedViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

    }

    public MutableLiveData<ArrayList<Movie>> getMovieList(){
        return moviesAll;
    }

    public void setMovieList(String search) {

        repository.setMovieList(search, new MovieInterface() {
            @Override
            public void onSuccess(ArrayList<Movie> movieList) {
                //return movieList;
                Log.v(TAG, "****LIST in ONSuccess");
                Log.v(TAG, "****LIST " + String.valueOf(movieList.size()));
                moviesAll.postValue(movieList);
            }
        });
    }

    public MutableLiveData<Movie> getSingleMovie() {return singleMovie;}

    public void setSingleMovie(String imdbID) {

        repository.setSingleMovie(imdbID, new SingleMovieInterface() {
            @Override
            public void onSingleSuccess(Movie movie) {
                Log.v(TAG, "****LIST in ONSuccess");
                singleMovie.postValue(movie);
            }
        });
    }

}