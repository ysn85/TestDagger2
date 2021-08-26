package com.annotation

object AnnotationHelper {
    fun inject(any: Any) {
        val clazz = any::class.java
        val fields = clazz.declaredFields
        fields.forEach {
            if (it.isAnnotationPresent(PersonAnnotation::class.java)) {
                val personAnnotation = it.getAnnotation(PersonAnnotation::class.java)
                it.isAccessible = true
                val obj = it.type.getConstructor().newInstance()
                obj.javaClass.declaredFields.forEach { value ->
                    value.isAccessible = true
                    when (value.name) {
                        "name" -> {
                            value.set(obj, personAnnotation.name)
                        }
                        "age" -> {
                            value.set(obj, personAnnotation.age)
                        }
                    }
                }
                it.set(any, obj)
            }
        }
    }
}