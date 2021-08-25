package com.dagger

import android.util.Log

class Customer {
    fun eat() {
        Log.i(TAG, "eat")
    }

    companion object {
        const val TAG = "Customer"
    }
}