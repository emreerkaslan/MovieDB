package com.erkaslan.moviedb.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.erkaslan.moviedb.data.Movie;
import java.util.ArrayList;
import com.erkaslan.moviedb.R;

public class MovieListAdapter extends RecyclerView.Adapter {
    ArrayList<Movie> movieList;
    Context context;

    public MovieListAdapter(Context context, ArrayList<Movie> movieList) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieListHolder(view, context);
    }

    //Creates a movie and puts movie name and sets a click listener
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Movie movie = (Movie) movieList.get(position);
        ((MovieListHolder) holder).bind(movie);

        ((MovieListHolder) holder).cardMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", movie.getTitle());
                bundle.putString("imdbID", movie.getImdbId());
                bundle.putString("poster", movie.getPoster());
                bundle.putString("year", movie.getYear());
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_detailFragment, bundle);
            }
        });


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieListHolder extends RecyclerView.ViewHolder{
        TextView textTitle,textYear;
        ImageView imageMovie;
        CardView cardMovie;
        Context context;

        public MovieListHolder(View view, Context context) {
            super(view);
            this.context = context;
            textTitle = (TextView) view.findViewById(R.id.textTitle);
            textYear = (TextView) view.findViewById(R.id.textYear);
            imageMovie = (ImageView) view.findViewById(R.id.imageMovie);
            cardMovie = (CardView) view.findViewById(R.id.cardMovie);
        }

        void bind(Movie movie) {

            textTitle.setText("Title: " + movie.getTitle());
            textYear.setText("Year: " + movie.getYear());
            Uri uri = Uri.parse(movie.getPoster());
            displayImageFromUrl(context, uri, imageMovie);

        }

    }

    //Loads and displays an image in circular shape
    public static void displayImageFromUrl(final Context context, final Uri uri, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .override(500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ic_nomovie_background)
                .into(imageView);/* Round image option
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                })*/
    }

}