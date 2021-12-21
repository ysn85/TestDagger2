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
}