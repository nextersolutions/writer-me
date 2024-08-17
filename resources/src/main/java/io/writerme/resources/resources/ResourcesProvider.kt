package io.writerme.resources.resources

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

class ResourcesProvider(
    private val context: Context
) {

    /**
     * Context
     */
    fun getContext(): Context = context.applicationContext
    fun getContentResolver(): ContentResolver = context.contentResolver

    /**
     * Resources
     */
    fun getResources(): Resources = context.resources

    /**
     * String
     */
    fun getString(@StringRes resId: Int) = context.getString(resId)
    fun getString(@StringRes resId: Int, vararg args: Any) = context.getString(resId, *args)

    /**
     * Color
     */
    fun getColor(@ColorRes resId: Int) = context.getColor(resId)
}
