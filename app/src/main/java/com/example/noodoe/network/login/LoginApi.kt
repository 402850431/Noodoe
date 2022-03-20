package com.example.noodoe.network.login

import com.example.noodoe.network.manager.RequestManager

object LoginApi {

    val loginApiService : LoginApiService by lazy { RequestManager.retrofit.create(LoginApiService::class.java) }

}
