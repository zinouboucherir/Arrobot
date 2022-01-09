package com.example.projet_mobile

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Plante::class,Frequence::class), version = 6)
@TypeConverters(TypeConverter::class)
abstract  class PlanteDB: RoomDatabase() {
    abstract fun myDao():Dao
    companion object {
        @Volatile
        private var instance: PlanteDB? = null
        fun getDatabase(context: Context): PlanteDB {
            if (instance != null)
                return instance!!
            val db = Room.databaseBuilder(
                context.applicationContext,
                PlanteDB::class.java, "plantes"
            ).build()
            instance = db
            return instance!!
        }
    }
}