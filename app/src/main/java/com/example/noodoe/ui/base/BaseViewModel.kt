package com.example.noodoe.ui.base

import android.app.Application
import android.content.Context
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noodoe.R
import com.example.noodoe.network.api.BaseResult
import com.example.noodoe.network.api.list.data.ListInfoResult
import com.example.noodoe.network.consts.ErrorUtils
import com.example.noodoe.network.consts.NetworkUtil
import com.example.noodoe.network.exception.NoConnectionException
import com.example.noodoe.network.api.login.data.ErrorResult
import kotlinx.coroutines.async
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException

enum class ApiStatus { LOADING, ERROR, DONE }
abstract class BaseViewModel(private val androidContext: Application) : ViewModel() {

    val networkExceptionUnavailable: LiveData<String>
        get() = _networkExceptionUnavailable

    val networkExceptionTimeout: LiveData<String>
        get() = _networkExceptionTimeout

    val networkExceptionUnknown: LiveData<String>
        get() = _networkExceptionUnknown

    private val _networkExceptionUnavailable = MutableLiveData<String>()
    private val _networkExceptionTimeout = MutableLiveData<String>()
    private val _networkExceptionUnknown = MutableLiveData<String>()
    private val _errorResult = MutableLiveData<ErrorResult>()

    val errorResult: LiveData<ErrorResult>
        get() = _errorResult

    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus
    private val _apiStatus = MutableLiveData<ApiStatus>()

    @Nullable
    suspend fun <T: BaseResult> doNetwork(
        exceptionHandle: Boolean = true,
        apiFun: suspend () -> Response<T>
    ): T? {
        _apiStatus.value = ApiStatus.LOADING
        return try {
            if (!NetworkUtil.isAvailable(androidContext))
                throw NoConnectionException()
            doApiFun(apiFun).apply {
                _apiStatus.value = ApiStatus.DONE
            }
        } catch (e: Exception) {
            Timber.e("doNetwork exception: $e")
            _apiStatus.value = ApiStatus.ERROR
            e.printStackTrace()
            if (exceptionHandle)
                doOnException(androidContext, e)
            null
        }
    }

    private suspend fun <T: BaseResult> doApiFun(apiFun: suspend () -> Response<T>): T? {
        val apiResult = viewModelScope.async {
            val response = apiFun()
            Timber.e("response.isSuccessful = ${response.isSuccessful}, response.body() = ${response.body().toString()}")
            when (response.isSuccessful) {
                true -> return@async response.body()
                false -> {
                    doResponseError(response)
                    return@async null
                }
            }
        }
        return apiResult.await()
    }

    private fun <T: BaseResult> doResponseError(response: Response<T>) {
        Timber.e("responseError = ${response.errorBody()?.charStream()}")
        val errorResult = ErrorUtils.parseError(response)
        errorResult?.let {
            _errorResult.value = it
        }
    }

    private fun doOnException(context: Context, exception: Exception) {
        when (exception) {
            is NoConnectionException -> {
                _networkExceptionUnavailable.postValue(context.getString(R.string.message_network_not_connect))
            }
            is SocketTimeoutException -> {
                _networkExceptionTimeout.postValue(context.getString(R.string.message_network_timeout))
            }
            else -> {
                _networkExceptionUnknown.postValue(context.getString(R.string.message_network_not_connect))
            }
        }
    }

}
