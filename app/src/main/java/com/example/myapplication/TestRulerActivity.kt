package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ruler.widget.ScrollRulerLayout


class TestRulerActivity : AppCompatActivity() {


    private var mRulerLayout: ScrollRulerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruler_main)
        mRulerLayout = findViewById(R.id.test_ruler_layout)

//        rulerView.setScope(5000, 15001, 500)
//        rulerView.setCurrentItem("10000")

        mRulerLayout?.setScope(20, 260, 10)
        mRulerLayout?.setCurrentItem(12)

        Log.i("TestRulerActivity", "----" + second2HHMMSS(10))
        Log.i("TestRulerActivity", "----" + second2HHMMSS(1000))
        Log.i("TestRulerActivity", "----" + second2HHMMSS(3650))
        Log.i("TestRulerActivity", "----" + second2HHMMSS(100000))
        Log.i("TestRulerActivity", "----" + second2HHMMSS(100000 * 2000))
        Log.i("TestRulerActivity", "----" + second2HHMMSS(86400 * 10 + 90))

        val textView = findViewById<TextView>(R.id.test_span_color_textView)
        val spannableString = SpannableString("为文字设置上标")
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#0099EE")),
            0,
            1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#00FFFF")),
            1,
            2,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#FF0000")),
            2,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
//        spannableString.setSpan(
//            BtsVerticalCenterSpan(),
//            2,
//            spannableString.length,
//            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
//        )
        textView.text = spannableString

        makeFunParams(args4 = "text")
    }

    /**
     * 将大等于60的描述转换为HH:mm:ss格式
     *
     * @param second
     * @return
     */
    private fun second2HHMMSS(second: Int): String? {
        val time = 60
        if (second < 60) {
            return "00:00:" + String.format("%1$02d", second)
        }
        var minInt = second / time
        var secInt = 0
        if (minInt < 60) {

            secInt = second % time
            val mins = String.format("%1$02d", minInt)
            val secd = String.format("%1$02d", secInt)
            return "$mins:$secd"
        } else {
            val hourInt = minInt / time
            val tempSecond = second - hourInt * time * time
            if (tempSecond < 60) {
                return if (hourInt < 100) {
                    String.format("%1$02d", hourInt) + ":00:" + String.format(
                        "%1$02d",
                        tempSecond
                    )
                } else {
                    String.format("%1$03d", hourInt) + ":00:" + String.format(
                        "%1$02d",
                        tempSecond
                    )
                }
            } else {
                minInt = tempSecond / time
                secInt = second % time
                return if (hourInt < 100) {
                    String.format("%1$02d", hourInt) + ":" + String.format(
                        "%1$02d",
                        minInt
                    ) + ":" + String.format(
                        "%1$02d",
                        secInt
                    )
                } else {
                    String.format("%1$03d", hourInt) + ":" + String.format(
                        "%1$02d",
                        minInt
                    ) + ":" + String.format(
                        "%1$02d",
                        secInt
                    )
                }
            }
        }
    }

    private fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
        if (value1 != null && value2 != null) {
            bothNotNull(value1, value2)
        }
    }

    private fun makeFunParams(
        args1: String = "",
        args2: String = "",
        args3: Int = 0,
        args4: String = ""
    ) {
        Log.i("TestRulerActivity", "args1 = $args1, args2 = $args2, args3 = $args3, args4 = $args4")
    }
}