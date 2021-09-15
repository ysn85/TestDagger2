package com.live.demo

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Contract

class TestViewModel(private val dataSource: DataSource) : ViewModel() {

    //    private val dataSource = TestDataSource()
    fun getData(): LiveData<String> {
        return dataSource.getData()
    }

    @Contract
    fun fetchData() {
        viewModelScope.launch {
            if (isMainThread()) {
                Log.i(TAG, "fetchData on MainThread ")
            } else {
                Log.i(TAG, "fetchData on SubThread ")
            }
//            BtsStringBuilder.of()
            dataSource.fetchData()
        }
    }

    private fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }

    class LiveDataVMFactory : ViewModelProvider.Factory {
        private val mDs = TestDataSource()
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TestViewModel(mDs) as T
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared start")
    }

    companion object {
        const val TAG = "TestViewModel"
    }
}