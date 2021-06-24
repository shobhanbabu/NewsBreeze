package com.assignment.newsbreeze.data.network

import com.assignment.newsbreeze.contract.IRemoteDataSource
import com.assignment.newsbreeze.contract.IWebService
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
class NetworkDataSource @Inject constructor(override val webService: IWebService) :
    IRemoteDataSource {

    @ExperimentalCoroutinesApi
    override suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>> =
        webService.getHeadlines()
}