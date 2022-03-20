package com.example.noodoe.network.api.user


import com.example.noodoe.network.api.BaseResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserTimeResult(
    @Json(name = "results")
    val results: List<Result>
): BaseResult()