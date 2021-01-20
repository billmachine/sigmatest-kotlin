package com.example.sigmatest.di

import com.example.sigmatest.R
import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.sigmatest.data.protocol.PostDao
import com.example.sigmatest.data.entity.PostEntity

@Database(entities = arrayOf(
    PostEntity::class

), version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        val roomcallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(app: Application): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(app).also { INSTANCE = it }
        }

        private fun buildDatabase(app: Application) =

            Room.databaseBuilder(app,
                AppDatabase::class.java,
                "${app.applicationContext.getString(R.string.app_name)}.db")
                .allowMainThreadQueries()
//            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
//            .fallbackToDestructiveMigration()
                .addCallback(roomcallback)

                .build()

    }

}