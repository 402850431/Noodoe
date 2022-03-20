package com.example.noodoe.network.api.list.data


import com.example.noodoe.network.api.BaseResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListInfoResult(
    @Json(name = "News")
    val news: List<NewsResult>,
    @Json(name = "updateTime")
    val updateTime: String
): BaseResult()