package com.assignment.newsbreeze.data.models

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table", primaryKeys = ["title", "description"])
data class DbArticle(
        @Embedded val source: DbSource?,
        val author: String?,
        val title: String,
        val description: String,
        val url: String?,
        val urlToImage: String?,
        val publishedAt: String?,
        val content: String?
)

fun DbArticle.transform(): Article =
    Article(source?.transform(), author, title, description, url, urlToImage, publishedAt, content)