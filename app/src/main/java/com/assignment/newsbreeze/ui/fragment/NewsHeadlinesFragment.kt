package com.assignment.newsbreeze.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.assignment.newsbreeze.databinding.NewsHeadlinesFragmentBinding
import com.assignment.newsbreeze.viewmodel.NewsViewModel

class NewsHeadlinesFragment : Fragment() {
    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: NewsHeadlinesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, cMainViewModelontainer: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsHeadlinesFragmentBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        return binding.root
    }
}