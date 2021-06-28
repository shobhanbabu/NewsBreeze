package com.assignment.newsbreeze.data.local

import androidx.room.*
import com.assignment.newsbreeze.data.models.DbArticle

@Dao
interface HeadlinesDBDao {
    @Query("SELECT * FROM news_table")
    fun getAllHeadlines(): List<DbArticle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<DbArticle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: DbArticle)

    @Query("DELETE FROM news_table")
    fun deleteHeadlines()

    @Delete
    fun delete(article: DbArticle)
}