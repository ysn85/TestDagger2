package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annotation.BtsBindHelper
import com.annotation.PersonAnnotation
import com.annotation.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    companion object {
        const val TAG = "MainActivity"
    }

    @PersonAnnotation(name = "小明", age = 15)
    private lateinit var student: Student

    var counter: Int = 0
    val name: String by lazy { "super name" }
    var str: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spr_list_drv_content_layout)

        val scrollView = findViewById<ScrollView>(R.id.spr_list_scrollView)
        OverScrollDecoratorHelper.setUpOverScroll(scrollView)

        testDefaultArg(1, true)
        testDefaultArg()

        testBlockFun { var1, var2 ->
            Log.i(TAG, "var1 {$var1}, var2 {$var2}")
        }

//        setSupportActionBar(findViewById(R.id.toolbar))
//
//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
//        val atomicValue = AtomicLong()
//        for (i in 1..1_000_000) {
        val deferred = GlobalScope.async {
            while (true) {
                delay(3000)
                doWork(counter++)
            }
        }
//        }
        val goOpenTextView: TextView? = findViewById(R.id.tv_go_open2)

        val letTextView = goOpenTextView?.let {
            it.text = "123"
            testAlso()
            testAlso1()
        }
        val alsoTextView = goOpenTextView?.also {
            it.text = "123"
        }
        val applyTextView = goOpenTextView?.apply {
            text = "123"
        }
        Log.i(
            TAG, "letTextView = {$letTextView}, alsoTextView = {$alsoTextView}, " +
                    "applyTextView = {$applyTextView}"
        )


        findViewById<View>(R.id.spr_list_drv_content_modify_the_itinerary_textView)
            .setOnClickListener {
//                launchFromMainScope(true)
//                if (name.length > 2) {
//                    Log.i(TAG, "$name")
//                } else {
//
//                }
                BtsBindHelper.inject(this)
                Log.i(TAG, "student is {${student.name}}, {${student.age}}")

//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                    goOpenTextView?.setTextAppearance(R.style.sprPsgNotificationGoOpenUIStyle)
//                }
//                val context = it.context
//                val intent = Intent(context, TestRulerActivity::class.java)
////                if (context !is Activity) {
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////                }
//                context.startActivity(intent)
            }
        findViewById<View>(R.id.spr_list_drv_content_switch_routes_textView)
            .setOnClickListener {
//                launchFromMainScope(false)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    goOpenTextView?.setTextAppearance(R.style.sprPsgNotificationSwitchUIStyle)
                }
            }

        var recyclerView: RecyclerView = findViewById(R.id.spr_list_recyclerView)

        recyclerView.layoutManager = GridLayoutManager(
            this,
            1,
            GridLayoutManager.VERTICAL,
            false
        )
        var titles = arrayOf("修改行程", "切换路线", "收藏路线", "取消行程")
        var icons = arrayOf(
            R.drawable.modify_the_itinerary_icon,
            R.drawable.switch_routes_icon,
            R.drawable.favorite_routes_icon,
            R.drawable.cancel_the_trip_icon
        )
        var datas: ArrayList<SprMoreOptionsData> = ArrayList()
        for (i in titles.indices) {
            val sprMoreOptionsData: SprMoreOptionsData = SprMoreOptionsData()
            sprMoreOptionsData.iconRes = icons[i]
            sprMoreOptionsData.title = titles[i]
            datas.add(sprMoreOptionsData)
        }
        val sprMoreOptionsAdapter: SprMoreOptionsAdapter = SprMoreOptionsAdapter()
        recyclerView.adapter = sprMoreOptionsAdapter
        sprMoreOptionsAdapter.updateData(datas)
    }

    private fun testAlso(): Int {
        return 10
    }

    private fun testAlso1() {
    }

    private fun testDefaultArg(args1: Int = 0, args2: Boolean = false) {
        Log.i(TAG, "args1 {$args1}, args2 {$args2}")
    }

    private fun testBlockFun(block: (var1: Int, var2: Boolean) -> Unit) {
        block.invoke(10, false)
    }

    var def: Deferred<Any>? = null
    private fun launchFromMainScope(start: Boolean) {
        if (start) {
            launch {
                def = async(
                    start = CoroutineStart.LAZY,
                    context = Dispatchers.IO
                ) {
                    doCounter()
                }
                def?.start()
            }
        } else {
            def?.cancel()
        }
    }

    private suspend fun doCounter() {
        // network request
        Log.i(TAG, "launchFromMainScope start")
        while (true) {
            delay(1000)
            Log.i(TAG, "launchFromMainScope running")
        }
        Log.i(TAG, "launchFromMainScope stop")
    }

    private class SprMoreOptionsData {
        var title: String? = null
        var icon: String? = null
        var iconRes: Int = R.drawable.cancel_the_trip_icon
    }

    private class SprMoreOptionsAdapter : RecyclerView.Adapter<SprMoreOptionsViewHolder>() {
        var datas: ArrayList<SprMoreOptionsData>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SprMoreOptionsViewHolder {
            return SprMoreOptionsViewHolder(
                View.inflate(
                    parent.context,
                    R.layout.spr_more_action_item,
                    null
                )
            )
        }

        override fun getItemCount(): Int {
            var size: Int = datas?.size ?: 0
            return size
        }

        override fun onBindViewHolder(holder: SprMoreOptionsViewHolder, position: Int) {
            datas?.get(position)?.let { holder.updateData(it) }
        }

        fun updateData(data: ArrayList<SprMoreOptionsData>?) {
            val addAll = data?.let { datas?.addAll(it) }
        }
    }

    private class SprMoreOptionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null
        var imageView: ImageView? = null

        init {
            textView = itemView.findViewById(R.id.spr_more_action_item_textView)
            imageView = itemView.findViewById(R.id.spr_more_action_item_imageView)
        }

        fun updateData(data: SprMoreOptionsData) {
            textView?.text = data.title
            imageView?.setImageResource(data.iconRes)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    suspend fun doWork(value: Int): Int {
        Log.i(TAG, "doWork value is $value")
        delay(3000)
        return value
    }

    class Main private constructor() {
        companion object {
            @JvmStatic
            fun getInstances(): Main {
                return Main()
            }
        }

        fun main(args: Array<String>) {
            val main = Main.getInstances()
        }
    }

    inline fun testInline(func: (age: Int) -> Unit, /*noinline*/ funcNo: (name: String) -> Unit) {
        func(10)
        funcNo("test")
    }
}