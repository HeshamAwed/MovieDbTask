package com.hesham.moviedbtask.ui.movies_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import com.hesham.moviedbtask.R
import com.hesham.moviedbtask.base.BaseFragment
import com.hesham.moviedbtask.data.Constants.MOVIE_KEY_OBJECT
import com.hesham.moviedbtask.databinding.FragmentListMoviesBinding
import com.hesham.moviedbtask.ui.adapters.LoaderStateAdapter
import com.hesham.moviedbtask.ui.adapters.MovieAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : BaseFragment() {

    private val moviesListViewModel: MoviesListViewModel by viewModel()
    private var popularity:String? = null

    private var _binding: FragmentListMoviesBinding? = null
    private val binding get() = _binding!!

    val movieAdapter = MovieAdapter(){
        val action = MoviesHomeFragmentDirections.goToMovieDetailsFragment(it)
        activity?.findNavController(R.id.nav_host_fragment_activity_main)?.navigate(action)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListMoviesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf {it.containsKey(MOVIE_KEY_OBJECT)}?.apply {
            popularity = getString(MOVIE_KEY_OBJECT)
        }
        binding.refreshLayout.setOnRefreshListener {
            moviesListViewModel.doRefreshMovies()
        }
        val loadStateAdapter = LoaderStateAdapter{movieAdapter.retry()}
        binding.recyclerView.adapter = movieAdapter.withLoadStateFooter(loadStateAdapter)
        movieAdapter.addLoadStateListener {
            moviesListViewModel.handleLoadStates(it,movieAdapter.itemCount)
        }
        moviesListViewModel.apply {
            binding.viewModel = this

            errorMessage.observe(viewLifecycleOwner, {
                handleErrorMessage(it)
            })
            noInternetConnectionEvent.observe(viewLifecycleOwner, {
                handleErrorMessage(getString(R.string.no_internet_connection))
            })
            connectTimeoutEvent.observe(viewLifecycleOwner, {
                handleErrorMessage(getString(R.string.connect_timeout))
            })
            forceUpdateAppEvent.observe(viewLifecycleOwner, {
                handleErrorMessage(getString(R.string.force_update_app))
            })
            serverMaintainEvent.observe(viewLifecycleOwner, {
                handleErrorMessage(getString(R.string.server_maintain_message))
            })
            unknownErrorEvent.observe(viewLifecycleOwner, {
                handleErrorMessage(getString(R.string.unknown_error))
            })
        }
    }

    override fun onResume() {
        super.onResume()
        popularity?.let {
            lifecycleScope.launch {
                moviesListViewModel.getMovies(it).collectLatest {
                    movieAdapter.submitData(it)
                }
            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}