package com.annotation

import com.sk.dagger.bts_annotation.FuncTimeCost

class Student {
    var name: String? = null
    var age: Int = 0

    fun getStudentInfo(): String {
        return ""
    }

    @FuncTimeCost
    fun getAgeValue(): Int {
        return age
    }

    val stringLengthFunc: (String) -> Int = { input ->
        input.length
    }

    val stringLength: Int = stringLengthFunc("Android")
}