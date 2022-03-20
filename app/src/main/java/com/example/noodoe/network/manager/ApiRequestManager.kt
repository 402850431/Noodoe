package com.example.noodoe.network.manager

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.noodoe.network.consts.Constants.BASE_URL
import com.example.noodoe.network.consts.Constants.CONNECT_TIMEOUT
import com.example.noodoe.network.consts.Constants.HOME_BASE_URL
import com.example.noodoe.network.consts.Constants.READ_TIMEOUT
import com.example.noodoe.network.consts.Constants.WRITE_TIMEOUT
import com.example.noodoe.network.interceptor.LogInterceptor
import com.example.noodoe.repository.KEY_TOKEN
import com.example.noodoe.repository.NAME_LOGIN
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class ApiRequestManager private constructor(context: Context) {

    companion object {
        private lateinit var staticContext: Application
        val instance: ApiRequestManager by lazy {
            ApiRequestManager(staticContext)
        }

        fun init(context: Application) {
            staticContext = context
        }
    }

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

    private val sharedPref: SharedPreferences by lazy {
        staticContext.getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE)
    }

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(mOkHttpClientBuilder.build())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun newBaseUrlRetrofit(url: String? = HOME_BASE_URL): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url ?: HOME_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun withTokenRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClientBuilder
                .addInterceptor(Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    sharedPref.getString(KEY_TOKEN, "")?.let {
                        builder.addHeader("X-Parse-Session-Token", it)
                    }
                    return@Interceptor chain.proceed(builder.build())
                }).build()
            )
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

}
