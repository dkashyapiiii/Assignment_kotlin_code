package com.diksha.employeedata.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.diksha.employeedata.ModelClass.RoomModel

@Dao
interface RoomDao {
    @get:Query("SELECT * FROM roommodel")
    val allUsers: List<RoomModel?>?

    @Insert
    fun insertUser(vararg users: RoomModel?)

    @Delete
    fun delete(user: RoomModel?)
}