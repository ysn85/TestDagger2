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
                obj?.javaClass?.declaredFields?.forEach { field ->
                    field.isAccessible = true
                    when (field.name) {
                        "name" -> {
                            if (String::class.java == field.type) {
                                try {
                                    field.set(obj, personAnnotation.name)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                        "age" -> {
                            if (Int::class.java == field.type) {
                                try {
                                    field.set(obj, personAnnotation.age)
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
        fields.forEach { field ->
            // 检查当前元素是否被指定注解修饰
            if (field.isAnnotationPresent(TestBindView::class.java)) {
                // 查找指定注解
                val annotation = field.getAnnotation(TestBindView::class.java)
                field.isAccessible = true
                val id = annotation.value
                val obj = sourceView.findViewById<View>(id)
                viewClickArray[id] = obj
                try {
                    field.set(activity, obj)
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
                obj.setOnClickListener { view ->
                    try {
                        method.invoke(activity, view)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        viewClickArray.clear()
    }
}