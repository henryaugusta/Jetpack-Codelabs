package com.feylabs.firrieflix.data.source.local

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "tb_favorite")
@Parcelize
data class FavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @NonNull
    @ColumnInfo(name = "favId")
    var movId: Int = 0,

    // for identification is this a show or movie
    // 1 for movie, 2 for show.
    @NonNull
    @ColumnInfo(name = "type")
    var favType: Int,

    @ColumnInfo(name = "fav_Name")
    var name: String? = null,

    @ColumnInfo(name = "fav_desc")
    var desc: String? = null,

    @ColumnInfo(name = "fav_img_prev")
    var imgPreview: String? = null,

    @ColumnInfo(name = "fav_poster")
    var poster: String? = null,

): Parcelable