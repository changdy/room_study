package com.example.word


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 */
class WordsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_words, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val wordViewModel: WordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = MyAdapter(false, wordViewModel)
        wordViewModel.getAllWordsLive().observe(requireActivity(), Observer<List<WordEntity>> {
            val itemCount = adapter.itemCount
            adapter.allWords = it
            // 这里要注意 , 避免数据绑定的时候 陷入循环
            if (itemCount != it.size) {
                adapter.notifyDataSetChanged()
            }
        })
        recyclerView.adapter = adapter
        val floatingActionButton = requireActivity().findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            Navigation
                .findNavController(it)
                .navigate(R.id.action_wordsFragment_to_addWordsFragment)
        }
    }
}