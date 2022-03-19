package com.example.noodoe.network.login.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ACL(
    @Json(name = "WkuKfCAdGq")
    val wkuKfCAdGq: AclData
)