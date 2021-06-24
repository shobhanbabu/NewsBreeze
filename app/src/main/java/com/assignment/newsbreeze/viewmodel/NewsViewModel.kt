package com.assignment.newsbreeze.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.assignment.newsbreeze.models.Article

/**
 * In an MVVM architecture, the [ViewModel] acts as the point at which the view and the data layers
 * of the applicable interface in order to implement the business logic.
 */
class NewsViewModel : ViewModel() {

    val selectedArticle = MutableLiveData<Article>()

    fun openNewsArticle(info : String) {
        selectedArticle.postValue(Article(info))
    }
}