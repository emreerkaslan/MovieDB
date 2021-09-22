package com.erkaslan.moviedb.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.erkaslan.moviedb.R;
import com.erkaslan.moviedb.data.Movie;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    public TextView textTitleDet, textYearDet, textDirDet, textPlotDet, textTypeDet;
    public Toolbar toolbar;

    public ImageView imageMovieDet;
    public DetailFragment() {
        super(R.layout.fragment_detail);
    }

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
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
        final View v = inflater.inflate(R.layout.fragment_detail, container, false);
        textPlotDet = (TextView) v.findViewById(R.id.textPlotDet);
        textYearDet = (TextView) v.findViewById(R.id.textYearDet);
        textDirDet = (TextView) v.findViewById(R.id.textDirDet);
        textTypeDet = (TextView) v.findViewById(R.id.textTypeDet);
        imageMovieDet = (ImageView) v.findViewById(R.id.imageMovieDet);

        toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle(getArguments().getString("title"));
        textYearDet.setText(getArguments().getString("year"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        SharedViewModel sharedViewModel = new SharedViewModel(this.getActivity().getApplication());
        sharedViewModel.setSingleMovie(getArguments().getString("imdbID"));

        //observer sets recylerview to movie set once movelist is changed
        sharedViewModel.getSingleMovie().observe(getViewLifecycleOwner(), new Observer<Movie>() {
            @Override
            public void onChanged(Movie singleMovie) {
                toolbar.setTitle(singleMovie.getTitle());
                textYearDet.setText("Year: " +singleMovie.getYear());
                textTypeDet.setText("Genre: " + singleMovie.getType());
                textPlotDet.setText("Story: " + singleMovie.getPlot());
                textDirDet.setText("Director: " + singleMovie.getDirector());
                MovieListAdapter.displayImageFromUrl(getContext(), Uri.parse(singleMovie.getPoster()), imageMovieDet);
            }
        });

        return v;
    }
}