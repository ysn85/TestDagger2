package com.live.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.annotation.BtsBindHelper
import com.annotation.BtsBindView
import com.annotation.BtsOnClick
import com.example.myapplication.R

class TestLiveDataActivity : AppCompatActivity() {

    private val mVM: TestViewModel by viewModels {
        TestViewModel.LiveDataVMFactory(application)
    }

//    private var mVM: TestViewModel? = null

    @BtsBindView(R.id.vm_notify_net_btn)
    private var mNotifyBtn: TextView? = null

    @BtsBindView(R.id.vm_net_view)
    private var mWebView: WebView? = null

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

        mVM.getData().observe(this, { value ->
            mNotifyBtn?.text = value

            if (value.contains("<HTML>", true)) {
                mWebView?.loadData(
                    HtmlCompat.fromHtml(
                        value,
                        HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
                    ).toString(), "text/html", "UTF-8"
                )
            }
        })
    }

    @BtsOnClick([R.id.vm_notify_net_btn, R.id.vm_finish_btn, R.id.vm_notify_local_btn])
    private fun onBtnClick(view: View) {
        when (view.id) {
            R.id.vm_notify_net_btn ->
                if (view is TextView) {
                    mVM.fetchNetData()
                }
            R.id.vm_notify_local_btn -> {
                mVM.fetchLocalData()
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