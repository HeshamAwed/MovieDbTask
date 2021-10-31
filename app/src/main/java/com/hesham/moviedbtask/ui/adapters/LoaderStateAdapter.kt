package com.hesham.moviedbtask.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hesham.moviedbtask.R
import com.hesham.moviedbtask.databinding.MovieLoadStateItemBinding

class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.create(parent, retry)
    }

    /**
     * view holder class for footer loader and error state handling
     */
    class LoaderViewHolder(val binding: MovieLoadStateItemBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }
        companion object {
            // get instance of the DoggoImageViewHolder
            fun create(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.movie_load_state_item, parent, false)
                val binding = MovieLoadStateItemBinding.bind(view)
                return LoaderViewHolder(binding, retry)
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.textView.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState is LoadState.Error
            binding.textView.isVisible = loadState is LoadState.Error
        }
    }
}
