package com.example.noodoe.network.login

import com.example.noodoe.network.login.data.LoginData
import retrofit2.http.*


interface LoginApiService {
    @POST("login")
    suspend fun userLogin(
        @Query("accept") contentType: String? = "application/vnd.github.v3+json",
        @Query("since") since: String? = null,
        @Query("per_page") itemsPerPage: Int? = null,
    ): List<LoginData>

}
