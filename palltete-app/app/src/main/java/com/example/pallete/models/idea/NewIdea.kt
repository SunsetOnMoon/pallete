package com.example.pallete.models.idea

data class NewIdea(
    val name: String,
    val description: String?,
    val topicId: Int,
    val userId: Int
)
