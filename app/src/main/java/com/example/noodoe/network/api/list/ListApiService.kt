package com.example.noodoe.network.api.list

import com.example.noodoe.network.api.list.data.ListInfoResult
import com.example.noodoe.network.api.user.UserTimeRequest
import com.example.noodoe.network.api.user.UserTimeResult
import com.example.noodoe.network.consts.Constants
import com.example.noodoe.network.consts.Constants.NEWS_URL
import com.example.noodoe.network.consts.Constants.USERS_URL
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ListApiService {
    @GET(NEWS_URL)
    suspend fun getList(): Response<ListInfoResult>

    @POST(USERS_URL)
    suspend fun setUserTimeZone(
        @Path("objectId") objectId: String,
        @Body userTimeRequest: UserTimeRequest
    ): Response<UserTimeResult>
}
