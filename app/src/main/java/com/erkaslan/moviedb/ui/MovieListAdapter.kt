package com.erkaslan.moviedb.ui

import android.content.Context
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.erkaslan.moviedb.R
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.erkaslan.moviedb.data.Movie
import java.util.ArrayList

class MovieListAdapter(var context: Context, var movieList: ArrayList<Movie>, var itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ItemClickListener {
        fun itemClick(position: Int, movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.v("ADAPTER", "Home: onCreateViewHolder")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieListHolder(view, context)
    }

    //Creates a movie and puts movie name and sets a click listener
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movieList[position]
        (holder as MovieListHolder).bind(movie)

        holder.cardMovie.setOnClickListener { v ->
            itemClickListener.itemClick(position, movie)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    internal inner class MovieListHolder(view: View, var context: Context) : RecyclerView.ViewHolder(view) {
        var textTitle: TextView
        var textYear: TextView
        var imageMovie: ImageView
        var cardMovie: CardView
        fun bind(movie: Movie) {
            textTitle.text = "Title: " + movie.title
            textYear.text = "Year: " + movie.year
            val uri = Uri.parse(movie.poster)
            displayImageFromUrl(context, uri, imageMovie)
        }

        init {
            textTitle = view.findViewById<View>(R.id.textTitle) as TextView
            textYear = view.findViewById<View>(R.id.textYear) as TextView
            imageMovie = view.findViewById<View>(R.id.imageMovie) as ImageView
            cardMovie = view.findViewById<View>(R.id.cardMovie) as CardView
        }
    }

    companion object {
        //Loads and displays an image in rectangle shape
        fun displayImageFromUrl(context: Context?, uri: Uri?, imageView: ImageView?) {
            Glide.with(context!!)
                .asBitmap()
                .load(uri)
                .override(500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .placeholder(R.drawable.ic_nomovie_background)
                .into(imageView!!)
        }
    }
}