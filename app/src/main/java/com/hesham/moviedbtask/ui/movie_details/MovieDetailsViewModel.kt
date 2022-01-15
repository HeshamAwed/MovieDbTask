package com.hesham.moviedbtask.ui.movie_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hesham.moviedbtask.domain.entities.Movie

class MovieDetailsViewModel : ViewModel() {
    val movie = MutableLiveData<Movie>()
}
