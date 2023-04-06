package com.example.rafaelanastacioalves.moby.repository.http;


import com.example.rafaelanastacioalves.moby.domain.entities.EntityDetails
import com.example.rafaelanastacioalves.moby.domain.entities.Post
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIClient {
    @GET("/posts")
    suspend fun getMainEntityList(): List<Post>;

    @GET("/posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: Int): Post
}
