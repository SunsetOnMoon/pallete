package com.example.pallete.models.post

data class Post(
    val postId: Int,
    val title: String,
    val description: String? = null,
    val authorId: Int,
    val ideaId: Int? = null,
    val topicId: Int,
    val rate: Double? = 0.0,
    val imagePath: String
)
