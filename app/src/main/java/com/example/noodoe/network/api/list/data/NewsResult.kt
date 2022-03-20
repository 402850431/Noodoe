package com.example.noodoe.network.api.list.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResult(
    @Json(name = "chtmessage")
    val chtMessage: String,
    @Json(name = "content")
    val content: String,
    @Json(name = "endtime")
    val endTime: String,
    @Json(name = "engmessage")
    val engMessage: String,
    @Json(name = "starttime")
    val startTime: String,
    @Json(name = "updatetime")
    val updateTime: String,
    @Json(name = "url")
    val url: String
)