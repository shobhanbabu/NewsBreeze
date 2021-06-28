package com.assignment.newsbreeze.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.assignment.newsbreeze.R
import com.assignment.newsbreeze.databinding.FragmentNewsArticleBinding
import com.assignment.newsbreeze.utils.showToast
import com.assignment.newsbreeze.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsArticleFragment : Fragment() {
    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentNewsArticleBinding
    private val args: NewsArticleFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsArticleBinding.inflate(layoutInflater)
        binding.activity = activity
        binding.article = args.newsArticle
        binding.save.setOnClickListener(View.OnClickListener {
            viewModel.saveArticle(args.newsArticle)
            context?.apply {
                showToast(getString(R.string.saved))
            }
        })
        return binding.root
    }
}