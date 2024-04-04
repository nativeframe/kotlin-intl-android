package com.nativeframe.kotlin.intl.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import com.nativeframe.kotlin.intl.android.R

/**
 * View for phone number. Encapsulates an [EditText]
 */
class PhoneNumberEditText(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : PhoneNumberField(context, attrs, defStyleAttr) {
    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : this(context, attrs, 0)

    override fun findSpinner(): Spinner {
        return findViewById(R.id.phoneFieldSpinner)
    }

    override fun findEditText(): EditText {
        return findViewById(R.id.phoneFieldEditText)
    }

    override fun setUpLayout() {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        gravity = Gravity.CENTER_VERTICAL
        orientation = HORIZONTAL
        setPadding(0, context.resources.getDimensionPixelSize(R.dimen.padding_large), 0, 0)
    }

    override fun getLayoutResId(): Int = R.layout.kia_view_phone_edit
}