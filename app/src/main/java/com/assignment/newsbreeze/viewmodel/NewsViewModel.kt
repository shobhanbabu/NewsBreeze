package com.assignment.newsbreeze.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.newsbreeze.data.models.Article
import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.usecase.HeadlineUseCase
import com.assignment.newsbreeze.utils.ViewState
import com.assignment.newsbreeze.utils.getViewStateFlowForNetworkCall
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * In an MVVM architecture, the [ViewModel] acts as the point at which the view and the data layers
 * of the applicable interface in order to implement the business logic.
 */
class NewsViewModel @ViewModelInject constructor(
    private val headlineUseCase: HeadlineUseCase
) : ViewModel() {
    val newsHeadlinesState: MutableLiveData<ViewState<List<Article>>> by lazy {
        MutableLiveData<ViewState<List<Article>>>()
    }

    fun loadHeadlines() {
        viewModelScope.launch {
            getViewStateFlowForNetworkCall {
                //Need to pass appropriate request to get the required headlines/news
                headlineUseCase.execute(print("dummy"))
            }.collect {
                when (it) {
                    is ViewState.Loading -> newsHeadlinesState.value = it
                    is ViewState.RenderFailure -> {
                        newsHeadlinesState.value = it
//                        localDataService.getHeadlines().collect { result ->
//                            newsHeadlinesState.value = when (result) {
//                                is IOTaskResult.OnSuccess -> ViewState.RenderSuccess(result.data.articles)
//                                is IOTaskResult.OnFailed -> ViewState.RenderFailure(it.throwable)
//                            }
//                        }
                    }
                    is ViewState.RenderSuccess<NewsHeadlinesResponse> -> {
//                        localDataService.cacheHeadlines(it.output)
                        newsHeadlinesState.value = ViewState.RenderSuccess(it.output.articles)
                    }
                }
            }
        }
    }
}