package com.assignment.newsbreeze.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.newsbreeze.data.models.Article
import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.usecase.HeadlineUseCase
import com.assignment.newsbreeze.utils.IOTaskResult
import com.assignment.newsbreeze.utils.ViewState
import com.assignment.newsbreeze.utils.getViewStateFlowForNetworkCall
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    val localHeadlines: MutableLiveData<List<Article>> by lazy {
        MutableLiveData<List<Article>>()
    }

    fun loadHeadlines() {
        viewModelScope.launch {
            getViewStateFlowForNetworkCall {
                //Need to pass appropriate request to get the required headlines/news
                headlineUseCase.execute(empty())
            }.collect {
                when (it) {
                    is ViewState.Loading -> newsHeadlinesState.value = it
                    is ViewState.RenderFailure -> newsHeadlinesState.value = it
                    is ViewState.RenderSuccess<NewsHeadlinesResponse> ->
                        newsHeadlinesState.value = ViewState.RenderSuccess(it.output.articles)
                }
            }
        }
    }


    fun fetchLocalHeadlines() {
        viewModelScope.launch {
            //Need to pass appropriate request to get the required headlines/news
            withContext(IO) {
                headlineUseCase.repository.localDataSource.getHeadlines().collect {
                    when (it) {
                        is IOTaskResult.OnSuccess -> localHeadlines.postValue(it.data.articles)
                        is IOTaskResult.OnFailed -> localHeadlines.postValue(emptyList())
                    }
                }
            }
        }
    }

    private fun empty() = Unit

    fun saveArticle(it: Article) {
        viewModelScope.launch {
            withContext(IO) {
                headlineUseCase.repository.localDataSource.saveArticle(it)
            }
        }
    }

    fun deleteArticle(it: Article) {
        viewModelScope.launch {
            withContext(IO) {
                headlineUseCase.repository.localDataSource.saveArticle(it)
            }
        }
    }
}