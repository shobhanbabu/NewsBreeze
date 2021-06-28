package com.assignment.newsbreeze.data.network

import com.assignment.newsbreeze.BuildConfig
import com.assignment.newsbreeze.data.models.NewsHeadlinesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NewsApi {
    @GET("/v2/everything")
    suspend fun getHeadlines(
        @Query("q") query: String = "breaking news",
        @Query("from") fromDate: String = "2021-06-01",
        @Query("to") toDate: String = "2021-06-01",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<NewsHeadlinesResponse>
}