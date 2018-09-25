package com.gpetuhov.android.samplepagingroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gpetuhov.android.samplepagingroom.R
import com.gpetuhov.android.samplepagingroom.models.User

class UserAdapter(val context: Context) : PagedListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onBindViewHolder(holderUser: UserViewHolder, position: Int) {
        var person = getItem(position)

        if (person == null) {
            holderUser.clear()
        } else {
            holderUser.bind(person)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user,
                parent, false))
    }

    // === Inner classes ===

    class UserViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = view as TextView

        fun bind(user: User) {
            tvName.text = user.name
        }

        fun clear() {
            tvName.text = null
        }
    }
}