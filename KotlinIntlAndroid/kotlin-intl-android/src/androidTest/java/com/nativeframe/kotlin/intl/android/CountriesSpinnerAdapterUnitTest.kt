package com.nativeframe.kotlin.intl.android

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nativeframe.kotlin.intl.android.ui.CountriesSpinnerAdapter
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CountriesSpinnerAdapterUnitTest {
    @Test
    fun countriesSpinnerAdapterTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val adapter = CountriesSpinnerAdapter(appContext, COUNTRIES.filter {
            "us,mx".contains(it.code)
        }.toTypedArray())

        assertEquals("adapter count is wrong", 2, adapter.count)
    }
}