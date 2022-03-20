package com.example.noodoe.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.noodoe.db.dao.UserInfoDao
import com.example.noodoe.db.entity.UserInfo
import com.example.noodoe.network.api.login.data.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

const val NAME_LOGIN = "login"
const val KEY_TOKEN = "token"
const val KEY_OBJECT_ID = "objectId"

class LoginRepository(private val androidContext: Context, private val userInfoDao: UserInfoDao) {
    private val sharedPref: SharedPreferences by lazy {
        androidContext.getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE)
    }

    val userInfo: Flow<UserInfo?> = userInfoDao.getUserInfo()

    var isLogin: Boolean = false

    var token
        get() = sharedPref.getString(KEY_TOKEN, "")
        set(value) {
            with(sharedPref.edit()) {
                putString(KEY_TOKEN, value)
                apply()
            }
        }


    suspend fun login(loginResult: LoginResult) {
        token?.let { this.token = loginResult.sessionToken }
        isLogin = true

        withContext(Dispatchers.IO) {
            userInfoDao.update(transform(loginResult))
        }
    }

    suspend fun logout() {
        isLogin = false
        clear()
    }


    private suspend fun clear() {
        with(sharedPref.edit()) {
            remove(KEY_TOKEN)
            apply()
        }
        clearUserInfo()
    }

    private fun transform(loginData: LoginResult): UserInfo =
        UserInfo(
            0,
            loginData.objectId,
            username = loginData.username,
            reportEmail = loginData.reportEmail,
            sessionToken = loginData.sessionToken
        )

    private suspend fun clearUserInfo() {
        withContext(Dispatchers.IO) {
            userInfoDao.deleteAll()
        }
    }

}