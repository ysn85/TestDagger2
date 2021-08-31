package com.annotation

import android.app.Activity
import android.view.View

object AnnotationHelper {
    fun inject(any: Any) {
        val clazz = any::class.java
        val fields = clazz.declaredFields
        fields.forEach {
            // 检查当前元素是否被指定注解修饰
            if (it.isAnnotationPresent(PersonAnnotation::class.java)) {
                // 查找指定注解
                val personAnnotation = it.getAnnotation(PersonAnnotation::class.java)
                it.isAccessible = true
                val obj = try {
                    it.type.getConstructor().newInstance()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                obj?.javaClass?.declaredFields?.forEach { value ->
                    value.isAccessible = true
                    when (value.name) {
                        "name" -> {
                            if (String::class.java == value.type) {
                                value.set(obj, personAnnotation.name)
                            }
                        }
                        "age" -> {
                            if (Int::class.java == value.type) {
                                value.set(obj, personAnnotation.age)
                            }
                        }
                    }
                }
                it.set(any, obj)
            }
        }
    }

    fun bind(activity: Activity) {
        val clazz = activity::class.java
        val fields = clazz.declaredFields
        val sourceView: View = activity.window.decorView
        fields.forEach {
            // 检查当前元素是否被指定注解修饰
            if (it.isAnnotationPresent(TestBindView::class.java)) {
                // 查找指定注解
                val personAnnotation = it.getAnnotation(TestBindView::class.java)
                it.isAccessible = true
                val obj = sourceView.findViewById<View>(personAnnotation.value)
                it.set(activity, obj)
            }
        }
    }
}