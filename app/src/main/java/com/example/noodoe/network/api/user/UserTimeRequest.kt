package com.example.noodoe.network.api.user


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserTimeRequest(
    @Json(name = "updatedAt")
    val updatedAt: String,
)