package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WordDatabase.initInstance(this)
        val wordViewModel: WordViewModel =
            ViewModelProviders.of(this).get(WordViewModel::class.java)
        val cardAdapter = MyAdapter(true, wordViewModel)
        val normalAdapter = MyAdapter(false, wordViewModel)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = normalAdapter
        val insertButton: Button = findViewById(R.id.button_insert)
        val clearButton: Button = findViewById(R.id.button_clear)
        val cardSwitch = findViewById<Switch>(R.id.card_switch)
            .also {
                it.setOnCheckedChangeListener { _, y ->
                    val adapter = if (y) cardAdapter else normalAdapter
                    adapter.allWords = wordViewModel.getAllWordsLive().value!!
                    recyclerView.adapter = adapter
                }
            }
        // 有泛型 不能隐藏
        wordViewModel.getAllWordsLive()
            .observe(this, Observer<List<WordEntity>> {
                val adapter = if (cardSwitch.isChecked) cardAdapter else normalAdapter
                val itemCount = adapter.itemCount
                adapter.allWords = it
                // 这里要注意 , 避免数据绑定的时候 陷入循环
                if (itemCount != it.size) {
                    adapter.notifyDataSetChanged()
                }
            })
        val map = mapOf(
            "Hello" to "你好",
            "World" to "世界",
            "Android" to "安卓系统",
            "Google" to "谷歌公司",
            "Studio" to "工作室",
            "Project" to "项目",
            "Database" to "数据库",
            "Recycler" to "回收站",
            "View" to "视图",
            "String" to "字符串",
            "Value" to "价值",
            "Integer" to "整数类型"
        )
        // 没泛型可以隐藏   insertButton.setOnClickListener(View.OnClickListener {   })
        insertButton.setOnClickListener {
            map.forEach { wordViewModel.insertWords(WordEntity(null, it.key, it.value, true)) }
        }
        clearButton.setOnClickListener { wordViewModel.deleteAllWords() }
    }
}
