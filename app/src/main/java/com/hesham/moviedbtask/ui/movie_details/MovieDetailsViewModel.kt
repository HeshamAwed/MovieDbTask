package com.hesham.moviedbtask.ui.movie_details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hesham.moviedbtask.data.model.MovieModel

class MovieDetailsViewModel : ViewModel() {
   val movie = MutableLiveData<MovieModel>()
}