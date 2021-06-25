package com.assignment.newsbreeze.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.assignment.newsbreeze.databinding.FragmentNewsArticleBinding

class NewsArticleFragment : Fragment() {
    private lateinit var binding: FragmentNewsArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsArticleBinding.inflate(layoutInflater)
        binding.activity = context as Activity?
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.article = requireArguments().getParcelable("newsArticle")
    }
}