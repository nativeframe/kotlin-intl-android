package com.nativeframe.kotlin.intl.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import com.google.android.material.textfield.TextInputLayout
import com.nativeframe.kotlin.intl.android.R

/**
 * View for phone number layout. Encapsulates an [EditText]
 */
open class PhoneNumberInputLayout(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : PhoneNumberField(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    private var textInputLayout: TextInputLayout? = null

    override fun setUpLayout() {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        gravity = Gravity.TOP
        orientation = HORIZONTAL
    }

    override fun findSpinner(): Spinner {
        return findViewById(R.id.phoneFieldLayoutSpinner)
    }

    override fun findEditText(): EditText {
        return findViewById(R.id.phoneFieldTextInputEditText)
    }

    override fun setUpView() {
        super.setUpView()
        textInputLayout = findViewById(R.id.phoneFieldTextInputLayout)
    }

    override fun getLayoutResId(): Int = R.layout.kia_view_phone_input_layout

    override fun setHint(resId: Int) {
        textInputLayout!!.hint = context.getString(resId)
    }

    override fun setError(error: String?) {
        textInputLayout!!.isErrorEnabled = !error.isNullOrEmpty()
        textInputLayout!!.error = error
    }
}

