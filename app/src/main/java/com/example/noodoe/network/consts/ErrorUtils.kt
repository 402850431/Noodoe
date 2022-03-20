package com.example.noodoe.network.consts

import androidx.annotation.Nullable
import com.example.noodoe.network.api.login.data.ErrorResult
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import com.example.noodoe.network.manager.ApiRequestManager
import timber.log.Timber
import java.io.IOException

object ErrorUtils {
    @Nullable
    fun <T> parseError(response: Response<T>): ErrorResult? {

        val converter: Converter<ResponseBody, ErrorResult> = ApiRequestManager.instance.retrofit
            .responseBodyConverter(ErrorResult::class.java, arrayOfNulls<Annotation>(0))

        var error: ErrorResult? = null

        response.errorBody()?.let {
            try {
                error = converter.convert(it)
            } catch (e: IOException) {
                Timber.e("response.errorBody() parseError: $e")
                throw e
            }
        }
        error?.let {
            return ErrorResult(it.code, it.error)
        }

        return null
    }

}