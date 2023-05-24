package com.example.pallete.models.idea

data class Idea(
    val ideaId: Int,
    val name: String,
    val description: String? = "",
    val topicId: Int,
    val userId: Int
)
