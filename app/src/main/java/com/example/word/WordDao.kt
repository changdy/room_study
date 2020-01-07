package com.example.word

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {
    @Insert
    fun insertWords(vararg words: WordEntity)

    @Update
    fun updateWords(vararg words: WordEntity)

    @Delete
    fun deleteWords(vararg words: WordEntity)

    @Query("DELETE FROM WORD")
    fun deleteAllWords()

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    fun getAllWordsLive(): LiveData<List<WordEntity>>
}