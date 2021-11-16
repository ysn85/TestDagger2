package com.live.demo

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TestViewModel(private val dataSource: DataSource) : ViewModel() {
    private val mData = MediatorLiveData<String>()

    init {
        mData.addSource(dataSource.getDataFromNet()) {
            mData.postValue(it)
        }

        mData.addSource(dataSource.getDataFromLocal()) {
            mData.postValue(it)
        }
    }

    fun getData(): LiveData<String> {
        return mData
    }

    fun fetchNetData() {
        viewModelScope.launch {
            if (isMainThread()) {
                Log.i(TAG, "fetchNetData on MainThread ")
            } else {
                Log.i(TAG, "fetchNetData on SubThread ")
            }
            dataSource.fetchNetData()
        }
    }

    fun fetchLocalData() {
        viewModelScope.launch {
            if (isMainThread()) {
                Log.i(TAG, "fetchLocalData on MainThread ")
            } else {
                Log.i(TAG, "fetchLocalData on SubThread ")
            }
            dataSource.fetchLocalData()
        }
    }


    private fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }

    class LiveDataVMFactory(private val context: Context) : ViewModelProvider.Factory {
        private val mDs = TestDataSource()
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            Log.i(TAG, "create Model from pkg ${context.packageName}");
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