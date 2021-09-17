package com.annotation

@TestAnnotation
class Student {
    @TestAnnotation
    var name: String? = null
    var age: Int = 0

    @TestAnnotation
    fun getStudentInfo(): String {
        return "name is $name age is $age"
    }
}