package com.live.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TestViewModel(private val dataSource: DataSource) : ViewModel() {

    fun getData(): LiveData<String> {
        return dataSource.getData()
    }

    fun fetchData() {
        dataSource.fetchData()
    }

    object LiveDataVMFactory : ViewModelProvider.Factory {
        private val mDs = TestDataSource()
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TestViewModel(mDs) as T
        }
    }
}