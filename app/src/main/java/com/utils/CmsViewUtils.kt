package com.utils

import android.view.View

/**
 * @author      ext.yangshaoning1@jd.com
 * @date        2022/6/8 17:57
 * @description 描述
 */
object CmsViewUtils {
    @JvmStatic
    fun View.visible() {
        if (this.visibility != View.VISIBLE)
            this.visibility = View.VISIBLE
    }

    @JvmStatic
    fun View.invisible() {
        if (this.visibility != View.INVISIBLE)
            this.visibility = View.INVISIBLE
    }

    @JvmStatic
    fun View.gone() {
        if (this.visibility != View.GONE)
            this.visibility = View.GONE
    }
}