package com.live.demo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.annotation.BtsBindHelper
import com.annotation.BtsBindView
import com.annotation.BtsOnClick
import com.example.myapplication.R

class TestLiveDataActivity : AppCompatActivity() {

    private val mVM: TestViewModel by viewModels {
        TestViewModel.LiveDataVMFactory
    }

    @BtsBindView(R.id.vm_notify_btn)
    private var mNotifyBtn: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mVM = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
//            .get(TestViewModel::class.java)

        setContentView(R.layout.activity_vm)
        BtsBindHelper.bind(this)
        mVM?.getData()?.observe(this, { value ->
            mNotifyBtn?.text = value
        })
    }

    @BtsOnClick([R.id.vm_notify_btn])
    private fun onBtnClick(view: View) {
        if (view is TextView) {
            mVM?.fetchData()
        }
    }
}