package com.assignment.newsbreeze.contract

import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * The repository class represents the data store of the application. This class is primarily utilised
 * when building offline-first applications where it will make the determination to load the data from
 * a local Room DB vs calling the retrofit function in order to obtain the data
 */
interface IRepository {

    val remoteDataSource: IRemoteDataSource
//    val headlinesDBDao: HeadlinesDBDao
    /**
     * Makes the popular photos API call via data source. In an offline-first architecture, it is at this function
     * call that the Repository class would check if the data exists in a Room
     * table, if so return the data from the db, else perform a retrofit call to obtain and store the data
     * into the db before returning the same
     */
    @ExperimentalCoroutinesApi
    suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>>
}