package com.example.noodoe.network.manager

import android.app.Application
import android.content.Context
import com.example.noodoe.network.consts.Constants
import com.example.noodoe.network.consts.Constants.BASE_URL
import com.example.noodoe.network.consts.Constants.CONNECT_TIMEOUT
import com.example.noodoe.network.consts.Constants.READ_TIMEOUT
import com.example.noodoe.network.consts.Constants.WRITE_TIMEOUT
import com.example.noodoe.network.interceptor.LogInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object RequestManager {

    private val mOkHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
        .addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
                .addHeader("X-Parse-Application-Id", "vqYuKPOkLQLYHhk4QTGsGKFwATT4mBIGREI2m8eD")
            return@Interceptor chain.proceed(builder.build())
        })
        .addInterceptor(LogInterceptor())


    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(mOkHttpClientBuilder.build())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

}
