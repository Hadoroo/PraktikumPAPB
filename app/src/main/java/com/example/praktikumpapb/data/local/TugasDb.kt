package com.example.praktikumpapb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tugas::class], version = 1)
abstract class TugasDb : RoomDatabase() {
    abstract fun tugasDao(): TugasDAO

    companion object {
        @Volatile
        private var INSTANCE: TugasDb? = null

        @JvmStatic
        fun getDatabase(context: Context): TugasDb {
            if (INSTANCE == null) {
                synchronized(TugasDb::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TugasDb::class.java, "Tugas_database").build()
                }
            }
            return INSTANCE as TugasDb
        }
    }
}