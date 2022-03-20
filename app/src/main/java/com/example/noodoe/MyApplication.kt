package com.example.noodoe

import android.app.Application
import android.content.Context
import com.example.noodoe.ui.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

/**
 * App 內部切換語系
 */
class MyApplication : Application() {
    companion object {
        lateinit var appContext: Context
        private var instance: MyApplication? = null
    }

    fun getInstance(): MyApplication? {
        if (instance == null) throw IllegalStateException("Application not be created yet.")
        return instance
    }

    private val viewModelModule = module {
        viewModel { LoginViewModel(get()) }
    }

    private val repoModule = module {
    }

    private val serviceModule = module {
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        instance = this

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApplication)
            modules(viewModelModule)
        }
    }

}