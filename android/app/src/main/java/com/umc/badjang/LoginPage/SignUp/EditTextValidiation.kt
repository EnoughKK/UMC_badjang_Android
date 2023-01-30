package com.umc.badjang.LoginPage.SignUp

import com.google.android.material.textfield.TextInputLayout
import com.umc.badjang.R
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object EditTextValidiation {

    fun validEmail(
        textInputLayout: TextInputLayout
    ): Boolean {
        val validEmail =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val matcherEmail: Matcher =
            Pattern.compile(validEmail)
                .matcher(textInputLayout.editText?.text.toString().toLowerCase(Locale.ROOT))

        return if (matcherEmail.matches()) {
            textInputLayout.error = null
            true
        } else {
            textInputLayout.error =
                textInputLayout.context.resources.getString(R.string.invalidemail)
            textInputLayout.editText?.requestFocus()
            false
        }
    }

    fun validPhone(mPhoneEdit: TextInputLayout): Boolean {
        val pattern: Pattern

        val phone = "\\d+"
        pattern = Pattern.compile(phone)
        val matcher: Matcher = pattern.matcher(mPhoneEdit.editText?.text.toString())
        return if (matcher.matches()) {
            mPhoneEdit.error = null
            true
        } else {
            mPhoneEdit.error = mPhoneEdit.context.resources.getString(R.string.invalidPhone)
            mPhoneEdit.requestFocus()
            false
        }
    }

    fun validBirthdate(textInputLayout: TextInputLayout):Boolean{
        val pattern: Pattern
        val birthdatePattern = "^[0-9]{11}$"
        pattern = Pattern.compile(birthdatePattern)
        val matcher: Matcher = pattern.matcher(textInputLayout.editText?.text.toString())
        return if (matcher.matches()) {
            textInputLayout.error = null
            true
        } else {
            textInputLayout.error =
                textInputLayout.context.resources.getString(R.string.invalidbirthdate)
            textInputLayout.editText?.requestFocus()
            false
        }

    }


    fun validPassword(
        textInputLayout: TextInputLayout
    ): Boolean {
        val pattern: Pattern

        val passwordPattern = "^[A-Za-z0-9]{2,20}$"
        pattern = Pattern.compile(passwordPattern)
        val matcher: Matcher = pattern.matcher(textInputLayout.editText?.text.toString())
        return if (matcher.matches()) {
            textInputLayout.error = null
            true
        } else {
            textInputLayout.error =
                textInputLayout.context.resources.getString(R.string.invalidPassword)
            textInputLayout.editText?.requestFocus()
            false
        }
    }

    fun validConfirmPassword(
        Password: TextInputLayout,
        confirmInputLayout: TextInputLayout
    ): Boolean {
        return if (confirmInputLayout.editText?.text.toString().isEmpty()) {
            true
        } else {
            if (confirmInputLayout.editText?.text.toString() != Password.editText?.text.toString()) {
                confirmInputLayout.error =
                    confirmInputLayout.context.getString(R.string.password_match)
                confirmInputLayout.editText?.requestFocus()
                false
            } else {
                confirmInputLayout.error = null
                true
            }
        }
    }


    fun validUserName(
        textInputLayout: TextInputLayout
    ): Boolean {
        val validName = "^(?![ .]*$)[\\w\\d\\p{L} .]*$"

        val mName = textInputLayout.editText?.text.toString().trim().toLowerCase(Locale.ROOT)
        val matcherName: Matcher = Pattern.compile(validName).matcher(mName)
        return if (matcherName.matches() && textInputLayout.editText?.text.toString()
                .trim().isNotEmpty()
        ) {
            textInputLayout.error = null
            true
        } else {
            textInputLayout.error =
                textInputLayout.context.resources.getString(R.string.invalid_user_name)
            textInputLayout.editText?.requestFocus()
            false
        }
    }

    fun validName(
        textInputLayout: TextInputLayout
    ): Boolean {
//        val validName = "^(?![ .]*$)[\\w\\d\\p{L} .]*$"
        val validName = "^[_A-Za-z0-9]{2,20}$"

        val mName = textInputLayout.editText?.text.toString().trim().toLowerCase(Locale.ROOT)
        val matcherName: Matcher = Pattern.compile(validName).matcher(mName)
        return if (matcherName.matches() && textInputLayout.editText?.text.toString()
                .trim().isNotEmpty()
        ) {
            textInputLayout.error = null
            true
        } else {
            textInputLayout.error =
                textInputLayout.context.resources.getString(R.string.invalid_name)
            textInputLayout.editText?.requestFocus()
            false
        }
    }

    fun validEditText(
        textInputLayout: TextInputLayout
    ): Boolean {

        return if (textInputLayout.editText?.text.toString()
                .trim().isNotEmpty()
        ) {
            textInputLayout.error = null
            true
        } else {
            textInputLayout.error =
                textInputLayout.context.resources.getString(R.string.invalid_name)
            textInputLayout.editText?.requestFocus()
            false
        }
    }
}