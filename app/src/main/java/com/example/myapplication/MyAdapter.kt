package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Changdy on 2020/1/5.
 */
class MyAdapter(private var userCardView: Boolean) : RecyclerView.Adapter<MyViewHolder>() {
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
        holder.textViewNumber.text = wordEntity.id.toString()
        holder.textViewChinese.text = wordEntity.chineseMeaning
        holder.textViewEnglish.text = wordEntity.word
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse("https://m.youdao.com/dict?le=eng&q=${wordEntity.word}")
            }
            holder.itemView.context.startActivities(arrayOf(intent))
        }
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textViewNumber: TextView = itemView.findViewById(R.id.textViewNumber)
    var textViewEnglish: TextView = itemView.findViewById(R.id.textViewEnglish)
    var textViewChinese: TextView = itemView.findViewById(R.id.textViewChinese)
}