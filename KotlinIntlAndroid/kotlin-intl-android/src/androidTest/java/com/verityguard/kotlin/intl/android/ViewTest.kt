package com.nativeframe.kotlin.intl.android

import android.widget.LinearLayout
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nativeframe.kotlin.intl.android.view.PhoneNumberEditText
import com.nativeframe.kotlin.intl.android.view.PhoneNumberField
import com.nativeframe.kotlin.intl.android.view.PhoneNumberInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ViewTest {
    @Test
    fun phoneNumberEditTextTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val view = PhoneNumberEditText(appContext, null)
        LinearLayout(appContext).addView(view)

        phoneNumberTest(view)
    }

    @Test
    fun phoneNumberInputLayoutTest() = runBlocking(Dispatchers.Main) {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        //prevents runtime error
        appContext.setTheme(androidx.appcompat.R.style.Theme_AppCompat)

        val view = PhoneNumberInputLayout(appContext, null)
        LinearLayout(appContext).addView(view)

        phoneNumberTest(view)
    }

    private fun phoneNumberTest(view: PhoneNumberField) {
        view.editText.setText("+15129991818")

        assertFalse("bad phone number", view.phoneNumber.isBlank())
        assertFalse("bad phone number (raw)", view.rawInput.isBlank())
        assertNotEquals(
            "formatted phone number should not be raw",
            view.phoneNumber, view.rawInput
        )
        assertTrue("no countries", view.countries.isNotEmpty())
    }
}