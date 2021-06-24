package com.assignment.newsbreeze.data.models

data class NewsHeadlinesResponse (
	val status : String,
	val totalResults : Int,
	val articles : List<Article>
)