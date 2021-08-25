package com.example.myapplication

object AnnotationMgr {
    fun injectStudentProp(any: Any) {
        val clazz = any::class.java
        val fields = clazz.declaredFields
        for (field in fields) {
            if (field.isAnnotationPresent(PersonAnnotation::class.java)) {
                val personAnnotation = field.getAnnotation(PersonAnnotation::class.java)
                if (field.type == Student::class.java) {
                    field.isAccessible = true
                    val student = Student().apply {
                        this.age = personAnnotation.age
                        this.name = personAnnotation.name
                    }
                    field.set(any, student)
                }
            }
        }
    }
}