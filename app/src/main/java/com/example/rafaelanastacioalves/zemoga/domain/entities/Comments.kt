package com.example.rafaelanastacioalves.zemoga.domain.entities

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
