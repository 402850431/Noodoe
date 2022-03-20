package com.example.noodoe.network.api.list

import com.example.noodoe.network.api.list.data.ListInfoResult
import com.example.noodoe.network.consts.Constants
import com.example.noodoe.network.consts.Constants.NEWS_URL
import retrofit2.Response
import retrofit2.http.GET


interface ListApiService {
    @GET(NEWS_URL)
    suspend fun getList(): Response<ListInfoResult>
}
