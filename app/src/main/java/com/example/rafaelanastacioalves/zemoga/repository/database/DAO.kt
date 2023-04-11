package com.example.rafaelanastacioalves.zemoga.repository.database

import androidx.room.*
import com.example.rafaelanastacioalves.zemoga.domain.entities.Post
import com.example.rafaelanastacioalves.zemoga.domain.entities.User

@Dao
interface DAO {

    @Query("SELECT * FROM post ORDER BY id ASC")
    fun getPostList(): List<Post>


    @Insert
    fun savePostList(resultData: List<Post>?)

    @Delete
    fun delete(post: Post)

    @Query("DELETE FROM post")
    fun deleteAllPosts()

    @Query("SELECT * FROM post WHERE id = :postId")
    fun getPostById(postId: String): Post?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePost(post: Post): Long


    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserById(userId: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)



}