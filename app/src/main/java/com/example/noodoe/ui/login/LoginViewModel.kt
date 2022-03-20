package com.example.noodoe.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noodoe.network.consts.ServiceConstants
import com.example.noodoe.network.api.login.data.LoginRequest
import com.example.noodoe.network.api.login.data.LoginResult
import com.example.noodoe.repository.LoginRepository
import com.example.noodoe.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(androidContext: Application, private val loginRepository: LoginRepository) : BaseViewModel(androidContext) {

    val loginResult: LiveData<LoginResult?>
        get() = _loginResult
    private val _loginResult = MutableLiveData<LoginResult?>()

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            doNetwork {
                ServiceConstants.loginApiService.login(LoginRequest(userName, password))
            }.let {
                _loginResult.value = it
                //save token to repository
                it?.apply {
                    loginRepository.login(it)
                }
            }
        }
    }

}
