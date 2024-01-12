package com.example.lunchrecommendation.util

import android.content.Context
import com.example.lunchrecommendation.BuildConfig


class PreferencesUtil {

    companion object {

        var context: Context? = null

        const val PREF_NAME = BuildConfig.APPLICATION_ID

        // Set Preferences
        @JvmStatic
        fun setPreferencesString(key: String, value: String) =
            context!!.getSharedPreferences(PREF_NAME, 0).edit()
                .putString(key, value)
                .commit()

        @JvmStatic
        fun setPreferencesInt(key: String, value: Int) =
            context!!.getSharedPreferences(PREF_NAME, 0).edit()
                .putInt(key, value)
                .commit()

        @JvmStatic
        fun setPreferencesFloat(key: String, value: Float) =
            context!!.getSharedPreferences(PREF_NAME, 0).edit()
                .putFloat(key, value)
                .commit()

        @JvmStatic
        fun setPreferencesLong(key: String, value: Long) =
            context!!.getSharedPreferences(PREF_NAME, 0).edit()
                .putLong(key, value)
                .commit()

        @JvmStatic
        fun setPreferencesBoolean(key: String, value: Boolean) =
            context!!.getSharedPreferences(PREF_NAME, 0).edit()
                .putBoolean(key, value)
                .commit()

        // Get Preferences
        @JvmStatic
        fun getPreferencesString(key: String): String =
            context!!.getSharedPreferences(PREF_NAME, 0).getString(key, "") ?: ""

        @JvmStatic
        fun getPreferencesInt(key: String): Int =
            context!!.getSharedPreferences(PREF_NAME, 0).getInt(key, 0)

        @JvmStatic
        fun getPreferencesFloat(key: String): Float =
            context!!.getSharedPreferences(PREF_NAME, 0).getFloat(key, 0f)

        @JvmStatic
        fun getPreferencesLong(key: String): Long =
            context!!.getSharedPreferences(PREF_NAME, 0).getLong(key, 0L)

        @JvmStatic
        fun getPreferencesBoolean(key: String): Boolean =
            context!!.getSharedPreferences(PREF_NAME, 0).getBoolean(key, false)

        // Delete Preferences
        @JvmStatic
        fun deletePreferences(key: String): Boolean =
            context!!.getSharedPreferences(PREF_NAME, 0).edit()
                .remove(key)
                .commit()

        // Set Preferences for String Set
        @JvmStatic
        fun setPreferencesStringSet(key: String, values: Set<String>) =
            context!!.getSharedPreferences(PREF_NAME, 0).edit()
                .putStringSet(key, values)
                .commit()

        // Get Preferences for String Set
        @JvmStatic
        fun getPreferencesStringSet(key: String): Set<String> =
            context!!.getSharedPreferences(PREF_NAME, 0).getStringSet(key, setOf()) ?: setOf()

        // Delete Preferences for String Set
        @JvmStatic
        fun deletePreferencesStringSet(key: String): Boolean =
            context!!.getSharedPreferences(PREF_NAME, 0).edit()
                .remove(key)
                .commit()

    }
}