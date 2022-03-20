package com.example.noodoe.network.api.login.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AclData(
    @Json(name = "read")
    val read: Boolean,
    @Json(name = "write")
    val write: Boolean
)