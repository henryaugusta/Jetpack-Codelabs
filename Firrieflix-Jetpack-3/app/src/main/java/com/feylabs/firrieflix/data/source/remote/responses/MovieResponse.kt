package com.feylabs.firrieflix.data.source.remote.responses

import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

data class MovieResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
){
    data class Result(
        val overview: String,
        val release_date: String,
        val adult: Boolean,
        val backdrop_path: String,
        val genre_ids: List<Int>,
        val id: Int,
        val media_type: String,
        val original_language: String,
        val original_title: String,
        val popularity: Double,
        val poster_path: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
    )
}