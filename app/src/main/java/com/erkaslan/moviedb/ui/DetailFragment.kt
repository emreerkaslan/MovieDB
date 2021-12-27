package com.erkaslan.moviedb.ui

import com.erkaslan.moviedb.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.erkaslan.moviedb.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar?.let { it.setNavigationOnClickListener(View.OnClickListener { requireActivity().onBackPressed() }) }
        val sharedViewModel = SharedViewModel(this.requireActivity().application)
        sharedViewModel.setSingleMovie(requireArguments().getString("imdbID"))

        //observer sets recylerview to movie set once movelist is changed
        sharedViewModel.singleMovie.observe(viewLifecycleOwner, { singleMovie ->
            binding.movie = singleMovie
            singleMovie!!.poster.let {
                Glide.with(requireContext()).asBitmap().load(it).override(500)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
                    .placeholder(R.drawable.ic_nomovie_background).into(binding.imageMovieDet!!)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}