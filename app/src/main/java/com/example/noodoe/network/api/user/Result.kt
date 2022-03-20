package com.example.noodoe.network.api.user


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "objectId")
    val objectId: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "username")
    val username: String
)