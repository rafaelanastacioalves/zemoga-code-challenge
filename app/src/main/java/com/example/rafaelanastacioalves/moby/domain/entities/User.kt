package com.example.rafaelanastacioalves.moby.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name ="username")
    val username : String,
    @ColumnInfo(name = "email")
    val email : String
)
