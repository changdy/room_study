package com.example.word

import android.os.AsyncTask


class WordRepository {
    private val wordDao: WordDao = WordDatabase.instance.getWordDao()

    fun insertWords(vararg wordEntities: WordEntity) = InsertAsyncTask(wordDao).execute(*wordEntities)

    fun updateWords(vararg wordEntities: WordEntity) = UpdateAsyncTask(wordDao).execute(*wordEntities)

    fun deleteWords(vararg wordEntities: WordEntity) = DeleteAsyncTask(wordDao).execute(*wordEntities)


    fun getWords(pattern: String?) = if (pattern.isNullOrBlank()) wordDao.getAllWordsLive() else wordDao.getWordsLiveWithPattern("%$pattern%")

    fun deleteAllWords() = DeleteAllAsyncTask(wordDao).execute()

//    fun getWordsLiveWithPattern(pattern: String?, adapter: MyAdapter) = ChangeAsyncTask(wordDao, adapter).execute(pattern)

//    class ChangeAsyncTask(private val wordDao: WordDao, private val adapter: MyAdapter) : AsyncTask<String?, Void?, List<WordEntity>>() {
//        override fun doInBackground(vararg params: String?): List<WordEntity> {
//            return if (params[0].isNullOrBlank()) wordDao.getAllWordsLive() else wordDao.getWordsLiveWithPattern(params[0]!!)
//        }
//
//        override fun onPostExecute(result: List<WordEntity>) {
//            adapter.allWords = result
//        }
//    }


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