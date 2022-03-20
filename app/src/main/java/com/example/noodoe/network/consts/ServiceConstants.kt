package com.example.noodoe.network.consts

import com.example.noodoe.network.api.list.ListApiService
import com.example.noodoe.network.api.login.LoginApiService
import com.example.noodoe.network.manager.ApiRequestManager
import com.example.noodoe.network.manager.ApiRequestManager.newBaseUrl

object ServiceConstants {

    val loginApiService : LoginApiService by lazy { ApiRequestManager.retrofit.create(LoginApiService::class.java) }
    val listApiService : ListApiService by lazy { ApiRequestManager.retrofit.newBaseUrl().create(ListApiService::class.java) }

}
