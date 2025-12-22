package com.example.movieguide.features.moviefavorites.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.movieguide.databinding.ItemFavoritesMovieBinding
import com.example.movieguide.features.moviefavorites.presentation.FavoriteState

class FavoriteAdapter (
    private val onItemClicked: (String) -> Unit,
) : ListAdapter<FavoriteState, FavoriteHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            binding = ItemFavoritesMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClicked = onItemClicked,
            )
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val entity = getItem(position)
        entity?.let {
            holder.bind(entity)

        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FavoriteState>() {

        override fun areItemsTheSame(oldItem: FavoriteState, newItem: FavoriteState): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: FavoriteState, newItem: FavoriteState): Boolean {
            return oldItem == newItem
        }
    }
}