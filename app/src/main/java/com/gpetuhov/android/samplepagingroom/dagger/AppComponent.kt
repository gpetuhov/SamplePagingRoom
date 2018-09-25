package com.gpetuhov.android.samplepagingroom.dagger

import com.gpetuhov.android.samplepagingroom.MainActivity
import com.gpetuhov.android.samplepagingroom.models.UsersViewModel
import com.gpetuhov.android.samplepagingroom.room.UserDatabaseModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    UserDatabaseModule::class
])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(usersViewModel: UsersViewModel)
}
