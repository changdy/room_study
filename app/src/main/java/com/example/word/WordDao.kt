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

    // LiveData 对象不能立刻调用 value属性 , 会导致空指针, 猜测和 异步线程的机制有关
    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    fun getAllWordsLive(): LiveData<List<WordEntity>>

    @Query("SELECT * FROM WORD where english_word like :pattern   ORDER BY ID DESC")
    fun getWordsLiveWithPattern(pattern: String): LiveData<List<WordEntity>>
}