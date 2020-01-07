package com.example.word

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Changdy on 2019/12/28.
 */
@Entity(tableName = "word")
data class WordEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "english_word") var word: String? = null,
    @ColumnInfo(name = "chinese_meaning") var chineseMeaning: String? = null,
    @ColumnInfo(name = "chinese_invisible") var chineseInvisible: Boolean? = null
)