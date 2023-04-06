package com.example.rafaelanastacioalves.moby.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rafaelanastacioalves.moby.domain.entities.Post

@Dao
interface DAO {

    @Query("SELECT * FROM post")
    fun getPostList(): List<Post>

    @Insert
    fun savePostList(resultData: List<Post>?)

    @Delete
    fun delete(post: Post)

    @Query("DELETE FROM post")
    fun deleteAllPosts()
}