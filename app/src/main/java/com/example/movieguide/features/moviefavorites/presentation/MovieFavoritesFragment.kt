package com.example.movieguide.features.moviefavorites.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.di.findDependencies
import com.example.movieguide.R
import com.example.movieguide.databinding.FragmentFavoritesListBinding
import com.example.movieguide.features.moviefavorites.adapter.FavoriteAdapter
import com.example.movieguide.features.moviefavorites.di.DaggerMovieFavoritesComponent
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieFavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels { favoriteViewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        DaggerMovieFavoritesComponent.factory()
            .create(dependencies = findDependencies())
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = FavoriteAdapter(

            onItemClicked = { movieTitle ->
                requireActivity().findNavController(R.id.nav_host_activity_main)
                    .navigate(
                        resId = R.id.action_main_to_details,
                        args = bundleOf("movieTitle" to movieTitle),
                    )
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        subscribeUI()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
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

                            else -> showMovieList(movieState = state.movieState)
                        }
                    }
                }
            }
        }
    }

    private fun showMovieList(movieState: List<FavoriteState>) {
        binding.progress.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        (binding.recyclerView.adapter as FavoriteAdapter).submitList(movieState)
    }

    private fun showLoading() {
        binding.progress.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}