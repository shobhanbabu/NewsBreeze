package com.assignment.newsbreeze.di

import android.content.Context
import android.content.SharedPreferences
import com.assignment.newsbreeze.BuildConfig
import com.assignment.newsbreeze.contract.*
import com.assignment.newsbreeze.data.local.LocalDataService
import com.assignment.newsbreeze.data.local.LocalDataSource
import com.assignment.newsbreeze.data.network.NetworkDataSource
import com.assignment.newsbreeze.data.repository.NewsRepository
import com.assignment.newsbreeze.data.network.NewsApi
import com.assignment.newsbreeze.data.network.RetrofitNewsWebService
import com.assignment.newsbreeze.usecase.HeadlineUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt Module class that builds our dependency graph
 */
@InstallIn(ActivityComponent::class)
@Module
object NewsHiltDIModule {

    /**
     * Returns the [HttpLoggingInterceptor] instance with logging based on build type
     */
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    /**
     * Provides an [OkHttpClient]
     * @param loggingInterceptor [HttpLoggingInterceptor] instance
     */
    @Provides
    fun provideOKHttpClient(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient().apply {
        OkHttpClient.Builder().run {
            addInterceptor(loggingInterceptor)
            build()
        }
    }

    /**
     * Returns a [GsonConverterFactory] instance
     */
    @Provides
    fun provideGSONConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    /**
     * Returns an instance of the [NewsApi] interface for the retrofit class
     * @return [NewsApi] impl
     */
    @Provides
    fun provideRetrofitInstance(
        client: OkHttpClient,
        gsonConverterFactory : GsonConverterFactory
    ): NewsApi =
        Retrofit.Builder().run {
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(gsonConverterFactory)
            client(client)
            build()
        }.run {
            create(NewsApi::class.java)
        }

    /**
     * Returns a [IWebService] impl
     * @param retrofitClient [NewsApi] retrofit interface
     */
    @Provides
    fun providesRetrofitService(retrofitClient: NewsApi): IWebService =
        RetrofitNewsWebService(retrofitClient)

    /**
     * Returns a [IRemoteDataSource] impl
     * @param webService [IWebService] instance
     */
    @Provides
    fun providesNetworkDataSource(webService: IWebService): IRemoteDataSource =
        NetworkDataSource(webService)

    /**
     * Returns a [ILocalService] impl
     * @param sharedPreferences [SharedPreferences] retrofit interface
     */
    @Provides
    fun providesLocalDataService(sharedPreferences: SharedPreferences): ILocalService =
        LocalDataService(sharedPreferences)

    /**
     * Returns a [ILocalDataSource] impl
     * @param localService [ILocalService] instance
     */
    @Singleton
    @Provides
    fun providesLocalDataSource(localService: ILocalService): ILocalDataSource =
        LocalDataSource(localService)

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("Constants.PREF_NAME", Context.MODE_PRIVATE)

    /**
     * Returns a singleton [IRepository] implementation
     * @param remoteDataSource [IRemoteDataSource] implementation
     */
    @Provides
    fun provideRepository(remoteDataSource: IRemoteDataSource
    ): IRepository =
        NewsRepository(remoteDataSource)


    /**
     * Returns a [HeadlineUseCase] instance
     * @param repository [IRepository] impl
     */
    @Provides
    fun provideHeadlineUseCase(repository: IRepository): HeadlineUseCase =
        HeadlineUseCase(repository)
}