package com.example.noodoe.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noodoe.network.login.LoginApi
import com.example.noodoe.network.login.data.LoginRequest
import com.example.noodoe.network.login.data.LoginResult
import com.example.noodoe.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(androidContext: Application) : BaseViewModel(androidContext) {

    val loginResult: LiveData<LoginResult>
        get() = _loginResult
    private val _loginResult = MutableLiveData<LoginResult>()

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            doNetwork {
                Log.e(">>>", "userName = $userName, password = $password")
                LoginApi.loginApiService.login(LoginRequest(userName, password))
            }.let {
                Log.e(">>>", "result = $it")
                _loginResult.value = it
            }
        }
    }

}
