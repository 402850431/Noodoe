package com.example.noodoe.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "user_info_table")
data class UserInfo(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "objectId")
    var objectId: String,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "reportEmail")
    var reportEmail: String? = null,

    @Json(name = "sessionToken")
    val sessionToken: String
)
