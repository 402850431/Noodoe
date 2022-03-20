package com.example.noodoe.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noodoe.R
import com.example.noodoe.ui.base.BaseFragment
import com.example.noodoe.ui.list.ListActivity
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : BaseFragment<LoginViewModel>() {
    override val viewModel: LoginViewModel by sharedViewModel()

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
            viewModel.login(et_account.text.trim().toString(), et_password.text.trim().toString())
        }
    }

    private fun initObserver() {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            if (it?.sessionToken != null) {
                startActivity(Intent(requireContext(), ListActivity::class.java))
            }
        }

        viewModel.errorResult.observe(viewLifecycleOwner) {
            showDialog(it.error)
        }
    }

}