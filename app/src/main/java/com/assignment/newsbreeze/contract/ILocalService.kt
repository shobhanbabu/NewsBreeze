package com.assignment.newsbreeze.contract

import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * This interface provides contracts a local-service class needs to abide by to provide the app
 * with network data as required
 */
interface ILocalService {

    /**
     * Performs the news API call to shared prefs. In an online-first architecture, it is at this function
     * call that the Repository class would check if the data fetched from retrofit call else get the data
     * from local which is last stored or cached data
     * @return [IOTaskResult] of [NewsHeadlinesResponse] type
     */
    @ExperimentalCoroutinesApi
    suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>>


    /**
     * caches the [NewsHeadlinesResponse]
     */
    @ExperimentalCoroutinesApi
    suspend fun cacheHeadlines(headlinesResponse: NewsHeadlinesResponse)
}