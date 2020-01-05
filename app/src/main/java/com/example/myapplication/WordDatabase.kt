package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {


    abstract fun getWordDao(): WordDao

    companion object {
        lateinit var instance: WordDatabase
        @JvmStatic
        fun initInstance(context: Context) {
            instance = Room
                .databaseBuilder(context, WordDatabase::class.java, "word_database")
                .build()
        }
    }
}


//    companion object {
//        val instance = Single.sin
//    }
//
//    private object Single {
//
//        val sin: WordDatabase = Room.databaseBuilder(
//            MyApplication.instance(),
//            WordDatabase::class.java,
//            "word_database"
//        )
//            .allowMainThreadQueries()
//            .build()
//    }
//
//}