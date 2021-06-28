package com.assignment.newsbreeze.data.local

import com.assignment.newsbreeze.contract.ILocalService
import com.assignment.newsbreeze.data.models.Article
import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import com.assignment.newsbreeze.data.models.transform
import com.assignment.newsbreeze.utils.IOTaskResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [ILocalService] impl class which uses Room DB to provide the app with the functionality to
 * store recently fetched headlines
 */
@Singleton
class LocalDataService @Inject constructor(private val headlinesDBDao: HeadlinesDBDao) :
    ILocalService {

    @ExperimentalCoroutinesApi
    override suspend fun getHeadlines(): Flow<IOTaskResult<NewsHeadlinesResponse>> =
        flow {
            val cachedData = headlinesDBDao.getAllHeadlines().map {
                it.transform()
            }
            if (!cachedData.isNullOrEmpty()) {
                val fromJson = NewsHeadlinesResponse("", cachedData.size, cachedData)
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
        headlinesDBDao.insertAll(headlinesResponse.articles.map { it.transform() })
    }

    override suspend fun saveArticle(article: Article) {
        headlinesDBDao.insert(article.transform())
    }

    override suspend fun deleteArticle(article: Article) {
        headlinesDBDao.delete(article.transform())
    }
}