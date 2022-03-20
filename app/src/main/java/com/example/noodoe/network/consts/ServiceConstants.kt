package com.example.noodoe.network.consts

import com.example.noodoe.network.api.list.ListApiService
import com.example.noodoe.network.api.login.LoginApiService
import com.example.noodoe.network.manager.ApiRequestManager

object ServiceConstants {

    val loginApiService : LoginApiService by lazy { ApiRequestManager.instance.retrofit.create(LoginApiService::class.java) }
    val listApiService : ListApiService by lazy { ApiRequestManager.instance.newBaseUrlRetrofit().create(ListApiService::class.java) }
    val userApiService : ListApiService by lazy { ApiRequestManager.instance.withTokenRetrofit().create(ListApiService::class.java) }

}
