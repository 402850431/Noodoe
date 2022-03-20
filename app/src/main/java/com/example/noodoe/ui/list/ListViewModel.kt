package com.example.noodoe.ui.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noodoe.network.api.list.data.NewsResult
import com.example.noodoe.network.consts.ServiceConstants
import com.example.noodoe.repository.LoginRepository
import com.example.noodoe.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ListViewModel(
    androidContext: Application,
    private val loginRepository: LoginRepository
) : BaseViewModel(androidContext) {

    val userInfo = loginRepository.userInfo

    val listResultList: LiveData<List<NewsResult>?>
        get() = _listResultList
    private val _listResultList = MutableLiveData<List<NewsResult>?>()

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
}
