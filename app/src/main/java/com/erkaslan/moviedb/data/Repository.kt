package com.erkaslan.moviedb.data

import android.app.Application
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.erkaslan.moviedb.data.MovieApi.Companion.getInstance
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import com.android.volley.VolleyError
import org.json.JSONException
import java.util.ArrayList

class Repository(private val application: Application) : MovieInterface, SingleMovieInterface {
    private val movieList = ArrayList<Movie>()
    private val singleMovie = Movie("Title", "2020", "poster", "123")
    override fun onSuccess(movieList: ArrayList<Movie>) {}
    override fun onSingleSuccess(movie: Movie) {}
    private val SEARCH_URL = "https://www.omdbapi.com/?apikey=bc3334c&s="
    private val SINGLE_MOVIE_URL = "https://www.omdbapi.com/?apikey=bc3334c&i="

    //Sets url for omdb api gets the list and sends to interface on success
    fun setMovieList(search: String, movieInterface: MovieInterface) {
        val url = SEARCH_URL + search.trim().replace(" ", "+")
        movieList.clear()
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            object : Response.Listener<JSONObject> {
                override fun onResponse(response: JSONObject) {
                    try {
                        val jsonArray = response.getJSONArray("Search")
                        for (i in 0 until jsonArray.length()) {
                            val movie = jsonArray.getJSONObject(i)
                            val title = movie.getString("Title")
                            val year = movie.getString("Year")
                            val id = movie.getString("imdbID")
                            val poster = movie.getString("Poster")
                            movieList.add(Movie(title, year, poster, id))
                        }
                        movieInterface.onSuccess(movieList)
                        Log.v(TAG, "****list size" + movieList.size.toString())
                    } catch (e: JSONException) {
                        val movieError = ArrayList<Movie>()
                        movieInterface.onSuccess(movieError)
                        e.printStackTrace()
                    }
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    val movieError = ArrayList<Movie>()
                    movieInterface.onSuccess(movieError)
                    error.printStackTrace()
                }
            })
        getInstance(application.applicationContext).addToRequestQueue(request)
    }

    //Sets url for omdb api gets the single movie details and sends to interface on success
    fun setSingleMovie(imdbID: String, singleMovieInterface: SingleMovieInterface) {
        val url = SINGLE_MOVIE_URL + imdbID.trim().replace(" ", "+")
        Log.v(TAG, "****$url")
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            object : Response.Listener<JSONObject> {
                override fun onResponse(response: JSONObject) {
                    try {
                        singleMovie.type = response.getString("Genre")
                        singleMovie.director = response.getString("Director")
                        singleMovie.title = response.getString("Title")
                        singleMovie.year = response.getString("Year")
                        singleMovie.poster = response.getString("Poster")
                        singleMovie.plot = response.getString("Plot")
                        singleMovie.setImdbID(imdbID)
                        singleMovieInterface.onSingleSuccess(singleMovie)
                        Log.v(TAG, "****movie set")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    error.printStackTrace()
                }
            })
        getInstance(application).addToRequestQueue(request)
    }

    companion object {
        private val TAG = "REPOSITORY"
    }
}