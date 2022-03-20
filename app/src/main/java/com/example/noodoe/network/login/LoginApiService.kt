package com.example.noodoe.network.login

import com.example.noodoe.network.consts.Constants.LOGIN_URL
import com.example.noodoe.network.login.data.LoginRequest
import com.example.noodoe.network.login.data.LoginResult
import retrofit2.Response
import retrofit2.http.*


interface LoginApiService {
    @POST(LOGIN_URL)
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResult>

}
