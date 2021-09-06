package com.live.demo

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TestViewModel(private val dataSource: DataSource) : ViewModel() {

    fun getData(): LiveData<String> {
        return dataSource.getData()
    }

    fun fetchData() {
        viewModelScope.launch {
            if (Looper.myLooper() === Looper.getMainLooper()) {
                Log.i(TAG, "fetchData on MainThread ")
            } else {
                Log.i(TAG, "fetchData on SubThread ")
            }
            dataSource.fetchData()
        }
    }

    object LiveDataVMFactory : ViewModelProvider.Factory {
        private val mDs = TestDataSource()
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TestViewModel(mDs) as T
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        const val TAG = "TestViewModel"
    }
}