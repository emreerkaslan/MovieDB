package com.erkaslan.moviedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.erkaslan.moviedb.ui.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    public MainActivity() {

        super(R.layout.activity_main);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.nav_graph, HomeFragment.class, null)
                    .commit();
        }

         */
        
        
    }

}