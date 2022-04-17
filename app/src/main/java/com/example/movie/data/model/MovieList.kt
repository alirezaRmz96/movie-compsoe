package com.example.moviecleenapp.data.model

data class MovieList(
    val page: Int,
    val results: ArrayList<Result>,
    val total_pages: Int,
    val total_results: Int
)