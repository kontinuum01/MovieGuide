package com.example.movieguide.features.movie.adapter

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieguide.R
import com.example.movieguide.databinding.ItemMovieBinding
import com.example.movieguide.features.movie.presentation.MovieState

class MovieHolder (
    private val binding: ItemMovieBinding,
    private val onItemClicked: (String) -> Unit,
    private val onAddToFavorites: (String) -> Unit,
    private val onRemoveFromFavorites: (String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movieState: MovieState) {
        binding.poster.load(movieState.poster)
        binding.year.text = movieState.year
        binding.title.text = movieState.title
        binding.genre.text = movieState.genre
        binding.metascore.text = "Metascore:${movieState.metascore}"
        binding.imdbRating.text = "IMDb:${movieState.imdbRating}"

        binding.root.setOnClickListener {
            onItemClicked(movieState.title)
        }

        bindFavorite(movieState)
    }

    fun bindFavorite(movieState: MovieState) {
        if (movieState.isFavorite) {
            binding.favorite.setImageResource(R.drawable.ic_favorite_on)
            binding.favorite.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.red))
            binding.favorite.setOnClickListener {
                onRemoveFromFavorites(movieState.title)
                binding.favorite.bump()
            }
        } else {
            binding.favorite.setImageResource(R.drawable.ic_favorite_off)
            binding.favorite.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.gray))
            binding.favorite.setOnClickListener {
                onAddToFavorites(movieState.title)
                binding.favorite.bump()
            }
        }
    }
}



