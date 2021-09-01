package com.annotation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

/**
 * @author Lee
 * @since 2021-08-25
 */
class AnnotationActivity : AppCompatActivity() {
    @TestAnnotationJ
    @PersonAnnotation(name = "小明", age = 15)
    private lateinit var mStudent: Student

    @TestAnnotationJ
    @PersonAnnotation(name = "小丽", age = 16)
    private lateinit var mStudentJ: StudentJ

    @TestBindView(R.id.annotation_tv)
    var mAnnotationTv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation)
        AnnotationHelper.inject(this)
        AnnotationHelper.bind(this)
        Log.i(TAG, "mAnnotationTv content is ${mAnnotationTv?.text}")
    }

    companion object {
        const val TAG = "AnnotationActivity"
    }

    @TestOnClick(R.id.annotation_tv)
    private fun onTxtClick(view: View) {
        if (view is TextView) {
            Log.i(TAG, "onTxtClick is click! ${view.text}")
        } else {
            Log.i(TAG, "onTxtClick is click!")
        }
    }

    @TestOnClick(R.id.annotation_btn)
    private fun onBtnClick(view: View) {
        Log.i(TAG, "my name is ${mStudent.name}, age is ${mStudent.age}")
        Log.i(TAG, "my name is ${mStudentJ.name}, age is ${mStudentJ.age}")
        if (view is TextView) {
            Log.i(TAG, "tv content is ${view.text}")
        } else {
            Log.i(TAG, "onBtnClick is click!")
        }
    }
}