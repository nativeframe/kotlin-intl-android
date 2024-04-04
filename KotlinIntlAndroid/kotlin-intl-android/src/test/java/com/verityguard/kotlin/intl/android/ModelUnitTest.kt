package com.nativeframe.kotlin.intl.android

import com.nativeframe.kotlin.intl.android.view.PhoneNumberFieldOptions
import org.junit.Test

import org.junit.Assert.*

/**
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ModelUnitTest {
    @Test
    fun countryUnitTest() {
        val c = Country(code = "us", name = "USA", dialCode = 1, R.drawable.country_flag_us)

        assertFalse("displayName should not be blank", c.displayName.isBlank())
    }

    @Test
    fun phoneNumberFieldOptionsTest() {
        PhoneNumberFieldOptions(
            enableLog = true, enableAutoFormatting = true,
            autoFormattingAtLength = 9
        ).apply {
        }
    }
}