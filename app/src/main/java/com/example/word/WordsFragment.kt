package com.example.word


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 */
class WordsFragment : Fragment() {

    private val wordRepository = WordRepository()
    private val normalAdapter = MyAdapter(false, wordRepository)
    private val cardAdapter = MyAdapter(true, wordRepository)
    private lateinit var wordEntity: LiveData<List<WordEntity>>
    private lateinit var recyclerView: RecyclerView


    companion object {
        const val VIEW_TYPE_SHP = "view_type_shp"
        const val IS_USING_CARD_VIEW = "is_using_card_view"
    }

    init {
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearData -> {
                AlertDialog.Builder(requireActivity())
                    .setTitle("清空数据")
                    .setPositiveButton("确定") { _, _ -> wordRepository.deleteAllWords() }
                    .setNegativeButton("取消") { _, _ -> }
                    .create()
                    .show()
            }
            R.id.switch_view_type -> {
                val shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE)
                val isUsingCardView = shp.getBoolean(IS_USING_CARD_VIEW, false)
                val editor = shp.edit()
                editor.putBoolean(IS_USING_CARD_VIEW, !isUsingCardView)
                editor.apply()
                changeAdapter()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_words, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        fillAdapter()
        recyclerView.adapter = getCurrentAdapter()
        val floatingActionButton = requireActivity().findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener { Navigation.findNavController(it).navigate(R.id.action_wordsFragment_to_addWordsFragment) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.maxWidth = 600
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true
            override fun onQueryTextChange(newText: String?): Boolean {
                fillAdapter(newText?.trim())
                return true
            }
        })
    }

    private fun fillAdapter(pattern: String? = null) {
        if (this::wordEntity.isInitialized) {
            wordEntity.removeObservers(requireActivity())
        }
        wordEntity = wordRepository.getWords(pattern)
        wordEntity.observe(requireActivity(), Observer<List<WordEntity>> {
            val currentAdapter = getCurrentAdapter()
            val itemCount = currentAdapter.itemCount
            currentAdapter.allWords = it
            if (it.size != itemCount) {
                currentAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun changeAdapter() {
        wordEntity.removeObservers(requireActivity())
        val currentAdapter = getCurrentAdapter()
        wordEntity.observe(requireActivity(), Observer<List<WordEntity>> {
            currentAdapter.allWords = it
            currentAdapter.notifyDataSetChanged()
        })
        recyclerView.adapter = currentAdapter
    }

    private fun getCurrentAdapter(): MyAdapter {
        val shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE)
        return if (shp.getBoolean(IS_USING_CARD_VIEW, false)) cardAdapter else normalAdapter
    }
}