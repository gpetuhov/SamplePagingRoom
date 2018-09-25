package com.gpetuhov.android.samplepagingroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gpetuhov.android.samplepagingroom.adapter.UserAdapter
import com.gpetuhov.android.samplepagingroom.models.User
import com.gpetuhov.android.samplepagingroom.models.UsersViewModel
import com.gpetuhov.android.samplepagingroom.room.UserDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.defaultSharedPreferences
import javax.inject.Inject
import androidx.recyclerview.widget.LinearLayoutManager




class MainActivity : AppCompatActivity() {

    companion object {
        const val FIRST_RUN = "first_run_flag"
    }

    @Inject
    lateinit var dataBase: UserDatabase

    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SamplePagingRoomApp.appComponent.inject(this)

        // Init database with dummy data on first run
        initDummyData()

        // Init recycler view
        val mLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayoutManager

        // Set adapter for the recycler view
        val adapter = UserAdapter(this)
        recyclerView.adapter = adapter

        // Subscibe to ViewModel changes
        viewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        viewModel.usersLiveData.observe(this, Observer { names ->
            if (names != null) adapter.submitList(names)
        })
    }

    private fun initDummyData() {
        val isFirstRun = defaultSharedPreferences.getBoolean(FIRST_RUN, true)

        // If first run, init database with dummy data
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
