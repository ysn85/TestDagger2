package com.annotation

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
                val obj = it.type.getConstructor().newInstance()
                obj.javaClass.declaredFields.forEach { value ->
                    value.isAccessible = true
                    when (value.name) {
                        "name" -> {
                            if ("java.lang.String" == value.type.name) {
                                value.set(obj, personAnnotation.name)
                            }
                        }
                        "age" -> {
                            if ("int" == value.type.name) {
                                value.set(obj, personAnnotation.age)
                            }
                        }
                    }
                }
                it.set(any, obj)
            }
        }
    }
}