package com.erkaslan.moviedb.data

data class Movie(var title: String, var year: String, var poster: String, var imdbId: String) {
    var director: String? = null
    var plot: String? = null
    var type: String? = null
    fun setImdbID(imdbID: String) {
        imdbId = imdbID
    }
}