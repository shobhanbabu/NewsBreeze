package com.assignment.newsbreeze.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.assignment.newsbreeze.databinding.ActivityMainBinding
import com.assignment.newsbreeze.ui.fragment.NewsHeadlinesFragmentDirections
import com.assignment.newsbreeze.viewmodel.NewsViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        viewModel.selectedArticle.observe(this, Observer {
            val direction = NewsHeadlinesFragmentDirections.actionHeadlinesToArticle(it)
            binding.navHostFragment.findNavController().navigate(direction)
        })
    }
}