package com.bilalberek.movish.Ui.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bilalberek.movish.Adapters.MovishAdapter
import com.bilalberek.movish.R
import com.bilalberek.movish.Ui.MainActivity
import com.bilalberek.movish.ViewModels.MainViewmodel


class NewMovieFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainViewmodel
    private lateinit var adapter: MovishAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("backStack tracker", "buradayaım")
        recyclerView = view.findViewById(R.id.movieRecycler)

        viewModel = (activity as MainActivity).viewmodel
        viewModel.fetchMovies()
        adapter = MovishAdapter()

        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView.adapter = adapter

        viewModel._movieResults.observe(viewLifecycleOwner, Observer{
            Log.d("backStack tracker", "buradayaım2")
            adapter.differ.submitList(it)
            Log.d("backStack tracker", "${it[0].title}")
            Log.d("backStack tracker", "${it?.size}")
        })
    }

    companion object {

        fun newInstance() = NewMovieFragment()

    }
}