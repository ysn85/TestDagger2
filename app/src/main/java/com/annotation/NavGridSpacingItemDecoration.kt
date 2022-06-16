package com.annotation

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs


/**
 * @author      ext.yangshaoning1@jd.com
 * @date        2022/6/10 14:27
 * @description 自定义九宫格元素横纵间距装饰器
 */
class NavGridSpacingItemDecoration : RecyclerView.ItemDecoration() {
    private var mLeftRightSpacing = INVALID_SPACING_VALUE
    private var mTopSpacing = INVALID_SPACING_VALUE
    private var mBottomSpacing = INVALID_SPACING_VALUE
    private var mSpanCount = INVALID_SPACING_VALUE

    /**
     * 是否包含左右边距
     * true  | A B C |
     * false  |A B C|
     */
//    private var mIncludeEdge = false


    private companion object {
        private const val INVALID_SPACING_VALUE = -1
    }

    fun setSpacing(
        leftRight: Int = INVALID_SPACING_VALUE,
        top: Int = INVALID_SPACING_VALUE,
        bottom: Int = INVALID_SPACING_VALUE,
        spanCount: Int = INVALID_SPACING_VALUE
    ) {
        mLeftRightSpacing = leftRight
        mTopSpacing = top
        mBottomSpacing = bottom
        mSpanCount = spanCount
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position: Int = parent.getChildAdapterPosition(view) // item position
        val rows = position % mSpanCount // 元素所处行号0..n
        val column = abs(position - rows) / mSpanCount

        outRect.left =
            column * mLeftRightSpacing / mSpanCount
        outRect.right =
            mLeftRightSpacing - (column + 1) * mLeftRightSpacing / mSpanCount

//        if (rows == 0) { // 第一行
//            val column = position / 2
//            outRect.left =
//                column * mLeftRightSpacing / mSpanCount // column * ((1f / spanCount) * spacing)
//            outRect.right =
//                mLeftRightSpacing - (column + 1) * mLeftRightSpacing / mSpanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//        } else {
//            val column = (position - 1) / 2
//            outRect.left =
//                column * mLeftRightSpacing / mSpanCount // column * ((1f / spanCount) * spacing)
//            outRect.right =
//                mLeftRightSpacing - (column + 1) * mLeftRightSpacing / mSpanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//        }

        // 1,3,5,7,9
        // 0,2,4,6,8
//        outRect.left =
//            column * mLeftRightSpacing / mSpanCount // column * ((1f / spanCount) * spacing)
//        outRect.right =
//            mLeftRightSpacing - (column + 1) * mLeftRightSpacing / mSpanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
        Log.d(
            "GridDe",
            "position = ${position}, content = ${(view as TextView).text}, left = ${outRect.left}, right = ${outRect.right}, rows = $rows"
        )
        if (rows > 0) {
//            if (mTopSpacing != INVALID_SPACING_VALUE) {
//                outRect.top = mTopSpacing
//            }
//            if (mBottomSpacing != INVALID_SPACING_VALUE) {
//                outRect.bottom = mBottomSpacing
//            }
            outRect.top = mTopSpacing
        }
    }
}