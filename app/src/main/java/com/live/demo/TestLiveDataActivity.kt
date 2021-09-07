package com.live.demo

import android.os.Bundle
import android.util.Log
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
        TestViewModel.LiveDataVMFactory()
    }

//    private var mVM: TestViewModel? = null

    @BtsBindView(R.id.vm_notify_btn)
    private var mNotifyBtn: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mVM = ViewModelProvider(this)
//            .get(TestViewModel::class.java)
        setContentView(R.layout.activity_vm)
        BtsBindHelper.bind(this)
//        val btsStringBuilder = BtsStringBuilder.of()
//        btsStringBuilder.append("test")
//        Log.i(TestViewModel.TAG, "onCreate $btsStringBuilder")
//        val btsStringBuilder1 = BtsStringBuilder.of()
//        btsStringBuilder1.append("test1")
//        Log.i(TestViewModel.TAG, "onCreate $btsStringBuilder1")

        mVM?.getData()?.observe(this, { value ->
            mNotifyBtn?.text = value
        })
    }

    @BtsOnClick([R.id.vm_notify_btn, R.id.vm_finish_btn])
    private fun onBtnClick(view: View) {
        when (view.id) {
            R.id.vm_notify_btn ->
                if (view is TextView) {
                    mVM?.fetchData()
                }
            R.id.vm_finish_btn ->
                finish()
        }
    }

    override fun onDestroy() {
        Log.i(TestViewModel.TAG, "onDestroy start")
        super.onDestroy()
        Log.i(TestViewModel.TAG, "onDestroy end")
    }
}