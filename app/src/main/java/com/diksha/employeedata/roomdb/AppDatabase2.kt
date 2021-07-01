package com.diksha.employeedata.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.diksha.employeedata.ModelClass.RoomModel

@Database(entities = [RoomModel::class], version = 1)
abstract class AppDatabase2 : RoomDatabase() {
    abstract fun userDao(): RoomDao?

    companion object {
        private var INSTANCE: AppDatabase2? = null
        fun getDbInstance(context: Context): AppDatabase2? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase2::class.java,
                    "ROOM_DB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}