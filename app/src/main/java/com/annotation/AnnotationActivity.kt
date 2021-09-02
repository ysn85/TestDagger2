package com.annotation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    @BtsBindView(R.id.annotation_btn1)
    private var mAnnotationTv: TextView? = null

    @BtsBindView(R.id.annotation_rv)
    private var mAnnotationRv: RecyclerView? = null

    @BtsBindViews([R.id.annotation_array_btn, R.id.annotation_array_btn1])
    val mListUIs: ArrayList<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation)
        AnnotationHelper.inject(this)
        AnnotationHelper.bind(this, (findViewById<ViewGroup>(android.R.id.content)).getChildAt(0))

        Log.i(TAG, "mAnnotationTv content is ${mAnnotationTv?.text}")
        Log.i(TAG, "my name is ${mStudent.name}, age is ${mStudent.age}")
        Log.i(TAG, "my name is ${mStudentJ.name}, age is ${mStudentJ.age}")

        mAnnotationRv?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAnnotationRv?.adapter = MyAdapter()

        mListUIs?.forEach {
            if (it is TextView) {
                Log.i(TAG, "list content is ${it.text}")
            }
        }
    }

    companion object {
        const val TAG = "AnnotationActivity"
    }

    @BtsOnClick([R.id.annotation_btn, R.id.annotation_btn1])
    private fun onBtnClick(view: View) {
        if (view is TextView) {
            Log.i(TAG, "tv content is ${view.text}")
        } else {
            Log.i(TAG, "onBtnClick is click!")
        }
    }

    class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_demo_item, null)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.updateData("$position test item")
        }

        override fun getItemCount(): Int {
            return 100
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            @BtsBindView(R.id.list_demo_item_tv)
            private var mDemoItemTv: TextView? = null

            init {
                AnnotationHelper.bind(this, itemView)
            }

            fun updateData(value: String) {
                mDemoItemTv?.text = value
            }

            @BtsOnClick([R.id.list_demo_item_tv])
            private fun onItemClick(view: View) {
                if (view is TextView) {
                    Toast.makeText(
                        view.context, "onItemClick item ${view.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}