package com.erkaslan.moviedb.data

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

//Singleton Volley class returns Volley instance and lasts for app lifetime
class MovieApi private constructor(context: Context) {

    companion object {
        @Volatile
        private var instance: MovieApi? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: MovieApi(context).also {
                    instance = it
                }
            }

        private const val TAG = "MovieApi"
    }

    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}