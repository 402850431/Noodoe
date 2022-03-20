package com.example.noodoe.ui

import android.os.Bundle
import com.example.noodoe.R
import com.example.noodoe.ui.base.BaseActivity
import com.example.noodoe.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}