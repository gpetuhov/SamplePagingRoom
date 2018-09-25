package com.gpetuhov.android.samplepagingroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gpetuhov.android.samplepagingroom.adapter.UserAdapter
import com.gpetuhov.android.samplepagingroom.models.UsersViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager


// Data flow:
// Network (in this example - generated dummy data) ->
// Room database ->
// DataSource ->
// LiveData<PagedList<>> ->
// ViewModel ->
// PagedList (UI observes changes in ViewModel, so if data in the database changes, new PagedList is provided) ->
// PagedListAdapter ->
// RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SamplePagingRoomApp.appComponent.inject(this)

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
}
