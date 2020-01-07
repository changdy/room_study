package com.example.word

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private var wordRepository: WordRepository = WordRepository()

    fun getAllWordsLive() = wordRepository.allWordsLive
    fun insertWords(vararg words: WordEntity) = wordRepository.insertWords(*words)
    fun updateWords(vararg words: WordEntity) = wordRepository.updateWords(*words)
    fun deleteWords(vararg words: WordEntity) = wordRepository.deleteWords(*words)
    fun deleteAllWords() = wordRepository.deleteAllWords()
}