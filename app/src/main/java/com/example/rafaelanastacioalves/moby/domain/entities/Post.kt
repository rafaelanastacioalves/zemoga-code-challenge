package com.example.rafaelanastacioalves.moby.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
 data class Post (
       @PrimaryKey  var id: String,
       @ColumnInfo(name = "userId") var userId: String,
       @ColumnInfo(name = "title") var title: String,
       @ColumnInfo(name = "body") var body: String

)


