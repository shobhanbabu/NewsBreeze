package com.assignment.byjusnews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.newsbreeze.data.models.Article

@Dao
interface HeadlinesDBDao {
    @Query("SELECT * FROM article")
    fun getAllHeadlines(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>)

    @Query("DELETE FROM article")
    fun deleteHeadlines()
}