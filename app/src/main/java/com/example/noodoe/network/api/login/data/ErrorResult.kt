package com.example.noodoe.network.api.login.data


import com.example.noodoe.network.api.BaseResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResult(
    @Json(name = "code")
    val code: Int,
    @Json(name = "error")
    val error: String
): BaseResult()