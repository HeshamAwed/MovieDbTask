package com.hesham.moviedbtask.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.hesham.moviedbtask.R
import com.hesham.moviedbtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.ivBack.setOnClickListener {
            findNavController(R.id.nav_host_fragment_activity_main).navigateUp()
        }
        setContentView(binding.root)
    }
    fun changeTitle(title: String, showBackButton: Boolean) {
        binding.tvTitle.text = title
        if (showBackButton)
            binding.ivBack.visibility = View.VISIBLE
        else
            binding.ivBack.visibility = View.GONE
    }
}
