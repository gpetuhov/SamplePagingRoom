package com.gpetuhov.android.samplepagingroom.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gpetuhov.android.samplepagingroom.models.User

class UserDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}