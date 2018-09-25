package com.gpetuhov.android.samplepagingroom.room

import android.content.Context
import javax.inject.Singleton
import dagger.Provides
import androidx.room.Room
import com.gpetuhov.android.samplepagingroom.SamplePagingRoomApp
import dagger.Module


@Module
class UserDatabaseModule {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return SamplePagingRoomApp.application.applicationContext
    }

    @Provides
    @Singleton
    fun provideQuakeDatabase(context: Context): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "user_database").build()
    }

    @Provides
    @Singleton
    fun provideQuakeDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }
}