package com.example.noodoe.network.consts

import androidx.annotation.Nullable
import retrofit2.Response


object ErrorUtils {
    @Nullable
    fun <T> parseError(response: Response<T>): T? {
        return response.body()
//        return response.errorBody() as T //error handle實作區
    }

}