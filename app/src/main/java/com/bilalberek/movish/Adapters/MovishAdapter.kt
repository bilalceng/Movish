package com.bilalberek.movish.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bilalberek.movish.Model.MovieListResponse
import com.bilalberek.movish.R
import com.bilalberek.movish.Utils.Utils.Companion.POSTER_BASE_URL
import com.bumptech.glide.Glide

class MovishAdapter(): RecyclerView.Adapter<MovishAdapter.MovishViewholder>(){

    inner class MovishViewholder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var image = itemView.findViewById<ImageView>(R.id.movieImage)
        private var title = itemView.findViewById<TextView>(R.id.title)
       fun bind(item: MovieListResponse.Result){
           title.text = item.title
           Log.d("backStack tracker", "${item?.title}")
           val photoUrl = POSTER_BASE_URL + item.posterPath
           Glide.with(itemView.context)
               .load(photoUrl)
               .into(image)
       }
    }


    private val differCallback = object : DiffUtil.ItemCallback<MovieListResponse.Result>() {
        override fun areItemsTheSame(oldItem: MovieListResponse.Result, newItem: MovieListResponse.Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieListResponse.Result, newItem: MovieListResponse.Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovishViewholder {
       return MovishViewholder(
          LayoutInflater.from(parent.context)
           .inflate(R.layout.recycler_view_item,parent,false))

    }

    override fun onBindViewHolder(holder: MovishViewholder, position: Int) {
        var item  = differ.currentList[position]

       holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}