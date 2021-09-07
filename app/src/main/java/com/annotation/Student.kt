package com.annotation

class Student {
    var name: String? = null
    var age: Int = 0

    @Deprecated(
        message = "age illegal",
        replaceWith = ReplaceWith("${"%d"}", "00"),
        level = DeprecationLevel.WARNING
    )
    @TestAnnotation
    fun getSuper(): Int {
        return age
    }

    override fun toString(): String {
        return "StudentJ{" +
                "age=" + age +
                "name=" + name +
                "}"
    }
}