package com.assignment.newsbreeze.contract

import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * The repository class represents the data store of the application. This class is primarily utilised
 * when building online-first applications where it will make the determination to load the data from
 * a local Room DB vs calling the retrofit function in order to obtain the data
 */
interface IRepository {
    val remoteDataSource: IRemoteDataSource
    val localDataSource: ILocalDataSource

    /**
     * Makes the News API call via data source. In an online-first architecture, it is at this function
     * call that the Repository class would perform a retrofit call if success caches returns the data,
     * else obtain cached data from the db
     */
    @ExperimentalCoroutinesApi
    suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>>
}