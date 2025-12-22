package com.example.movieguide.features.moviefavorites.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieguide.databinding.ItemFavoritesMovieBinding
import com.example.movieguide.features.moviefavorites.presentation.FavoriteState

class FavoriteHolder(
    private val binding: ItemFavoritesMovieBinding,
    private val onItemClicked: (String) -> Unit,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(favoriteState: FavoriteState) {
        binding.poster.load(favoriteState.poster)
        binding.year.text = favoriteState.year
        binding.title.text = favoriteState.title
        binding.genre.text = favoriteState.genre
        binding.metascore.text = "Metascore:${favoriteState.metascore}"
        binding.imdbRating.text = "IMDb:${favoriteState.imdbRating}"

        binding.root.setOnClickListener {
            onItemClicked(favoriteState.title)
        }
    }
}