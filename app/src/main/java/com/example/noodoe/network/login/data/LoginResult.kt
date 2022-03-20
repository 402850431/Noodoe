package com.example.noodoe.network.login.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResult(
    @Json(name = "ACL")
    val aCL: ACL,
    @Json(name = "code")
    val code: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "isVerifiedReportEmail")
    val isVerifiedReportEmail: Boolean,
    @Json(name = "number")
    val number: Int,
    @Json(name = "objectId")
    val objectId: String,
    @Json(name = "parameter")
    val parameter: Int,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "reportEmail")
    val reportEmail: String,
    @Json(name = "sessionToken")
    val sessionToken: String,
    @Json(name = "timeZone")
    val timeZone: String,
    @Json(name = "timezone")
    val timezone: Int,
    @Json(name = "timone")
    val timone: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "username")
    val username: String
)