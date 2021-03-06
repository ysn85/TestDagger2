package com.annotation

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annotation.FeedFlowProperty.Companion.FEED_FLOW_ITEM_MAX_WIDTH_VALUE
import com.auto.service.BaseDataService
import com.example.myapplication.R
import com.live.demo.TestLiveDataActivity
import com.live.mediator.demo.TestMediatorActivity
import com.sk.dagger.bts_annotation.FuncTimeCost
import com.sk.dagger.bts_annotation.TestDe
import com.sk.dagger.bts_annotation.TestKDe
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.math.roundToInt

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

    @FeedFlowProperty(value = FEED_FLOW_ITEM_MAX_WIDTH_VALUE)
    private var mItemWidth: Int = 0

    @TestKDe
    @BtsBindView(R.id.annotation_post_view)
    private var mPostView: View? = null

    private var mCountDownLatch: CountDownLatch = CountDownLatch(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation)
        BtsBindHelper.inject(this)
        BtsBindHelper.bind(this/*, (findViewById<ViewGroup>(android.R.id.content)).getChildAt(0)*/)

        Log.i(TAG, "mAnnotationTv content is ${mAnnotationTv?.text}")
        Log.i(TAG, "my name is ${mStudent.name}, age is ${mStudent.age}, mItemWidth = $mItemWidth")

        Log.i("MyLinearLayout", "post call start")
        mPostView?.post {
            Log.i("MyLinearLayout", "post call")
        }

        val layoutManager = GridLayoutManager(
            this,
            SPAN_COUNT,
            GridLayoutManager.HORIZONTAL,
            false
        )
        mAnnotationRv?.layoutManager = layoutManager


        val itemDecoration = NavGridSpacingItemDecoration()
        mAnnotationRv?.addItemDecoration(itemDecoration)
        itemDecoration.setSpacing(
            leftRight = 28, topBottom = 28,
            spanCount = SPAN_COUNT, col = COLUMN, itemCount = ITEM_COUNT
        )
        mAnnotationRv?.adapter = MyAdapter()

        run outSide@{
            mListUIs?.forEachIndexed { index, view ->
                if (view is TextView) {
                    Log.i(TAG, "list content is ${view.text}, index = $index")
                }
                if (index == 0) {
                    return@outSide
                }
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

//        Thread {
//            Log.i(TAG, "thread await")
//            mCountDownLatch.await()
//            Log.i(TAG, "thread await end")
//        }.start()

        val test = "1234"
        var demo = mutableMapOf<String, String>()
        test?.apply {
            demo[""] = this
            Log.i(TAG, "demo value is = ${demo.size}, demo = $demo")
        }

        mAnnotationTv?.post {
            Log.i(TAG, "getViewHeight value is = ${mAnnotationTv.getViewHeight()}")
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
        const val SPAN_COUNT = 2
        const val COLUMN = 4
        const val ITEM_COUNT = 13
    }


    private fun View?.getViewHeight(): Int {
        return this?.let {
            var realHeight = it.height
            if (realHeight == 0) {
                it.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                realHeight = it.measuredHeight
            }
            realHeight
        } ?: -1
    }

    @FuncTimeCost
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
        mCountDownLatch.countDown()
        when (view.id) {
            R.id.annotation_next_btn ->
                startActivity(Intent(this, TestMediatorActivity::class.java))
            R.id.annotation_live_data_btn ->
                startActivity(Intent(this, TestLiveDataActivity::class.java))
            else -> {
            }
        }
    }

    class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.list_demo_item, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.updateData("$position test item")
        }

        override fun getItemCount(): Int {
            return ITEM_COUNT
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            @BtsBindView(R.id.list_demo_item_tv)
            private var mDemoItemTv: TextView? = null

            @FeedFlowProperty(FEED_FLOW_ITEM_MAX_WIDTH_VALUE)
            private val mItemMaxWith: Int = 0

            init {
                BtsBindHelper.bind(this, itemView)

                Log.d("TestFeedFlow", "mItemMaxWith = $mItemMaxWith")
            }

            fun updateData(value: String) {
                mDemoItemTv?.layoutParams?.width =
//                         StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT
                    ((Resources.getSystem()
                        .displayMetrics.widthPixels - 28 * (COLUMN - 1)).toFloat() / COLUMN).roundToInt()
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