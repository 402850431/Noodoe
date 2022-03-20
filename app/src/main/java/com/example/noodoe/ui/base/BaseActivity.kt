package com.example.noodoe.ui.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.noodoe.R
import kotlinx.android.synthetic.main.layout_loading.view.*
import timber.log.Timber

abstract class BaseActivity <out VM : BaseViewModel> : AppCompatActivity() {

//    protected val viewModel: T by viewModel<T>()

    protected abstract val viewModel: VM
//    private val viewModel: BaseViewModel by viewModel()
//    private val viewModel: T by viewModel<BaseViewModel>()
//    protected abstract val viewModel: ViewModel by viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onNetworkException()
    }

    private fun onNetworkException() {
        viewModel.apiStatus.observe(this) { status ->
            when (status) {
                ApiStatus.LOADING -> loading()
                ApiStatus.DONE -> hideLoading()
                ApiStatus.ERROR -> {
                    hideLoading()
                    showCustomDialog("error")
                }
                else -> hideLoading()
            }
        }
        viewModel.networkExceptionUnavailable.observe(this) {
        }

        viewModel.networkExceptionTimeout.observe(this) {
        }

        viewModel.networkExceptionUnknown.observe(this) {
        }
    }

    private var loadingView: View? = null

    open fun loading() {
        if (loadingView == null) {
            loadingView = layoutInflater.inflate(R.layout.layout_loading, null)
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            addContentView(loadingView, params)
        } else {
            loadingView?.ll_loading?.visibility = View.VISIBLE
            loadingView?.ll_loading?.isClickable = true
        }
    }

    open fun hideLoading() {
        if (loadingView == null) {
            Timber.d("loadingView不存在")
        } else {
            loadingView?.ll_loading?.visibility = View.GONE
        }
    }

    private var alertDialogBuilder: AlertDialog.Builder? = null
    private var alertDialog: AlertDialog? = null

    fun showCustomDialog(
        title: String? = null,
        message: String? = null,
        buttonText: String? = null,
        positiveClickListener: () -> Unit? = { },
        negativeClickListener: () -> Unit? = { }
    ) {
        try {
            //防止跳出多個 error dialog
            if (alertDialog?.isShowing == true) alertDialog?.dismiss()

            alertDialogBuilder = AlertDialog.Builder(this@BaseActivity).apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton(buttonText ?: getString(R.string.ok)) { _, _ -> positiveClickListener }
                setNegativeButton(null) { _, _ -> negativeClickListener }
                setCancelable(false)
                setCancelable(false) //不能用系統 BACK 按鈕關閉 dialog
                show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
