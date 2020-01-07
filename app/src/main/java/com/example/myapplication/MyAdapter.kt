package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Changdy on 2020/1/5.
 */
class MyAdapter(
    private var userCardView: Boolean,
    private var wordViewModel: WordViewModel
) :
    RecyclerView.Adapter<MyViewHolder>() {
    var allWords: List<WordEntity> = mutableListOf()

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflate = layoutInflater.inflate(
            if (userCardView) R.layout.cell_card else R.layout.cell_normal,
            parent,
            false
        )
        return MyViewHolder(inflate)
    }

    override fun getItemCount() = allWords.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val wordEntity = allWords[position]
        holder.invisibleSwitch.setOnCheckedChangeListener(null)
        if (wordEntity.chineseInvisible!!) {
            holder.textViewChinese.visibility = View.INVISIBLE
            holder.invisibleSwitch.isChecked = true
        } else {
            holder.textViewChinese.visibility = View.VISIBLE
            holder.invisibleSwitch.isChecked = false
        }
        holder.textViewNumber.text = wordEntity.id.toString()
        holder.textViewChinese.text = wordEntity.chineseMeaning
        holder.textViewEnglish.text = wordEntity.word
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse("https://m.youdao.com/dict?le=eng&q=${wordEntity.word}")
            }
            holder.itemView.context.startActivities(arrayOf(intent))
        }
        holder.invisibleSwitch.setOnCheckedChangeListener { _, y ->
            if (y) {
                holder.textViewChinese.visibility = View.INVISIBLE
                wordEntity.chineseInvisible = true
            } else {
                holder.textViewChinese.visibility = View.VISIBLE
                wordEntity.chineseInvisible = false
            }
            wordViewModel.updateWords(wordEntity)
        }
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textViewNumber: TextView = itemView.findViewById(R.id.textViewNumber)
    var textViewEnglish: TextView = itemView.findViewById(R.id.textViewEnglish)
    var textViewChinese: TextView = itemView.findViewById(R.id.textViewChinese)
    var invisibleSwitch: Switch = itemView.findViewById(R.id.invisible_switch)
}