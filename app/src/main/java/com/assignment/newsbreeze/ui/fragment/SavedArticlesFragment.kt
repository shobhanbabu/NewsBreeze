package com.assignment.newsbreeze.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.assignment.newsbreeze.adapter.SavedNewsAdapter
import com.assignment.newsbreeze.data.models.Article
import com.assignment.newsbreeze.databinding.FragmentSavedArticlesBinding
import com.assignment.newsbreeze.utils.ListItemClickListener
import com.assignment.newsbreeze.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedArticlesFragment : Fragment() {
    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentSavedArticlesBinding

    private val headlineItemClickListener: ListItemClickListener<Article> = {
        val direction = SavedArticlesFragmentDirections.actionSavedArticlesToArticle(it)
        findNavController().navigate(direction)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedArticlesBinding.inflate(layoutInflater)
        binding.activity = activity
        viewModel.localHeadlines.observe(viewLifecycleOwner, Observer {
            if (binding.headlinesList.adapter == null) {
                binding.headlinesList.adapter = SavedNewsAdapter(
                    headlineItemClickListener
                )
            }

            (binding.headlinesList.adapter as SavedNewsAdapter).apply {
                itemList = it
            }
        })
        viewModel.fetchLocalHeadlines()
        return binding.root
    }
}