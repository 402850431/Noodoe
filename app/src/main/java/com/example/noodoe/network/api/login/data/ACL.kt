package com.example.noodoe.network.api.login.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ACL(
    @Json(name = "WkuKfCAdGq")
    val wkuKfCAdGq: AclData
)