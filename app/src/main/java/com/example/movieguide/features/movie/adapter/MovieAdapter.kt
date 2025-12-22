package com.example.movieguide.features.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.movieguide.databinding.ItemMovieBinding
import com.example.movieguide.features.movie.presentation.MovieState

class MovieAdapter (
    private val onAddToFavorites: (String) -> Unit,
    private val onRemoveFromFavorites: (String) -> Unit,
    private val onItemClicked: (String) -> Unit,
) : ListAdapter<MovieState, MovieHolder>(DiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            binding = ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClicked = onItemClicked,
            onAddToFavorites = onAddToFavorites,
            onRemoveFromFavorites = onRemoveFromFavorites,
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val entity = getItem(position)
        entity?.let {
            holder.bind(entity)

        }
    }
    override fun onBindViewHolder(
        holder: MovieHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] == true) {
                holder.bindFavorite(getItem(position))
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<MovieState>() {

    override fun areItemsTheSame(oldItem: MovieState, newItem: MovieState): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: MovieState, newItem: MovieState): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: MovieState, newItem: MovieState): Any? {
        if (oldItem.isFavorite != newItem.isFavorite) {
            return true
        }
        return null
    }
}