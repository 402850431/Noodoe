package com.example.noodoe.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noodoe.MyApplication
import com.example.noodoe.R
import com.example.noodoe.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<LoginViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOnClick()
        initObserver()
    }

    private fun initOnClick() {
        btn_login.setOnClickListener {
            sharedViewModel.login(et_account.text.toString(), et_password.text.toString())
        }
    }

    private fun initObserver() {
        sharedViewModel.loginResult.observe(viewLifecycleOwner) {
            Log.e(">>>", "it = ${it.toString()}")
        }
    }

    override val sharedViewModel: LoginViewModel
        get() = LoginViewModel(activity!!.application)
}