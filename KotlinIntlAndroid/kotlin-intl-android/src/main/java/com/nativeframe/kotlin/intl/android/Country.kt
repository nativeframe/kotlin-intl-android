package com.nativeframe.kotlin.intl.android

import androidx.annotation.DrawableRes
import java.util.Locale

/**
 * Country
 *
 * @param code Country code/abbreviation
 * @param name Country name
 * @param dialCode Phone dial code
 * @param drawable Flag image
 */
data class Country(
    val code: String,
    val name: String,
    val dialCode: Int,
    @DrawableRes val drawable: Int
) {
    val displayName: String = Locale("", code).getDisplayCountry(Locale.US)
}