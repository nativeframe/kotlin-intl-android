package com.nativeframe.kotlinintlandroid.view

import android.content.Context
import android.util.AttributeSet
import com.nativeframe.kotlin.intl.android.COUNTRIES
import com.nativeframe.kotlin.intl.android.view.PhoneNumberInputLayout
import com.nativeframe.kotlin.intl.android.Country
import com.nativeframe.kotlin.intl.android.view.PhoneNumberFieldOptions
import com.nativeframe.kotlinintlandroid.R

class CustomPhoneNumberInputLayout : PhoneNumberInputLayout {
    private var countryCode: String? = null

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setHint(R.string.phone_hint)
    }

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, countryCode: String?) : this(context, null, 0) {
        this.countryCode = countryCode
        setDefaultCountry(countryCode)
    }

    override fun getSupportedCountries(): Array<Country> {
        return COUNTRIES.filter { listOf("us", "mx", "ca").contains(it.code) }.toTypedArray()
    }

    override fun getPhoneFieldOptions(): PhoneNumberFieldOptions {
        return PhoneNumberFieldOptions(
            enableLog = true,
            enableAutoFormatting = true,
            autoFormattingAtLength = 9
        )
    }
}

