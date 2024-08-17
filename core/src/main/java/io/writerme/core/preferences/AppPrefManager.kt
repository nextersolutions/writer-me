package io.writerme.core.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.FormatUtils.ZERO
import java.util.Date

class AppPrefManager(
    private var context: Application
) {
    private var preferences: SharedPreferences? = null

    var userId: String
        get() = read(USER_ID_KEY) ?: EMPTY
        set(value) {
            write(USER_ID_KEY, value)
        }

    var phoneNumber: String
        get() = read(PHONE_NUMBER_KEY) ?: EMPTY
        set(value) {
            write(PHONE_NUMBER_KEY, value)
        }

    var passcode: String
        get() = read(PASSCODE_KEY) ?: EMPTY
        set(value) {
            write(PASSCODE_KEY, value)
        }

    var email: String
        get() = read(EMAIL_KEY) ?: EMPTY
        set(value) {
            write(EMAIL_KEY, value)
        }

    var firstName: String
        get() = read(FIRST_NAME_KEY) ?: EMPTY
        set(value) {
            write(FIRST_NAME_KEY, value)
        }

    var lastName: String
        get() = read(LAST_NAME_KEY) ?: EMPTY
        set(value) {
            write(LAST_NAME_KEY, value)
        }

    var date: Date?
        get() = readDate(BIRTH_DATE_KEY)
        set(value) {
            writeDate(BIRTH_DATE_KEY, value)
        }

    companion object {
        private const val TAG = "AppConfigPrefManager"
        private const val PREF_KEY = "io.writerme.core.preferences.$TAG"
        private const val USER_ID_KEY = "ID_KEY1"
        private const val PHONE_NUMBER_KEY = "PHONE_NUMBER_KEY"
        private const val PASSCODE_KEY = "PASSCODE_KEY"
        private const val EMAIL_KEY = "EMAIL_KEY"
        private const val FIRST_NAME_KEY = "FIRST_NAME_KEY"
        private const val LAST_NAME_KEY = "LAST_NAME_KEY"
        private const val BIRTH_DATE_KEY = "BIRTH_DATE_KEY"
    }

    fun isPersonalInfoExist(): Boolean {
        return firstName.isNotEmpty() && lastName.isNotEmpty() && date != null
    }

    private fun getPreferences(): SharedPreferences {
        if (preferences == null) {
            preferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        }
        return preferences ?: context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
    }

    private fun readBoolean(key: String?): Boolean? {
        return getPreferences()?.getBoolean(key, true)
    }

    private fun writeBoolean(key: String?, value: Boolean): Boolean {
        val editor = getPreferences()?.edit()
        editor?.putBoolean(key, value)
        return editor?.commit() ?: false
    }

    private fun write(key: String?, value: String): Boolean {
        val editor = getPreferences()?.edit()
        editor?.putString(key, value)
        return editor?.commit() ?: false
    }

    private fun read(key: String?): String? {
        return getPreferences()?.getString(key, EMPTY)
    }

    private fun delete(key: String?): Boolean {
        val editor = getPreferences()?.edit()
        editor?.remove(key)
        return editor?.commit() ?: false
    }

    private fun readDate(key: String): Date? {
        return try {
            val zero = ZERO.toLong()
            val value = getPreferences().getLong(key, zero)
            if (value != zero) {
                Date(value)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun writeDate(key: String, date: Date?): Boolean {
        val editor = getPreferences()?.edit()
        if (date != null) {
            editor?.putLong(key, date.time)
        }
        return editor?.commit() ?: false
    }
}
