package com.gpetuhov.android.samplepagingroom.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gpetuhov.android.samplepagingroom.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<User>>

    // This is the query, that returns data for the Paging library
    @Query("SELECT * FROM users")
    fun getAllPaged(): DataSource.Factory<Int, User>

    @Insert
    fun insertAll(users: List<User>)

    @Delete
    fun delete(user: User)
}