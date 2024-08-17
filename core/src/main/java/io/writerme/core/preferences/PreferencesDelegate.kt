package io.writerme.core.preferences

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T : Any> SharedPreferences.with(key: String, defValue: T) =
    PreferencesDelegate(this, key, defValue)

@Suppress("UNCHECKED_CAST")
class PreferencesDelegate<T : Any>(
    private val preferences: SharedPreferences,
    private val key: String,
    private val defValue: T
) : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        with(preferences) {
            val value = when (defValue) {
                is Boolean -> getBoolean(key, defValue)
                is Int -> getInt(key, defValue)
                is Float -> getFloat(key, defValue)
                is Long -> getLong(key, defValue)
                is String -> getString(key, defValue)
                else -> throw IllegalArgumentException("This type can't be obtained from preferences.")
            }
            return (value as? T) ?: defValue
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        with(preferences.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException("This type can't be saved into preferences.")
            }
            apply()
        }
    }
}
