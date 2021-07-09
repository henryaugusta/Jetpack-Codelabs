package com.feylabs.firrieflix.data.source.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "tb_shows")
@Parcelize
data class ShowLocalEntity(
    val backdrop_path: String?,
    val first_air_date: String?,
    @PrimaryKey
    val id: Int?,
    val media_type: String?,
    val name: String?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val vote_average: Double?,
    val vote_count: Int?
):Parcelable