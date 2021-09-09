package com.annotation

import android.app.Activity
import android.app.Dialog
import android.view.View

object BtsBindHelper {
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
        bind(activity, activity)
    }

    fun bind(target: Any, activity: Activity) {
        bind(target, activity.window.decorView)
    }

    fun bind(dialog: Dialog) {
        bind(dialog, dialog)
    }

    fun bind(target: Any, dialog: Dialog) {
        dialog.window?.let { bind(target, it.decorView) }
    }

    fun bind(target: Any, view: View) {
        val clazz = target::class.java
        val fields = clazz.declaredFields
        val sourceView = view
        val viewClickArray = hashMapOf<Int, View>()
        fields.forEach { field ->
            // 检查当前元素是否被指定注解修饰
            if (field.isAnnotationPresent(BtsBindView::class.java)) {
                // 查找指定注解
                val annotation = field.getAnnotation(BtsBindView::class.java)
                field.isAccessible = true
                val id = annotation.value
                val obj = sourceView.findViewById<View>(id)
                viewClickArray[id] = obj
                try {
                    field.set(target, obj)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (field.isAnnotationPresent(BtsBindViews::class.java)) {
                // 查找指定注解
                val annotation = field.getAnnotation(BtsBindViews::class.java)
                field.isAccessible = true
                val ids = annotation.value
                val views = mutableListOf<View>() /*when (field.type) {
                    ArrayList::class.java -> arrayListOf<View>()
                    else -> mutableListOf()
                }*/

                ids.forEachIndexed { _, value ->
                    val obj = sourceView.findViewById<View>(value)
                    views.add(obj)
                    viewClickArray[value] = obj
                }
                try {
                    field.set(target, views)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        val methods = clazz.declaredMethods
        methods.forEach { method ->
            if (method.isAnnotationPresent(BtsOnClick::class.java)) {
                val annotation = method.getAnnotation(BtsOnClick::class.java)
                method.isAccessible = true
                val ids = annotation.value
                ids.forEachIndexed { _, value ->
                    val obj = viewClickArray[value] ?: sourceView.findViewById(value)
                    obj.setOnClickListener { view ->
                        try {
                            method.invoke(target, view)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        viewClickArray.clear()
    }
}