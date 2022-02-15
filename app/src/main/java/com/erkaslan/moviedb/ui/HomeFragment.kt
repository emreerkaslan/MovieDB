package com.erkaslan.moviedb.ui

import com.erkaslan.moviedb.R
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.erkaslan.moviedb.data.Movie
import com.erkaslan.moviedb.databinding.FragmentHomeBinding
import java.util.ArrayList

class HomeFragment : Fragment() {

    private var movieAdapter: RecyclerView.Adapter<*>? = null
    private var movieLayoutManager: RecyclerView.LayoutManager? = null
    var movieList: ArrayList<Movie>? = null
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView(binding.recylerMovie)
        val sharedViewModel = SharedViewModel(this.requireActivity().application)

        if (binding.inputMovie.text.toString().isEmpty()) {
            binding.recylerMovie.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        } else {
            sharedViewModel.setMovieList(binding.inputMovie.text.toString())
            binding.textNotFound.visibility = View.INVISIBLE
            binding.recylerMovie.visibility = View.VISIBLE
        }

        //Search button click sets movie set
        binding.buttonSearch.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            if (binding.inputMovie.text.toString().isEmpty()) {
                binding.recylerMovie.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.textNotFound.text =
                    "Type in something, millions of fascinating movies are out there"
                binding.textNotFound.visibility = View.VISIBLE
            } else {
                sharedViewModel.setMovieList(binding.inputMovie.text.toString())
                binding.textNotFound.visibility = View.INVISIBLE
                binding.recylerMovie.visibility = View.VISIBLE
            }
        }

        //observer sets recylerview to movie set once movelist is changed
        sharedViewModel.moviesAll.observe(viewLifecycleOwner, { moviesAll ->
            binding.progressBar.visibility = View.GONE
            if (moviesAll?.size == 0) {
                binding.recylerMovie.visibility = View.GONE
                binding.textNotFound.text = "No movie found...are you sure about the title?"
                binding.textNotFound.visibility = View.VISIBLE
            } else {
                binding.textNotFound.visibility = View.INVISIBLE
                movieAdapter = context?.let {
                    MovieListAdapter(it, moviesAll ?: arrayListOf<Movie>(), object : MovieListAdapter.ItemClickListener {
                        override fun itemClick(position: Int, movie: Movie) {
                            val bundle = Bundle()
                            bundle.putString("title", movie.title)
                            bundle.putString("imdbID", movie.imdbId)
                            bundle.putString("poster", movie.poster)
                            bundle.putString("year", movie.year)
                            Navigation.findNavController(binding.root)
                                .navigate(R.id.action_homeFragment_to_detailFragment, bundle)
                        }
                    })
                }
                binding.recylerMovie.adapter = movieAdapter
                binding.recylerMovie.visibility = View.VISIBLE
                movieAdapter?.let { it.notifyDataSetChanged() }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = activity
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Sets up the movie, adapter etc.
    private fun initializeRecyclerView(movie: RecyclerView) {
        movieList = ArrayList()
        movie.isNestedScrollingEnabled = false
        movie.setHasFixedSize(false)
        movieLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        movie.layoutManager = movieLayoutManager
        movieAdapter = context?.let {
            MovieListAdapter(it, movieList!!, object : MovieListAdapter.ItemClickListener {
                override fun itemClick(position: Int, movie: Movie) {

                }
            })
        }
        movie.adapter = movieAdapter
    }

    companion object {
        private const val TAG = "Home"
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}