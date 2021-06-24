package com.assignment.newsbreeze.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.assignment.newsbreeze.databinding.FragmentNewsArticleBinding
import com.assignment.newsbreeze.models.Article
import com.assignment.newsbreeze.viewmodel.NewsViewModel

class NewsArticleFragment : Fragment() {
    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentNewsArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsArticleBinding.inflate(layoutInflater)
        Log.d(
            "Shoban",
            requireArguments().getParcelable<Article>("newsArticle").toString()
        )
        return binding.root
    }
}