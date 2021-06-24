package com.assignment.newsbreeze.contract

import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * This interface provides contracts a web-service class needs to abide by to provide the app
 * with network data as required
 */
interface IWebService {

    /**
     * Performs the news API call. In an offline-first architecture, it is at this function
     * call that the Repository class would check if the data exists in a Room
     * table, if so return the data from the db, else perform a retrofit call to obtain and store the data
     * into the db before returning the same
     * @return [IOTaskResult] of [NewsHeadlinesResponse] type
     */
    @ExperimentalCoroutinesApi
    suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>>
}