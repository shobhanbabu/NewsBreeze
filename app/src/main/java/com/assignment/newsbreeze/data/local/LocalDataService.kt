package com.assignment.newsbreeze.data.local

import android.content.SharedPreferences
import com.assignment.newsbreeze.contract.ILocalService
import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.utils.IOTaskResult
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [ILocalService] impl class which uses shared preferences to provide the app with the functionality to
 * store recently fetched headlines
 */
@Singleton
class LocalDataService @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ILocalService {

    private val headlinesKey = "Headlines_key"

    @ExperimentalCoroutinesApi
    override suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>> =
        flow {
            val cachedData = sharedPreferences.getString(headlinesKey, null)
            if (!cachedData.isNullOrEmpty()) {
                val fromJson = Gson().fromJson(cachedData, NewsHeadlinesResponse::class.java)
                emit(
                    IOTaskResult.OnSuccess<NewsHeadlinesResponse>(fromJson)
                )
            } else {
                emit(
                    IOTaskResult.OnFailed(RuntimeException("Headlines not yet cached."))
                )
            }
        }

    override suspend fun cacheHeadlines(headlinesResponse: NewsHeadlinesResponse) {
        sharedPreferences.edit().putString(headlinesKey, Gson().toJson(headlinesResponse)).commit()
    }
}