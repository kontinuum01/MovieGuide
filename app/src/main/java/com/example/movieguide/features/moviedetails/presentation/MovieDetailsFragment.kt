package com.example.movieguide.features.moviedetails.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.common.di.findDependencies
import com.example.movieguide.R
import com.example.movieguide.databinding.FragmentMoviedetailsBinding
import com.example.movieguide.features.moviedetails.di.DaggerMovieDetailsComponent
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMoviedetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: MovieDetailsViewModelFactory

    private val viewModel: MovieDetailsViewModel by viewModels(
        factoryProducer = { factory }
    )

    private val movieTitle by lazy { arguments?.getString("movieTitle")!! }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        DaggerMovieDetailsComponent.factory()
            .create(
                dependencies = findDependencies(),
                movieTitle = movieTitle
            )
            .inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviedetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUI()
    }

    private fun subscribeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when {
                            state.isLoading -> showLoading()
                            state.hasError -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Error wile loading data",
                                    Toast.LENGTH_SHORT
                                ).show()

                                viewModel.errorHasShown()
                            }

                            else -> showProduct(detailsState = state.detailsState)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {
        hideAll()
        binding.progress.visibility = View.VISIBLE
    }

    private fun showProduct(detailsState: MovieDetailsState) {
        binding.progress.visibility = View.GONE

        binding.poster.apply {
            load(detailsState.poster)
            visibility = View.VISIBLE
        }

        binding.year.apply {
            text = getString(R.string.year, detailsState.year)
            visibility = View.VISIBLE
        }

        binding.genre.apply {
            text = getString(R.string.genre, detailsState.genre)
            visibility = View.VISIBLE
        }

        binding.imdbRating.apply {
            text = getString(R.string.imdb, detailsState.imdbRating)
            visibility = View.VISIBLE
        }

        binding.metascore.apply {
            text = getString(R.string.metascore, detailsState.metascore)
            visibility = View.VISIBLE
        }

        binding.runtime.apply {
            text = getString(R.string.runtime, detailsState.runtime)
            visibility = View.VISIBLE
        }

        binding.director.apply{
            text = context.getString(R.string.director, detailsState.director)
            visibility = View.VISIBLE
        }
        
        binding.actors.apply { 
            text = context.getString(R.string.actors, detailsState.actors)
            visibility = View.VISIBLE
        }
        
        binding.plot.apply{
            text = context.getString(R.string.plot, detailsState.plot)
            visibility = View.VISIBLE
        }

        binding.title.apply {
            text = detailsState.title
            visibility = View.VISIBLE
        }

    }

    private fun hideAll() {
        binding.progress.visibility = View.GONE
        binding.poster.visibility = View.GONE
        binding.title.visibility = View.GONE
        binding.genre.visibility = View.GONE
        binding.imdbRating.visibility = View.GONE
        binding.metascore.visibility = View.GONE

    }

}