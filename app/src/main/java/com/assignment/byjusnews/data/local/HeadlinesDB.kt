package com.assignment.byjusnews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assignment.newsbreeze.data.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class HeadlinesDB : RoomDatabase() {
    abstract fun headlinesDao(): HeadlinesDBDao
}