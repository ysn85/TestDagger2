package com.live.demo

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TestDataSource : DataSource {
    private val mNameData = MutableLiveData<String>()
    override fun getData(): LiveData<String> {
        return mNameData
    }

    override suspend fun fetchData() {
        withContext(Dispatchers.Main) {
            mNameData.value = "Hello LiveData"
            mNameData.value = fetchRealData()
        }
    }

    private suspend fun fetchRealData(): String = withContext(Dispatchers.IO) {
        if (Looper.myLooper() === Looper.getMainLooper()) {
            Log.i(TestViewModel.TAG, "fetchRealData on MainThread ")
        } else {
            Log.i(TestViewModel.TAG, "fetchRealData on SubThread ")
        }
        delay(2000)
        "Hello new Data"
    }
}

interface DataSource {
    fun getData(): LiveData<String>
    suspend fun fetchData()
}