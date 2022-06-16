package com.annotation

/**
 * @author      ext.yangshaoning1@jd.com
 * @date        2022/6/15 14:14
 * @description feed流元素属性赋值注解
 */

@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class FeedFlowProperty(val value: String) {
    companion object {
        /**
         * feed流元素最大宽度
         */
        const val FEED_FLOW_ITEM_MAX_WIDTH_VALUE = "max_width"
    }
}
