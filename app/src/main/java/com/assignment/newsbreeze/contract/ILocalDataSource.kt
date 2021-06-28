package com.assignment.newsbreeze.contract

import com.assignment.newsbreeze.data.models.Article
import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * Contract for the local data source that will provide the plug to access local stored data by obtaining
 * an instance of [ILocalDataSource] interface in the implementing class
 */
interface ILocalDataSource {

    // Local service Interface that a local data source impl class needs to provide
    val localService: ILocalService

    /**
     * Requests the local service class to obtain the headlines stored in local db
     * @return [Flow] of [IOTaskResult] of [NewsHeadlinesResponse] type
     */
    @ExperimentalCoroutinesApi
    suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>>

    /**
     * Caches the response
     * [NewsHeadlinesResponse] type
     */
    @ExperimentalCoroutinesApi
    suspend fun cacheHeadlines(headlinesResponse: NewsHeadlinesResponse)

    /**
     * Caches the Article
     * [Article] type
     */
    @ExperimentalCoroutinesApi
    suspend fun saveArticle(article: Article)

    /**
     * Caches the Article
     * [Article] type
     */
    @ExperimentalCoroutinesApi
    suspend fun deleteArticle(article: Article)
}