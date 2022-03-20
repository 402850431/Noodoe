package com.example.noodoe.ui.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noodoe.db.entity.UserInfo
import com.example.noodoe.network.api.list.data.NewsResult
import com.example.noodoe.network.api.user.UserTimeRequest
import com.example.noodoe.network.api.user.UserTimeResult
import com.example.noodoe.network.consts.ServiceConstants
import com.example.noodoe.repository.LoginRepository
import com.example.noodoe.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ListViewModel(
    androidContext: Application,
    private val loginRepository: LoginRepository
) : BaseViewModel(androidContext) {

    var selectDate = ""
    var selectTime = ""

    private val userInfo: LiveData<UserInfo?> = loginRepository.userInfo.asLiveData() //TODO: get room data
    val reportEmail = loginRepository.reportEmail
    val updateTime = loginRepository.updatedAt
    private val objectId = loginRepository.objectId

    val listResultList: LiveData<List<NewsResult>?>
        get() = _listResultList
    private val _listResultList = MutableLiveData<List<NewsResult>?>()

    val userTimeResult: LiveData<UserTimeResult?>
        get() = _userTimeResult
    private val _userTimeResult = MutableLiveData<UserTimeResult?>()

    fun getListInfo() {
        viewModelScope.launch {
            doNetwork {
                ServiceConstants.listApiService.getList()
            }.let {
                it?.let {
                    _listResultList.value = it.news
                }
            }
        }
    }

    fun updateTime() {
        objectId?.let { objectId ->
            val dateTime = "${selectDate}T${selectTime}:00.000Z"
                viewModelScope.launch {
                    doNetwork {
                        ServiceConstants.userApiService.setUserTimeZone(objectId, UserTimeRequest(dateTime))
                    }.let {
                        it?.let {
                            _userTimeResult.value = it
                        }
                    }
                }
        }
    }
}
