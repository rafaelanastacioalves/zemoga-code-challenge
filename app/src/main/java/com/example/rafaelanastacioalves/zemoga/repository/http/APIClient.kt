package com.example.rafaelanastacioalves.zemoga.repository.http;


import com.example.rafaelanastacioalves.zemoga.domain.entities.Comment
import com.example.rafaelanastacioalves.zemoga.domain.entities.Post
import com.example.rafaelanastacioalves.zemoga.domain.entities.User
import retrofit2.http.GET
import retrofit2.http.Path

interface APIClient {
    @GET("/posts")
    suspend fun getPosts(): List<Post>;

    @GET("/posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: String): Post

    @GET("/users/{userId}")
    suspend fun getUserById(@Path("userId") userId: String) : User

    @GET("/posts/{postId}/comments")
    suspend fun getCommentsForPostId(@Path("postId") postId: String) : List<Comment>
}
