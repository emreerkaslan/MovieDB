package com.erkaslan.moviedb.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erkaslan.moviedb.data.Movie
import com.erkaslan.moviedb.data.MovieInterface
import com.erkaslan.moviedb.data.Repository
import com.erkaslan.moviedb.data.SingleMovieInterface
import java.util.ArrayList

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private var _moviesAll: MutableLiveData<ArrayList<Movie>?> = MutableLiveData()

    val moviesAll: MutableLiveData<ArrayList<Movie>?>
            get() = _moviesAll

    private var _singleMovie: MutableLiveData<Movie?> = MutableLiveData()

    val singleMovie: MutableLiveData<Movie?>
        get() = _singleMovie

    private val repository: Repository

    fun setMovieList(search: String?) {
        repository.setMovieList(search!!, object : MovieInterface {
            override fun onSuccess(movieList: ArrayList<Movie>) {
                Log.v(TAG, "****LIST " + movieList!!.size.toString())
                moviesAll.value = movieList
            }
        })
    }

    fun setSingleMovie(imdbID: String?) {
        repository.setSingleMovie(imdbID!!, object : SingleMovieInterface {
            override fun onSingleSuccess(movie: Movie) {
                Log.v(TAG, "****LIST in OnSuccess")
                singleMovie.value = movie
            }
        })
    }

    companion object {
        private const val TAG = "HomeVM"
    }

    init {
        repository = Repository(application)
    }
}