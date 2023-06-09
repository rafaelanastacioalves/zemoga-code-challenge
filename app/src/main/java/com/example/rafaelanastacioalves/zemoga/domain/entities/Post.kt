package com.example.rafaelanastacioalves.zemoga.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 data class Post (
       @PrimaryKey  var id: Long,
       @ColumnInfo(name = "userId") var userId: String,
       @ColumnInfo(name = "title") var title: String,
       @ColumnInfo(name = "body") var body: String,
       @ColumnInfo(name= "deleted") var deleted: Boolean = false,
       @ColumnInfo(name = "favorited") var favorited: Boolean = false

)


