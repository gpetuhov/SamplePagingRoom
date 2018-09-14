package com.gpetuhov.android.samplepagingroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.room.Room
import com.gpetuhov.android.samplepagingroom.models.User
import com.gpetuhov.android.samplepagingroom.room.UserDatabase
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.defaultSharedPreferences


class MainActivity : AppCompatActivity() {

    companion object {
        const val FIRST_RUN = "first_run_flag"
    }

    private lateinit var dataBase: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This should be injected with Dagger, but we init it here for simplicity
        dataBase = Room.databaseBuilder(this, UserDatabase::class.java, "user_database").build()

        // Init database with dummy data on first run
        initDummyData()
    }

    private fun initDummyData() {
        val isFirstRun = defaultSharedPreferences.getBoolean(FIRST_RUN, true)

        if (isFirstRun) {
            defaultSharedPreferences.edit {
                putBoolean(FIRST_RUN, false)
            }

            val dummyData = List(10000) {
                User("$it", "User name $it")
            }

            GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
                dataBase.userDao().insertAll(dummyData)
            })
        }
    }
}
