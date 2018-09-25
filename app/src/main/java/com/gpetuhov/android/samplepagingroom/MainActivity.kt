package com.gpetuhov.android.samplepagingroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import com.gpetuhov.android.samplepagingroom.models.User
import com.gpetuhov.android.samplepagingroom.room.UserDatabase
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.defaultSharedPreferences
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    companion object {
        const val FIRST_RUN = "first_run_flag"
    }

    @Inject
    lateinit var dataBase: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SamplePagingRoomApp.appComponent.inject(this)

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
