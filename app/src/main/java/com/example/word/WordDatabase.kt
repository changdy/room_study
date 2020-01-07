package com.example.word

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [WordEntity::class], version = 2, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {


    abstract fun getWordDao(): WordDao

    companion object {
        lateinit var instance: WordDatabase
        @JvmStatic
        fun initInstance(context: Context) {
            // 需要注意的是 sqlite中没有删除列功能, 需要新建表然后 执行拷贝操作. 字符串直接对应text类型 布尔值对应Integer
            // 另外 迁移的记录不能删,否则会导致 旧版本升级时 , APP启动失败
            val migration = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE word ADD COLUMN chinese_invisible INTEGER NOT NULL DEFAULT 0")
                }
            }
            instance = Room
                .databaseBuilder(context, WordDatabase::class.java, "word_database")
                //.fallbackToDestructiveMigration() // 这个直接删除旧数据 同时生成新的数据库表
                .addMigrations(migration)
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