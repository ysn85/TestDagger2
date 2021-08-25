package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TestCoroutines : AppCompatActivity(), CoroutineScope /*by MainScope()*/ {
    private lateinit var mJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name: String? = null
        mJob = launch {
            val deferred1 = GlobalScope.async {
                getAge()
            }
            val deferred2 = GlobalScope.async {
                getName()
            }
            val plusTime = deferred1.await() + deferred2.await()
            Log.i(TAG, "launch end plusTime = $plusTime")
        }
    }

    private suspend fun getName(): Int {
        delay(2000)
        Log.i(TAG, "getName start")
        return 18
    }

    private suspend fun getAge(): Int {
        delay(3000)
        Log.i(TAG, "getAge start")
        return 17
    }

    companion object {
        const val TAG = "TestCoroutines_"
    }

    override val coroutineContext: CoroutineContext
        get() = TODO("Not yet implemented")
}