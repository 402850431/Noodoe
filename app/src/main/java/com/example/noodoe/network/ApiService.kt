package com.example.noodoe.network

import com.example.noodoe.network.login.LoginApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://watch-master-staging.herokuapp.com/api/"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

object LoginApi {
    val loginApiService : LoginApiService by lazy { retrofit.create(LoginApiService::class.java) }
}
