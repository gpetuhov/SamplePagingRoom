package com.gpetuhov.android.samplepagingroom.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// Our dummy data item is just an id and a string
@Entity(tableName = "users")
data class User(
        @PrimaryKey val id: String,
        val name: String
) {
    override fun toString() = name
}