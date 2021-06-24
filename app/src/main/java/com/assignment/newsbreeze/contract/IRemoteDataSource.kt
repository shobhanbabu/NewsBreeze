package com.assignment.newsbreeze.contract

import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * Contract for the remote data source that will provide the plug to access network data by obtaining
 * an instance of [IWebService] interface in the implementing class
 */
interface IRemoteDataSource {

    // Webservice Interface that a remote data source impl class needs to provide
    val webService: IWebService

    /**
     * Requests the webservice class to obtain a list of photos by page number
     * @return [Flow] of [IOTaskResult] of [NewsHeadlinesResponse] type
     */
    @ExperimentalCoroutinesApi
    suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>>
}