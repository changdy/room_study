package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WordDatabase.initInstance(this)
        val insertButton: Button = findViewById(R.id.button_insert)
        val updateButton: Button = findViewById(R.id.button_update)
        val deleteButton: Button = findViewById(R.id.button_delete)
        val clearButton: Button = findViewById(R.id.button_clear)
        val textView: TextView = findViewById(R.id.textView2)
        val wordViewModel: WordViewModel =
            ViewModelProviders.of(this).get(WordViewModel::class.java)
        // 有泛型 不能隐藏
        wordViewModel.getAllWordsLive()
            .observe(this, Observer<List<WordEntity>> {
                val text = StringBuilder()
                for (word in it) {
                    text.append(word.id).append(":").append(word.word).append("=")
                        .append(word.chineseMeaning).append("\n")
                }
                textView.text = text.toString()
            })
        // 没泛型可以隐藏   insertButton.setOnClickListener(View.OnClickListener {   })
        insertButton.setOnClickListener {
            wordViewModel.insertWords(
                WordEntity(word = "Hello", chineseMeaning = "你好！"),
                WordEntity(word = "World", chineseMeaning = "世界！")
            )
        }
        clearButton.setOnClickListener { wordViewModel.deleteAllWords() }
        updateButton.setOnClickListener { wordViewModel.updateWords(WordEntity(90, "hi", "你好啊")) }
        deleteButton.setOnClickListener { wordViewModel.deleteWords(WordEntity(90)) }
    }
}
