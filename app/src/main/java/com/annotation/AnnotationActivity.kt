package com.annotation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

/**
 * @author Yang
 * @since 2021-08-25
 */
class AnnotationActivity : AppCompatActivity() {
    @PersonAnnotation(name = "小明", age = 15)
    private lateinit var mStudent: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation)
        findViewById<View>(R.id.annotation_btn).setOnClickListener {
            AnnotationHelper.inject(this)
            Log.i(TAG, "my name is ${mStudent.name}, age is ${mStudent.age}")
        }
    }

    companion object {
        const val TAG = "AnnotationActivity"
    }
}