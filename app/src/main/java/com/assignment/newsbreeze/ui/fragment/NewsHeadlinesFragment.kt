package com.assignment.newsbreeze.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.assignment.newsbreeze.R
import com.assignment.newsbreeze.adapter.NewsAdapter
import com.assignment.newsbreeze.data.models.Article
import com.assignment.newsbreeze.databinding.NewsHeadlinesFragmentBinding
import com.assignment.newsbreeze.utils.ListItemClickListener
import com.assignment.newsbreeze.utils.ViewState
import com.assignment.newsbreeze.utils.showToast
import com.assignment.newsbreeze.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsHeadlinesFragment : Fragment() {
    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: NewsHeadlinesFragmentBinding

    private val headlineItemClickListener: ListItemClickListener<Article> = {
        val direction = NewsHeadlinesFragmentDirections.actionHeadlinesToArticle(it)
        findNavController().navigate(direction)
    }

    private val headlineItemSaveClickListener: ListItemClickListener<Article> = {
        viewModel.saveArticle(it)
        context?.apply {
            showToast(getString(R.string.saved))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, cMainViewModelontainer: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsHeadlinesFragmentBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val divider = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
        requireContext().getDrawable(R.drawable.divider)?.let { divider.setDrawable(it) }

        binding.savedNews.setOnClickListener {
            val direction = NewsHeadlinesFragmentDirections.actionHeadlineToSavedArticles()
            findNavController().navigate(direction)
        }

        binding.headlinesList.addItemDecoration(divider)
        viewModel.newsHeadlinesState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading ->
                    binding.loading.visibility = if (it.isLoading) View.VISIBLE else View.GONE

                is ViewState.RenderFailure ->
                    it.throwable.message?.let { toastMessage ->
                        context?.showToast(toastMessage)
                    }

                is ViewState.RenderSuccess -> {
                    if (binding.headlinesList.adapter == null) {
                        binding.headlinesList.adapter = NewsAdapter(
                            headlineItemClickListener,
                            headlineItemSaveClickListener
                        )
                    }

                    (binding.headlinesList.adapter as NewsAdapter).apply {
                        itemList = it.output
                    }
                }
            }
        })
    }
}