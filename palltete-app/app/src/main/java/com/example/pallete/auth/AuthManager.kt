package com.example.pallete.auth

import com.example.pallete.models.user.User

object AuthManager {
    private var user: User? = null

    fun setUser(user: User) {
        this.user = user
    }
    fun getUser() : User {
        return user!!


    }
}