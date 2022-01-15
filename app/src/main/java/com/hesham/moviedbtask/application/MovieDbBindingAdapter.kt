package com.hesham.moviedbtask.base

import android.view.View
import android.widget.ImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import coil.request.CachePolicy
import com.hesham.moviedbtask.R

@BindingAdapter(value = ["imageUrl"], requireAll = false)
fun setImageUrl(imageView: ImageView, url: String?) {
    imageView.load(url) {
        placeholder(R.drawable.loading)
        error(R.drawable.bg_default_image)
        memoryCachePolicy(CachePolicy.ENABLED)
    }
}

@BindingAdapter("adapter")
fun setRecyclerAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    adapter?.let {
        recyclerView.adapter = it
        recyclerView.setHasFixedSize(true)
    }
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.customRefreshing(refreshing: Boolean?) {
    isRefreshing = refreshing == true
}

@BindingAdapter(value = ["isLoading"])
fun ContentLoadingProgressBar.show(isLoading: Boolean?) {
    if (isLoading == true) show() else hide()
}

@BindingAdapter("app:visibleGone")
fun bindViewGone(view: View, b: Boolean) {
    if (b) view.visibility = View.VISIBLE
    else view.visibility = View.GONE
}
