package com.example.noodoe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noodoe.R
import com.example.noodoe.ui.base.BaseActivity
import com.example.noodoe.ui.login.LoginViewModel

class MainActivity : BaseActivity<LoginViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override val viewModel: LoginViewModel
        get() = LoginViewModel(application)
}