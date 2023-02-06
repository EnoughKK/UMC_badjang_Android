package com.umc.badjang.LoginPage.SignUp

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
        private val MY_ACCOUNT : String = "account"

        fun setUserEmail(context: Context, input: String) {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = prefs.edit()
            editor.putString("MY_EMAIL", input)
            editor.commit()
        }

        fun getUserEmail(context: Context): String {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            return prefs.getString("MY_EMAIL", "").toString()
        }

        fun setUserPass(context: Context, input: String) {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = prefs.edit()
            editor.putString("MY_PASS", input)
            editor.commit()
        }

        fun getUserCon(context: Context): String {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            return prefs.getString("MY_CON", "").toString()
        }

        fun setUserCon(context: Context, input: String) {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = prefs.edit()
            editor.putString("MY_CON", input)
            editor.commit()
        }

        fun getUserName(context: Context): String {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            return prefs.getString("MY_NAME", "").toString()
        }

        fun setUserName(context: Context, input: String) {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = prefs.edit()
            editor.putString("MY_NAME", input)
            editor.commit()
        }
        fun getUserPhone(context: Context): String {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            return prefs.getString("MY_PHONE", "").toString()
        }

        fun setUserPhone(context: Context, input: String) {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = prefs.edit()
            editor.putString("MY_PHONE", input)
            editor.commit()
        }

        fun getUserBirth(context: Context): String {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            return prefs.getString("MY_BIRTH", "").toString()
        }

        fun setUserBirth(context: Context, input: String) {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = prefs.edit()
            editor.putString("MY_BIRTH", input)
            editor.commit()
        }


        fun clearUser(context: Context) {
            val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = prefs.edit()
            editor.clear()
            editor.commit()
        }

    }

