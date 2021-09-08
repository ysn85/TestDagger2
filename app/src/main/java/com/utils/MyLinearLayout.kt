package com.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout

class MyLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        log("onAttachedToWindow")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        log("onMeasure")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        log("onLayout")
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        log("onWindowFocusChanged")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        log("onDetachedFromWindow")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        log("onFinishInflate")
    }

    override fun requestLayout() {
        super.requestLayout()
        log("requestLayout")
    }

    private fun log(msg: String) {
        Log.i(TAG, "$msg width = $width, height = $height")
    }

    companion object {
        private const val TAG = "MyLinearLayout"
    }
}