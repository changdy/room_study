package com.example.myapplication

import android.os.AsyncTask
import androidx.lifecycle.LiveData


class WordRepository {
    val allWordsLive: LiveData<List<WordEntity>>
    private val wordDao: WordDao = WordDatabase.instance.getWordDao()

    init {
        allWordsLive = wordDao.getAllWordsLive()
    }

    fun insertWords(vararg wordEntities: WordEntity) =
        InsertAsyncTask(wordDao).execute(*wordEntities)

    fun updateWords(vararg wordEntities: WordEntity) =
        UpdateAsyncTask(wordDao).execute(*wordEntities)


    fun deleteWords(vararg wordEntities: WordEntity) =
        DeleteAsyncTask(wordDao).execute(*wordEntities)


    fun deleteAllWords() = DeleteAllAsyncTask(wordDao).execute()


    class InsertAsyncTask(private val wordDao: WordDao) : AsyncTask<WordEntity, Void?, Void?>() {
        override fun doInBackground(vararg params: WordEntity): Void? {
            wordDao.insertWords(*params)
            return null
        }
    }

    class UpdateAsyncTask(private val wordDao: WordDao) : AsyncTask<WordEntity, Void?, Void?>() {
        override fun doInBackground(vararg params: WordEntity): Void? {
            wordDao.updateWords(*params)
            return null
        }

    }

    private class DeleteAsyncTask(private val wordDao: WordDao) :
        AsyncTask<WordEntity, Void?, Void?>() {
        override fun doInBackground(vararg wordEntities: WordEntity): Void? {
            wordDao.deleteWords(*wordEntities)
            return null
        }

    }

    private class DeleteAllAsyncTask(private val wordDao: WordDao) :
        AsyncTask<Void, Void?, Void?>() {
        override fun doInBackground(vararg voids: Void): Void? {
            wordDao.deleteAllWords()
            return null
        }

    }
}