package com.assignment.newsbreeze.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assignment.newsbreeze.data.models.DbArticle

@Database(entities = [DbArticle::class], version = 1, exportSchema = false)
abstract class HeadlinesDB : RoomDatabase() {
    abstract fun headlinesDao(): HeadlinesDBDao
}