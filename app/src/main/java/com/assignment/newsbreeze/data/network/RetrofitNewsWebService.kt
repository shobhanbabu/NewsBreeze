package com.assignment.newsbreeze.data.network

import com.assignment.newsbreeze.contract.IWebService
import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import com.assignment.newsbreeze.utils.performSafeNetworkApiCall
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [IWebService] impl class which uses Retrofit to provide the app with the functionality to make
 * network requests
 */
@Singleton
class RetrofitNewsWebService @Inject constructor(private val retrofitClient: NewsApi) :
    IWebService {

    @ExperimentalCoroutinesApi
    override suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>> =
        performSafeNetworkApiCall("Error Obtaining headlines") {
            retrofitClient.getHeadlines()
        }
}