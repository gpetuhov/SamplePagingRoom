package com.gpetuhov.android.samplepagingroom.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gpetuhov.android.samplepagingroom.models.User

@Database(entities = [User::class], exportSchema = false, version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}