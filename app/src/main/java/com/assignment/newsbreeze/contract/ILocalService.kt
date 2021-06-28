package com.assignment.newsbreeze.contract

import com.assignment.newsbreeze.data.models.Article
import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * This interface provides contracts a local-service class needs to provide the app
 * with local data as required
 */
interface ILocalService {

    /**
     * Get the data from local which is last stored or cached data
     * @return [IOTaskResult] of [NewsHeadlinesResponse] type
     */
    @ExperimentalCoroutinesApi
    suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>>

    /**
     * caches the [NewsHeadlinesResponse]
     */
    @ExperimentalCoroutinesApi
    suspend fun cacheHeadlines(headlinesResponse: NewsHeadlinesResponse)

    /**
     * caches the [Article]
     */
    @ExperimentalCoroutinesApi
    suspend fun saveArticle(article: Article)

    /**
     * caches the [Article]
     */
    @ExperimentalCoroutinesApi
    suspend fun deleteArticle(article: Article)
}