package com.example.pallete.models.post

data class PostUpdate(
    val postId: Int,
    val title: String,
    val description: String? = null,
    val ideaId: Int?,
    val topicId: Int
)
