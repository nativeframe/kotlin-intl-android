package com.nativeframe.kotlinintlandroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nativeframe.kotlin.intl.android.view.PhoneNumberEditText
import com.nativeframe.kotlin.intl.android.view.PhoneNumberInputLayout
import com.nativeframe.kotlinintlandroid.view.CustomPhoneNumberInputLayout

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val phoneInputLayout = findViewById<PhoneNumberInputLayout>(R.id.phone_input_layout)
        val phoneNumberEditText = findViewById<PhoneNumberEditText>(R.id.edit_text)
        val button = findViewById<Button>(R.id.submit_button)

        val customPhoneInputLayout = CustomPhoneNumberInputLayout(this, "MX" /*Mexico */)
        with(findViewById<LinearLayout>(R.id.fields)) {
            addView(TextView(this@MainActivity, null).apply {
                text = "CustomPhoneNumberInputLayout"
            }, 4)
            addView(customPhoneInputLayout, 5)
        }

        phoneInputLayout.setHint(R.string.phone_hint)
        phoneInputLayout.setDefaultCountry("US")
        phoneNumberEditText.setHint(R.string.phone_hint)
        phoneNumberEditText.setDefaultCountry("FR")

        button.setOnClickListener {
            phoneInputLayout.setError(null)
            phoneNumberEditText.setError(null)
            customPhoneInputLayout.setError(null)

            if (!phoneInputLayout.isValid) {
                phoneInputLayout.setError(getString(R.string.invalid_phone_number))
                return@setOnClickListener
            }

            if (!phoneNumberEditText.isValid) {
                phoneNumberEditText.setError(getString(R.string.invalid_phone_number))
                return@setOnClickListener
            }

            if (!customPhoneInputLayout.isValid) {
                customPhoneInputLayout.setError(getString(R.string.invalid_phone_number))
                return@setOnClickListener
            }

            Toast.makeText(this@MainActivity, R.string.valid_phone_number, Toast.LENGTH_LONG)
                .show()
        }
    }
}

