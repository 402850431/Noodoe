package com.example.noodoe.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.text.Spanned
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.reflect.KClass

@SuppressLint("InflateParams")
abstract class BaseFragment<VM : ViewModel>() : Fragment() {

//    val viewModel: VM by sharedViewModel<VM>()
    protected abstract val sharedViewModel: VM

    open fun loading() {
        if (activity is BaseActivity<*>)
            (activity as BaseActivity<*>).loading()
    }

    open fun hideLoading() {
        if (activity is BaseActivity<*>)
            (activity as BaseActivity<*>).hideLoading()
    }

    open fun showDialog(
        title: String? = null,
        message: String? = null,
        buttonText: String? = null,
        positiveClickListener: () -> Unit? = { },
        negativeClickListener: () -> Unit? = { }
    ) {
        if (activity is BaseActivity<*>)
            (activity as BaseActivity<*>).showCustomDialog(
                title,
                message,
                buttonText,
                positiveClickListener,
                negativeClickListener
            )
    }


}
