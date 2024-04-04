package com.nativeframe.kotlin.intl.android.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.nativeframe.kotlin.intl.android.COUNTRIES
import com.nativeframe.kotlin.intl.android.Country
import com.nativeframe.kotlin.intl.android.ui.CountriesSpinnerAdapter
import java.util.Locale

/**
 * View for phone numbers with the corresponding country flag. Uses
 * libphonenumber (Google) to validate the phone number.
 *
 * @author Ismail created originally on 5/6/16 (java).
 * @author Antonio Johnson modified 03/26/24 (kotlin).
 */
abstract class PhoneNumberField(
    context: Context?, attrs: AttributeSet?, defStyleAttr: Int
) : LinearLayout(context, attrs, defStyleAttr) {
    lateinit var editText: EditText
    lateinit var spinner: Spinner
    lateinit var countries: Array<Country>

    private var country: Country? = null
    private var defaultCountryPosition = 0
    private var originalTextWatcher: TextWatcher? = null

    protected val phoneUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()

    init {
        //TODO do this outside of constructor/init
        inflate(getContext(), getLayoutResId(), this)
        setUpLayout()
        setUpView()
    }

    /**
     * Returns country spinner for phone number
     */
    protected abstract fun findSpinner(): Spinner

    /**
     * Returns edit text for phone number
     */
    protected abstract fun findEditText(): EditText

    /**
     * Returns phone number field options
     */
    protected open fun getPhoneFieldOptions(): PhoneNumberFieldOptions {
        return PhoneNumberFieldOptions(
            enableLog = false,
            enableAutoFormatting = true,
            autoFormattingAtLength = 9
        )
    }

    /**
     * List of countries that are shown
     */
    protected open fun getSupportedCountries(): Array<Country> = COUNTRIES

    /**
     * When a phone number field error is caught
     *
     * @param e Error
     */
    protected open fun onPhoneFieldError(e: Throwable) {
        if (getPhoneFieldOptions().enableLog) {
            Log.e("PhoneField", e.message, e)
        }
    }

    /**
     * Finds views and and loads countries into views
     */
    protected open fun setUpView() {
        spinner = findSpinner()
        editText = findEditText()
        countries = getSupportedCountries()

        val adapter = CountriesSpinnerAdapter(context, countries)
        spinner.setOnTouchListener { _, _ ->
            performClick()
            hideKeyboard()
            false
        }
        originalTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                var rawNumber = s.toString().trim()
                if (rawNumber.isEmpty()) {
                    spinner.setSelection(defaultCountryPosition)
                    return
                }

                val replace = "00"
                if (rawNumber.startsWith(replace)) {
                    rawNumber = rawNumber.replaceFirst(replace.toRegex(), "+")
                    editText.removeTextChangedListener(this)
                    editText.setText(rawNumber)
                    editText.addTextChangedListener(this)
                    editText.setSelection(rawNumber.length)
                }

                var number: Phonenumber.PhoneNumber? = null
                try {
                    number = parsePhoneNumber(rawNumber)

                    country?.let {
                        if (it.dialCode != number.countryCode) {
                            selectCountry(number.countryCode)
                        }
                    }
                } catch (e: NumberParseException) {
                    onPhoneFieldError(e)
                }

                if (number == null)
                    return

                getPhoneFieldOptions().also {
                    if (!it.enableAutoFormatting)
                        return@also
                    if (rawNumber.length < it.autoFormattingAtLength)
                        return@also
                    if (!phoneUtil.isValidNumber(number))
                        return@also

                    editText.removeTextChangedListener(this)
                    editText.setTextKeepState(
                        phoneUtil.format(
                            number,
                            PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL
                        )
                    )
                    editText.setSelection(editText.text.toString().length)
                    editText.addTextChangedListener(this)
                }
            }
        }
        editText.addTextChangedListener(originalTextWatcher)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                country = adapter.getItem(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                country = null
            }
        }
    }

    /**
     * Whether the entered phone number is valid or not.
     *
     * @return A boolean that indicates whether the number is of a valid pattern
     */
    val isValid: Boolean
        get() = try {
            phoneUtil.isValidNumber(parsePhoneNumber(rawInput))
        } catch (e: NumberParseException) {
            onPhoneFieldError(e)
            false
        }

    @Throws(NumberParseException::class)
    private fun parsePhoneNumber(number: String): Phonenumber.PhoneNumber {
        val defaultRegion =
            if (country != null) country!!.code.uppercase(Locale.getDefault()) else ""
        return phoneUtil.parseAndKeepRawInput(number, defaultRegion)
    }

    /**
     * Gets phone number.
     *
     * @return The phone number
     */
    var phoneNumber: String
        get() {
            try {
                val number: Phonenumber.PhoneNumber = parsePhoneNumber(rawInput)
                return phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164)
            } catch (e: NumberParseException) {
                onPhoneFieldError(e)
            }
            return rawInput
        }
        set(rawNumber) {
            try {
                val number: Phonenumber.PhoneNumber = parsePhoneNumber(rawNumber)
                if (country == null || country!!.dialCode != number.countryCode) {
                    selectCountry(number.countryCode)
                }
                editText.setText(
                    phoneUtil.format(
                        number, PhoneNumberUtil.PhoneNumberFormat.NATIONAL
                    )
                )
            } catch (e: NumberParseException) {
                onPhoneFieldError(e)
            }
        }

    /**
     * Sets default country, if valid
     *
     * @param countryCode Country code
     */
    fun setDefaultCountry(countryCode: String?) {
        for (i in countries.indices) {
            val country: Country = countries[i]
            if (country.code.equals(countryCode, ignoreCase = true)) {
                this.country = country
                defaultCountryPosition = i
                spinner.setSelection(i)
            }
        }
    }

    private fun selectCountry(dialCode: Int) {
        for (i in countries.indices) {
            val country: Country = countries[i]
            if (country.dialCode == dialCode) {
                this.country = country
                spinner.setSelection(i)
            }
        }
    }

    private fun hideKeyboard() {
        (context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager).hideSoftInputFromWindow(
            editText.windowToken, 0
        )
    }

    /**
     * Update layout attributes.
     */
    protected abstract fun setUpLayout()

    /**
     * Gets layout res id.
     *
     * @return the layout res id
     */
    abstract fun getLayoutResId(): Int

    /**
     * Sets hint.
     *
     * @param resId the res id
     */
    open fun setHint(resId: Int) {
        editText.setHint(resId)
    }

    /**
     * Gets raw input.
     *
     * @return the raw input
     */
    val rawInput: String get() = editText.text.toString()

    /**
     * Sets error.
     *
     * @param error the error
     */
    open fun setError(error: String?) {
        editText.error = error
    }
}

