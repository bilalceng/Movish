package com.bilalberek.movish.Ui.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.bilalberek.movish.R
import com.bilalberek.movish.Ui.MainActivity
import com.bilalberek.movish.Utils.Utils
import com.bilalberek.movish.ViewModels.MainViewmodel
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout

class MovieInfoFragment : Fragment() {
    private lateinit var overViewText: TextView
    private lateinit var viewModel: MainViewmodel
    private lateinit var movieImage: ImageView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var pointText: TextView
    private lateinit var yearText: TextView
    private lateinit var genreText: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_info, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewmodel
        toolbar = view.findViewById(R.id.actualToolbar)
        movieImage = view.findViewById(R.id.movieImage)
        collapsingToolbar = view.findViewById(R.id.collapsingToolbar)
        overViewText = view.findViewById(R.id.overView)
        pointText = view.findViewById(R.id.point)
        yearText = view.findViewById(R.id.year)
        genreText = view.findViewById(R.id.genre)



        var result = viewModel.activeResult

        var activity = requireActivity() as AppCompatActivity

        activity.setSupportActionBar(toolbar)

        result?.let { result ->

            val photoUrl = Utils.POSTER_BASE_URL + result.posterPath
            overViewText.text = "subject: \n\n ${result.overview}"

            genreText.text = "Vote count: ${result.voteCount.toString()}"
            yearText.text = "Release date: ${result.releaseDate}"
            pointText.text = "Imdb Point: ${result.voteAverage}"
            collapsingToolbar.title = result.title
            Glide.with(this)
                .load(photoUrl)
                .into(movieImage)
        }

    }



    companion object{
        fun newInstance() = MovieInfoFragment()
    }
}