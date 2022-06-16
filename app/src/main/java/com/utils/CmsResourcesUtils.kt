package com.utils

import android.content.Context

/**
 * @author      ext.yangshaoning1@jd.com
 * @date        2022/6/8 17:42
 * @description 描述
 */
object CmsResourcesUtils {

    @JvmStatic
    fun getString(context: Context, resId: Int): String? {
        if (context == null || resId == 0) return null
        return context.resources.getString(resId)
    }
}