package com.assignment.newsbreeze.usecase

import com.assignment.newsbreeze.contract.IRepository
import com.assignment.newsbreeze.contract.IUseCase
import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [IUseCase] class implementation that retrieves news headlines and returns
 * the [IOTaskResult] [NewsHeadlinesResponse] instance in return
 */
@Singleton
class HeadlineUseCase @Inject constructor(override val repository: IRepository) :
    IUseCase<Unit, NewsHeadlinesResponse> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: Unit): Flow<IOTaskResult<NewsHeadlinesResponse>> =
        repository.getHeadlines()
}