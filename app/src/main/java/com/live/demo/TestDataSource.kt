package com.live.demo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.live.demo.TestViewModel.Companion.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

class TestDataSource : DataSource {
    private val mNameData = MutableLiveData<String>()
    override fun getData(): LiveData<String> {
        return mNameData
    }

    override suspend fun fetchData() {
        withContext(Dispatchers.Main) {
            mNameData.value = "Loading..."
            mNameData.value = fetchRealData()
            mNameData.value = fetchDataFromNet()
        }
    }

    private suspend fun fetchDataFromNet(): String = withContext(Dispatchers.IO) {
//        BtsStringBuilder.of()
        Log.i(TAG, "fetchDataFromNet start")
        // 子线程需要这样更新LiveData数据
        mNameData.postValue("Loading Net")
        delay(2000)
        val url = URL("https://www.baidu.com/")
        var inputStream: InputStream? = null
        var bufferedReader: BufferedReader? = null
        val stringBuilder = StringBuilder()
        try {
            val httpURLConnection = url.openConnection()
            httpURLConnection.connect()
            inputStream = httpURLConnection.getInputStream()
            bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var readLineStr: String? = bufferedReader.readLine()
            while (readLineStr != null) {
                stringBuilder.append(readLineStr).append("\n")
                readLineStr = bufferedReader.readLine()
            }
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        } finally {
            bufferedReader?.close()
            inputStream?.close()
        }
        Log.i(TAG, "fetchDataFromNet end")
        if (stringBuilder.isEmpty()) {
            "Hello empty Data"
        } else {
            stringBuilder.toString()
        }
    }

    private suspend fun fetchRealData(): String = withContext(Dispatchers.IO) {
        Log.i(TAG, "fetchRealData start")
//        BtsStringBuilder.of()
        delay(2000)
        "Hello new Data"
    }
}

interface DataSource {
    fun getData(): LiveData<String>
    suspend fun fetchData()
}