package com.assignment.newsbreeze.contract

import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * A UseCase defines a specific task performed in the app. For this project there would be two:
 * 1. Get news headlines
 */
interface IUseCase<in I : Any, out O : Any> {

    val repository: IRepository

    /**
     * Execution contract which will run the business logic associated with completing a
     * particular use case
     * @param input [I] type input parameter
     * @since 1.0
     * @return [O] model type used to define the UseCase class
     */
    @ExperimentalCoroutinesApi
    suspend fun execute(input: I): Flow<IOTaskResult<O>>
}