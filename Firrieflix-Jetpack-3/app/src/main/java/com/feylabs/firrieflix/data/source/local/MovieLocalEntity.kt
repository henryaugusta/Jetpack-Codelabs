package com.feylabs.firrieflix.data.source.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tb_movies")
@Parcelize
data class MovieLocalEntity(
    val overview: String?,
    val release_date: String?,
    val adult: Boolean?,
    val backdrop_path: String?,
    @PrimaryKey
    val id: Int?,
    val media_type: String?,
    val original_language: String?,
    val original_title: String?,
    val popularity: Double?,
    val poster_path: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,

    val vote_count: Int?
) : Parcelable