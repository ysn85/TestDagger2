package com.live.mediator.demo

import android.os.Bundle
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.annotation.BtsBindHelper
import com.annotation.BtsBindView
import com.example.myapplication.R

class TestMediatorActivity : AppCompatActivity() {

    @BtsBindView(R.id.mediator_et)
    private var mEt: EditText? = null

    @BtsBindView(R.id.mediator_content_tv)
    private var mContentTv: TextView? = null

    @BtsBindView(R.id.mediator_count_tv)
    private var mCountTv: TextView? = null

    private val mCountMediatorLiveData = MediatorLiveData<Int>()
    private val mContentLiveData = MutableLiveData<String>()
    private val mCountLiveData = MutableLiveData<Int>()
    private val mCount1LiveData = MutableLiveData<Int>()
    private var mTextWatcher: TextWatcher? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mediator_layout)
        BtsBindHelper.bind(this)

        mTextWatcher = mEt?.addTextChangedListener {
            /*mContentLiveData.value = it?.toString()
            mCountLiveData.value = it?.length*/
            mContentLiveData.postValue(it?.toString())
            val len = it?.length
            mCount1LiveData.postValue(len)
            mCountLiveData.postValue(len)
        }

        mContentLiveData.observe(this, {
            mContentTv?.text = it
        })

        mCountMediatorLiveData.addSource(mCountLiveData, object : Observer<Int> {
            private var count = 0
            override fun onChanged(t: Int?) {
                count++
                if (count > 10) {
                    mCountMediatorLiveData.removeSource(mCountLiveData)
                }
                mCountMediatorLiveData.value = t
            }
        })

        mCountMediatorLiveData.addSource(mCount1LiveData) {
            mCountMediatorLiveData.value = it
        }

        mCountMediatorLiveData.observe(this, {
            mCountTv?.text = it.toString()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mEt?.removeTextChangedListener(mTextWatcher)
    }
}