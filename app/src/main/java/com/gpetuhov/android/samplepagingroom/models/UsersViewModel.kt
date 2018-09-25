package com.gpetuhov.android.samplepagingroom.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gpetuhov.android.samplepagingroom.SamplePagingRoomApp
import com.gpetuhov.android.samplepagingroom.room.UserDatabase
import javax.inject.Inject

class UsersViewModel constructor(application: Application)
    : AndroidViewModel(application) {

    @Inject
    lateinit var dataBase: UserDatabase

    var usersLiveData: LiveData<PagedList<User>>

    init {
        SamplePagingRoomApp.appComponent.inject(this)

        val factory: DataSource.Factory<Int, User> = dataBase.userDao().getAllPaged()

        val pagedListBuilder: LivePagedListBuilder<Int, User> =
                LivePagedListBuilder<Int, User>(factory, 50)

        usersLiveData = pagedListBuilder.build()
    }
}
