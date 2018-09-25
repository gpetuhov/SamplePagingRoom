package com.gpetuhov.android.samplepagingroom.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// Our dummy data item is just an autogenerated id and a string
@Entity(tableName = "users")
data class User(val name: String) {

    @PrimaryKey(autoGenerate = true) var id: Int = 0

    override fun toString() = name
}