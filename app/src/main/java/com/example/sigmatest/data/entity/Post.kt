package com.example.sigmatest.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tb_post")
@Parcelize
data class PostEntity(
    @PrimaryKey @ColumnInfo(name ="id") var id: Int?,
    @ColumnInfo(name="body")var body: String?,
    @ColumnInfo(name="title")var title: String?,
    @ColumnInfo(name="userid")var userid: Int?


): Parcelable {
//    @Ignore
//    var date: String?
}


data class PostResponse(
    val body: String?,
    val id: Int?,
    val title: String?,
    val userId: Int?
)