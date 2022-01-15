package com.hesham.moviedbtask.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hesham.moviedbtask.domain.entities.Movie
import com.hesham.moviedbtask.databinding.ItemMovieBinding
import com.hesham.moviedbtask.ui.adapters.MovieAdapter.ViewHolder

class MovieAdapter(val call: (Movie) -> Unit) : PagingDataAdapter<Movie, ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.binding.movieImage.setOnClickListener { call(movie) }
            holder.bind(movie)
        }
    }

    class ViewHolder(var binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movie = item
            binding.executePendingBindings()
        }
    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldMovie: Movie,
                newMovie: Movie
            ) = oldMovie.id == newMovie.id

            override fun areContentsTheSame(
                oldMovie: Movie,
                newMovie: Movie
            ) = oldMovie == newMovie
        }
    }
}
