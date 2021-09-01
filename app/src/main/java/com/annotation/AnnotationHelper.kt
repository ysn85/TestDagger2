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
                                try {
                                    value.set(obj, personAnnotation.name)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                        "age" -> {
                            if (Int::class.java == value.type) {
                                try {
                                    value.set(obj, personAnnotation.age)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                }
                try {
                    it.set(any, obj)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun bind(activity: Activity) {
        val clazz = activity::class.java
        val fields = clazz.declaredFields
        val sourceView: View = activity.window.decorView
        val viewClickArray = hashMapOf<Int, View>()
        fields.forEach {
            // 检查当前元素是否被指定注解修饰
            if (it.isAnnotationPresent(TestBindView::class.java)) {
                // 查找指定注解
                val annotation = it.getAnnotation(TestBindView::class.java)
                it.isAccessible = true
                val id = annotation.value
                val obj = sourceView.findViewById<View>(id)
                viewClickArray[id] = obj
                try {
                    it.set(activity, obj)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        val methods = clazz.declaredMethods
        methods.forEach { method ->
            if (method.isAnnotationPresent(TestOnClick::class.java)) {
                val annotation = method.getAnnotation(TestOnClick::class.java)
                method.isAccessible = true
                val id = annotation.value
                val obj = viewClickArray[id] ?: sourceView.findViewById(id)
                obj.setOnClickListener {
                    try {
                        method.invoke(activity, it)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        viewClickArray.clear()
    }
}