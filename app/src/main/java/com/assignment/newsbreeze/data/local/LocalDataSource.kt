package com.assignment.newsbreeze.data.local

import com.assignment.newsbreeze.contract.ILocalDataSource
import com.assignment.newsbreeze.contract.ILocalService
import com.assignment.newsbreeze.contract.IRemoteDataSource
import com.assignment.newsbreeze.contract.IWebService
import com.assignment.newsbreeze.data.models.Article
import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [IRemoteDataSource] impl class that provides access to network API calls
 * @see IRemoteDataSource
 * @see IWebService
 */
@Singleton
class LocalDataSource @Inject constructor(override val localService: ILocalService) :
    ILocalDataSource {

    @ExperimentalCoroutinesApi
    override suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>> =
        localService.getHeadlines()

    @ExperimentalCoroutinesApi
    override suspend fun cacheHeadlines(headlinesResponse: NewsHeadlinesResponse) {
        localService.cacheHeadlines(headlinesResponse)
    }

    @ExperimentalCoroutinesApi
    override suspend fun saveArticle(article: Article) {
        localService.saveArticle(article)
    }

    @ExperimentalCoroutinesApi
    override suspend fun deleteArticle(article: Article) {
        localService.deleteArticle(article)
    }
}