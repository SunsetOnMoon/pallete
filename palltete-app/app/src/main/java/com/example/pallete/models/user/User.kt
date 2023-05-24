package com.example.pallete.models.user

data class User(
    val userId: Int,
    val name: String,
    val description: String? = null,
    val subscribers: Int = 0,
    val subscriptions: Int = 0,
    val password: String? = null,
    val email: String? = null
)
