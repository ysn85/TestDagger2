package com.annotation

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.ceil


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
    private var mCol = INVALID_SPACING_VALUE
    private var mItemCount = INVALID_SPACING_VALUE

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
        spanCount: Int = INVALID_SPACING_VALUE,
        col: Int = INVALID_SPACING_VALUE,
        itemCount: Int = INVALID_SPACING_VALUE
    ) {
        mLeftRightSpacing = leftRight
        mTopSpacing = top
        mBottomSpacing = bottom
        mSpanCount = spanCount
        mCol = col
        mItemCount = itemCount
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            if (layoutManager.orientation == GridLayoutManager.VERTICAL) {
                val position: Int = parent.getChildAdapterPosition(view) // item position
                val column =
                    (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex /*position % mSpanCount*/
                outRect.left =
                    column * mLeftRightSpacing / mSpanCount
                outRect.right =
                    mLeftRightSpacing - (column + 1) * mLeftRightSpacing / mSpanCount
                if (position >= mSpanCount && mTopSpacing != INVALID_SPACING_VALUE) {
                    outRect.top = mTopSpacing
                }
                Log.d(
                    "GridDe",
                    "position = ${position}, content = ${(view as TextView).text}, " +
                            "left = ${outRect.left}, top = ${outRect.top}, " +
                            "right = ${outRect.right}" +
                            " column = $column, " +
                            "spanIndex = ${(view.layoutParams as GridLayoutManager.LayoutParams).spanIndex}"
                )
            } else {
                val position: Int = parent.getChildAdapterPosition(view) // item position
                var column: Int
                var div: Int
                if (mSpanCount == 1) {
                    div = mItemCount
                    column = position % div
                } else {
                    div = ceil(mItemCount.toDouble() / mSpanCount).toInt()
                    column = (position / mSpanCount) % div


                    val spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
                    outRect.top = spanIndex * mTopSpacing / mSpanCount
                    outRect.bottom = mTopSpacing - (spanIndex + 1) * mTopSpacing / mSpanCount
                }

                outRect.left = column * mLeftRightSpacing / div
                outRect.right =
                    mLeftRightSpacing - (column + 1) * mLeftRightSpacing / div

                Log.d(
                    "GridDe",
                    "position = ${position}, content = ${(view as TextView).text}, " +
                            "left = ${outRect.left} " +
                            "right = ${outRect.right} " +
                            "top = ${outRect.top} " + "bottom = ${outRect.bottom} " +
                            "column = $column, " +
                            "spanIndex = ${(view.layoutParams as GridLayoutManager.LayoutParams).spanIndex}"
                )
            }
        }
    }
}