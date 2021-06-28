package com.assignment.newsbreeze.data.models

data class DbSource(
	val id: String?,
	val name: String?
)

fun DbSource.transform(): Source = Source(id, name)