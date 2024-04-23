kotlin-intl-android (Kotlin International Android) or kia for short
===================

kotlin-intl-android is an Android kotlin library that provides internationalization features/views.

![alt text](https://firebasestorage.googleapis.com/v0/b/images-de159.appspot.com/o/kotlin-intl-android-sample.gif?alt=media&token=edaa9b0a-73a8-4cc3-acba-426cc63ab94d "Sample")

Most important:

 * `PhoneNumberEditText` : includes EditText alongside the flags spinner
 * `PhoneNumberInputLayout` : includes a TextInputLayout from Google's material library alongside the flags spinner 
 * `PhoneNumberFieldOptions`: Phone field options/configuration like enabling automatic phone number formatting. For example: `PhoneNumberFieldOptions(enableLog = false, enableAutoFormatting = true, autoFormattingAtLength = 9)`
 
## Features
 
 * Displays the correct country flag if the user enters a valid international phone number
 * Allows the user to choose the country manually or enter an international phone number with country code
 * Validates and formats the phone number 
 * Returns the valid phone number including the country code
 
## Usage

PhoneNumberInputLayout 
 
```xml
<com.nativeframe.kotlin.intl.android.view.PhoneNumberInputLayout
     android:id="@+id/phone_input_layout"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"/>
```
 
PhoneNumberEditText
 
```xml
 <com.nativeframe.kotlin.intl.android.view.PhoneNumberEditText
     android:id="@+id/edit_text"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"/>
```
 
```kotlin 
phoneNumberInputLayout.setDefaultCountry("DE");
phoneNumberEditText.setDefaultCountry("US");

button.setOnClickListener{
    var valid = true
    
    if (phoneNumberInputLayout.isValid) {
      phoneNumberInputLayout.setError(null)
    } else {
      phoneNumberInputLayout.setError("invalid")
      valid = false
    }

    if (phoneNumberEditText.isValid) {
      phoneNumberEditText.setError(null)
    } else {
      phoneNumberEditText.setError("invalid")
      valid = false
    }

    if (valid) {
      Toast.makeText(this, R.string.valid, Toast.LENGTH_LONG).show()
    } else {
      Toast.makeText(this, R.string.invalid, Toast.LENGTH_LONG).show()
    }
    
    val phoneNumber = phoneNumberInputLayout.phoneNumber
}
 
```

## Customization

You can extend `PhoneNumberInputLayout` or `PhoneNumberEditText` and provide your own xml but you will also need to override `findSpinner()` and `findEditText()`

## Motivation

This library was built for these reasons: 
 
 * Other Android libraries have aged a bit
 * This library is written in Kotlin, instead of Java
 * Ability to customize by overriding `getPhoneFieldOptions()`

## Attributions  

 1. Inspired by [android-phone-field](https://github.com/lamudi-gmbh/android-phone-field)
 2. Flag images from [GoSquared](https://www.gosquared.com/resources/flag-icons/)
 3. Original country data from mledoze's [World countries in JSON, CSV and XML](https://github.com/mledoze/countries)
 4. Formatting/validation using [libphonenumber](https://github.com/googlei18n/libphonenumber)


 ## Sample

 Start activity: `com.nativeframe.kotlinintlandroid.MainActivity` in the app module

 ## Unit Tests

 Run `./gradlew :kotlin-intl-android:test` and/or `./gradlew :kotlin-intl-android:connectedDebugAndroidTest`

## Coming Soon

* Maven hosting for releases