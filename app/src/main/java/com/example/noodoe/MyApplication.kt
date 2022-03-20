package com.example.noodoe

import android.app.Application
import android.content.Context
import com.example.noodoe.db.MyRoomDatabase
import com.example.noodoe.network.manager.ApiRequestManager
import com.example.noodoe.repository.LoginRepository
import com.example.noodoe.ui.list.ListViewModel
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
        viewModel { LoginViewModel(get(), get()) }
        viewModel { ListViewModel(get(), get()) }
    }

    private val repoModule = module {
        single { LoginRepository(get(), get()) }
    }

    private val serviceModule = module {
    }

    private val dbModule = module {
        single { MyRoomDatabase.getDatabase(get()) }
        single { get<MyRoomDatabase>().userInfoDao() }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        instance = this

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApplication)
            modules(listOf(viewModelModule, repoModule, dbModule))
        }
        ApiRequestManager.init(this)
    }

}