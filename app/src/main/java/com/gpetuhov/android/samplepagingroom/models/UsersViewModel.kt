package com.gpetuhov.android.samplepagingroom.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gpetuhov.android.samplepagingroom.SamplePagingRoomApp
import com.gpetuhov.android.samplepagingroom.room.UserDatabase
import kotlinx.coroutines.*
import javax.inject.Inject

// ViewModel provides paged list for the MainActivity
class UsersViewModel constructor(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "Boundary callback"
    }

    @Inject
    lateinit var dataBase: UserDatabase

    var usersLiveData: LiveData<PagedList<User>>

    init {
        SamplePagingRoomApp.appComponent.inject(this)

        val factory: DataSource.Factory<Int, User> = dataBase.userDao().getAllPaged()

        val pagedListBuilder = LivePagedListBuilder(factory, 50)
                .setBoundaryCallback(object : PagedList.BoundaryCallback<User>() {
                    // This method is called, when DataSource is empty
                    override fun onZeroItemsLoaded() {
                        super.onZeroItemsLoaded()
                        Log.d(TAG, "Empty data source!")
                        insertDummyData(0)
                    }

                    // This method is called, when the DataSource reaches the end
                    // (all data from the database has been displayed in the UI).
                    // Here we should trigger loading next portion of data from the network,
                    // and saving it into the database.
                    // So, network data is paged into the database, and database is paged into UI.
                    override fun onItemAtEndLoaded(itemAtEnd: User) {
                        super.onItemAtEndLoaded(itemAtEnd)
                        Log.d(TAG, "End of data!")

                        // itemAtEnd can be used as the starting point for the next portion of data
                        insertDummyData(itemAtEnd.id)
                    }
                })

        usersLiveData = pagedListBuilder.build()
    }

    // Instead of filling the database with dummy data, network request should be triggered here
    private fun insertDummyData(startNumber: Int) {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
            delay(3000)

            val dummyData = List(500) {
                User("User name ${startNumber + it}")
            }

            dataBase.userDao().insertAll(dummyData)
        })
    }
}
