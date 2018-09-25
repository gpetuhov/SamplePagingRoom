package com.gpetuhov.android.samplepagingroom

import android.app.Application
import com.gpetuhov.android.samplepagingroom.dagger.AppComponent
import com.gpetuhov.android.samplepagingroom.dagger.DaggerAppComponent

// Application class keeps Dagger component
class SamplePagingRoomApp : Application() {

    companion object {
        lateinit var application: SamplePagingRoomApp
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        appComponent = DaggerAppComponent.builder().build()
    }
}