package com.example.noodoe.network.consts

object Constants {

    var BASE_URL = "https://watch-master-staging.herokuapp.com"
    const val HOME_BASE_URL = "https://tcgbusfs.blob.core.windows.net"
    const val NEWS_URL = "/dotapp/news.json"
    const val LOGIN_URL = "/api/login"

    //timeout
    const val CONNECT_TIMEOUT: Long = 15 * 1000
    const val WRITE_TIMEOUT: Long = 15 * 1000
    const val READ_TIMEOUT: Long = 15 * 1000

}