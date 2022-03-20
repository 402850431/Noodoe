package com.example.noodoe.network.api.login.data


data class LoginRequest(
    val username: String,
    val password: String //NOTE:需加密?
)
