package com.example.myapplication

import android.util.Log

class TestInline {
    fun main() {
        testFoo {
            Log.i("TestInline", "invoke")
        }
    }

    private inline fun testFoo(cc: () -> Unit) {
        Log.i("TestInline", "start")
        cc.invoke()
        Log.i("TestInline", "end")
    }
}