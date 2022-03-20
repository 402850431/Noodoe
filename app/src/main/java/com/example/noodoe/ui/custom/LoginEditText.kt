package com.example.noodoe.ui.custom

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import com.example.noodoe.R
import kotlinx.android.synthetic.main.custom_login_edit_text.view.*

class LoginEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attrs, defStyle) {

    private var mOnFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
        block_editText.isSelected = hasFocus
        setError(null)
    }

    var eyeVisibility
        get() = btn_eye.visibility
        set(value) {
            btn_eye.visibility = value
        }

    val text: Editable
        get() = et_input.text

    var isClearShow
        get() = btn_clear.visibility == View.VISIBLE
        set(value) {
            btn_clear.visibility = if (value) View.VISIBLE else View.GONE
        }

    private var clearListener: OnClickListener? = null
    private var inputType: Int = 0
    private val typedArray by lazy {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LoginEditTextStyle, 0, 0)
    }

    private val editable by lazy { typedArray.getBoolean(R.styleable.LoginEditTextStyle_mEditable, true) }


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_login_edit_text, this, false)
        addView(view)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.LoginEditTextStyle, 0, 0)
        try {
            view.tv_title.text = typedArray.getText(R.styleable.LoginEditTextStyle_mTitleText)
            view.et_input.setText(typedArray.getText(R.styleable.LoginEditTextStyle_mText))
            view.et_input.hint = typedArray.getText(R.styleable.LoginEditTextStyle_mHint)
            if (!editable) {
                view.et_input.isEnabled = false
                view.et_input.inputType = InputType.TYPE_NULL
                view.et_input.isFocusable = false
                view.btn_clear.visibility = View.GONE
            }
            typedArray.getInt(R.styleable.LoginEditTextStyle_mEms, -1).let {
                if (it > 0) {
                    view.et_input.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(it))
                }
            }

            inputType = typedArray.getInt(R.styleable.LoginEditTextStyle_mInputType, InputType.TYPE_CLASS_TEXT)
            when (inputType) {
                InputType.TYPE_TEXT_VARIATION_PASSWORD -> {
                    view.et_input.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                    view.et_input.transformationMethod = PasswordTransformationMethod()
                }
                else -> {
                    view.et_input.inputType = inputType
                }
            }


            view.et_input.isEnabled = typedArray.getBoolean(R.styleable.LoginEditTextStyle_mEnable, true).apply {
                if (this)
                    isClearShow = false
            }

            view.btn_clear.visibility = View.GONE
            view.btn_eye.visibility = if (inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) View.VISIBLE else View.GONE

            //控制物件與下方的間距, default = 10dp
            val itemMarginBottom: Int = typedArray.getDimensionPixelOffset(R.styleable.LoginEditTextStyle_mMarginBottom, 10)
            setMarginBottom(itemMarginBottom)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }
//        setupFocus()
//        setupEye()
//        setupEditTextClearListener()
//        setError(null)
//        setupKeyBoardPressDown()

    }

    private fun setupFocus() {
        et_input.onFocusChangeListener = mOnFocusChangeListener
    }

    private fun setupEye() {
        btn_eye.setOnClickListener {
            if (cb_eye.isChecked) {
                cb_eye.isChecked = false
                et_input.transformationMethod = PasswordTransformationMethod.getInstance() //不顯示
            } else {
                cb_eye.isChecked = true
                et_input.transformationMethod = HideReturnsTransformationMethod.getInstance() //顯示
            }
            et_input.setSelection(et_input.length())
        }
    }

    private fun setupEditTextClearListener(listener: (() -> Unit)? = null) {
        listener?.let {
            clearListener = OnClickListener {
                et_input.setText("")
                listener.invoke()
            }
        }
        clearListener?.let {
            btn_clear.setOnClickListener(it)
        } ?: run {
            btn_clear.setOnClickListener {
                et_input.setText("")
            }
        }
    }


    private fun setupKeyBoardPressDown() {
        et_input.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                clearFocus()
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setError(value: String?) {
        tv_error.text = value
        if (tv_error.text.isNullOrEmpty()) {
            tv_error.visibility = View.GONE
            block_editText.isActivated = false
        } else {
            tv_error.visibility = View.VISIBLE
            block_editText.isActivated = true
        }
    }

    private fun setMarginBottom(px: Int) {
        (layout.layoutParams as LayoutParams).setMargins(0, 0, 0, px)
    }

}
