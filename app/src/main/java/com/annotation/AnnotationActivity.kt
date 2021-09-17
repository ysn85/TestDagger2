package com.annotation

import android.content.Intent
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
import com.auto.service.BaseDataService
import com.example.myapplication.R
import com.live.demo.TestLiveDataActivity
import com.sk.dagger.bts_annotation.TestDe
import com.sk.dagger.bts_annotation.TestKDe
import java.util.ServiceLoader

/**
 * @author Lee
 * @since 2021-08-25
 */
class AnnotationActivity : AppCompatActivity() {
    @TestDe
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
    val mListUIs: MutableList<View>? = null

    @TestKDe
    @BtsBindView(R.id.annotation_post_view)
    private var mPostView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation)
        BtsBindHelper.inject(this)
        BtsBindHelper.bind(this/*, (findViewById<ViewGroup>(android.R.id.content)).getChildAt(0)*/)

        Log.i(TAG, "mAnnotationTv content is ${mAnnotationTv?.text}")
        Log.i(TAG, "my name is ${mStudent.name}, age is ${mStudent.age}")
        Log.i(TAG, "my name is ${mStudentJ.name}, age is ${mStudentJ.age}")

        Log.i("MyLinearLayout", "post call start")
        mPostView?.post {
            Log.i("MyLinearLayout", "post call")
        }

        mAnnotationRv?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAnnotationRv?.adapter = MyAdapter()

        mListUIs?.forEach {
            if (it is TextView) {
                Log.i(TAG, "list content is ${it.text}")
            }
        }
        val hashSet = hashSetOf<TestLateInit>()
        val test1 = TestLateInit()
        test1.age = 10
        test1.height = 10

        val test2 = TestLateInit()
        test2.age = 100
        test2.height = 100

        hashSet.add(test1)
        hashSet.add(test2)
        Log.i(TAG, "hashSet start size ${hashSet.size}")
//        test1.age = 8
        hashSet.remove(test1)
        Log.i(TAG, "hashSet end size ${hashSet.size}")

        val serviceLoader = ServiceLoader.load(BaseDataService::class.java)
        serviceLoader.forEach {
            Log.i(TAG, "testServiceLoader ${it.data}")
        }
    }

//    @FuncTimeCost
    private fun testTimer() {
        Log.i(TAG, "call testTimer start")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.i(TAG, "mPostView width = ${mPostView?.width}, hasFocus = $hasFocus")
    }

    companion object {
        const val TAG = "AnnotationActivity"
    }

    @BtsOnClick(
        [
            R.id.annotation_btn,
            R.id.annotation_btn1,
            R.id.annotation_next_btn,
            R.id.annotation_live_data_btn]
    )
    private fun onBtnClick(view: View) {
        if (view is TextView) {
            Log.i(TAG, "tv content is ${view.text}")
        } else {
            Log.i(TAG, "onBtnClick is click!")
        }
        testTimer()
        when (view.id) {
            R.id.annotation_next_btn ->
                startActivity(Intent(this, AnnotationActivityJ::class.java))
            R.id.annotation_live_data_btn ->
                startActivity(Intent(this, TestLiveDataActivity::class.java))
            else -> {
            }
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
                BtsBindHelper.bind(this, itemView)
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

    class TestLateInit() {
        var age: Int = 0
        var height: Int = 0

        override fun hashCode(): Int {
            val magic = 31
            var result = 1
            result = magic * result + age
            result = magic * result + height
            return result
        }
    }
}