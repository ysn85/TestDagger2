package com.live.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TestDataSource : DataSource {
    private val mNameData = MutableLiveData<String>()
    override fun getData(): LiveData<String> {
        return mNameData
    }

    override fun fetchData() {
        mNameData.value = "Hello LiveData"
    }
}

interface DataSource {
    fun getData(): LiveData<String>
    fun fetchData()
}