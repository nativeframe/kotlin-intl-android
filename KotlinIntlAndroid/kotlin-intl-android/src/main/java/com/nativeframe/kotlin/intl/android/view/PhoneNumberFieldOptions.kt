package com.nativeframe.kotlin.intl.android.view

/**
 * Phone field options/configuration
 */
data class PhoneNumberFieldOptions(
    /**
     * Enable logging with [android.util.Log]
     */
    val enableLog: Boolean,

    /**
     * Enable automatic phone number formatting
     */
    val enableAutoFormatting: Boolean,

    /**
     * The length at which auto phone number formatting executes
     */
    val autoFormattingAtLength: Int
)