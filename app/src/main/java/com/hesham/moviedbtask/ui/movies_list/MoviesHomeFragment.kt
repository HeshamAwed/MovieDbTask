package com.hesham.moviedbtask.ui.movies_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.hesham.moviedbtask.R
import com.hesham.moviedbtask.base.BaseFragment
import com.hesham.moviedbtask.data.Constants
import com.hesham.moviedbtask.data.Constants.MOVIE_KEY_OBJECT
import com.hesham.moviedbtask.databinding.FragmentHomeMovieBinding

class MoviesHomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeMovieBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pager.adapter = MoviesHomeAdapter(this)
        TabLayoutMediator(binding.tabLayout,binding.pager){tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.title_top)
                1 -> tab.text = getString(R.string.title_popular)
                2 -> tab.text = getString(R.string.title_now)
            }
        }.attach()
    }

    class MoviesHomeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3
        val popularity = arrayListOf(Constants.TOP_RATED_MOVIE,Constants.POPULAR_MOVIE,Constants.NOW_PLAYING_MOVIE)
        override fun createFragment(position: Int): Fragment {
            val fragment = MoviesListFragment()
            fragment.arguments = Bundle().apply {
                putString(MOVIE_KEY_OBJECT, popularity[position])
            }
            return fragment
        }

    }

    override fun onResume() {
        super.onResume()
        changeActivityTitle(getString(R.string.app_name),false)
    }


}