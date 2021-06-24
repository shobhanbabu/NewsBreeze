package com.assignment.newsbreeze.data.repository

import com.assignment.newsbreeze.contract.IRemoteDataSource
import com.assignment.newsbreeze.contract.IRepository
import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class impl from [IRepository]\
 * @see IRepository
 * @see IRemoteDataSource
 */
@Singleton
class NewsRepository @Inject constructor(
    override val remoteDataSource: IRemoteDataSource
    ) :
    IRepository {

    @ExperimentalCoroutinesApi
    override suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>> =
        remoteDataSource.getHeadlines()
}