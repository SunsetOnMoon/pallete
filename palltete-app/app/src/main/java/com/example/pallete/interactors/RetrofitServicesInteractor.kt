package com.example.pallete.interactors

import com.example.pallete.models.idea.Idea
import com.example.pallete.models.user.User
import com.example.pallete.models.user.UserRegister
import io.reactivex.rxjava3.core.Single

interface RetrofitServicesInteractor {
    fun getUsers(): Single<List<User>>
    fun registerUser(userRegister: UserRegister): Single<User>
    fun getUserById(id: Int): Single<User>
    fun loginUser(name: String, password: String): Single<User>
    fun updateUser(id: Int, name: String, description: String): Single<User>

    fun getUserIdeas(userId: Int): Single<List<Idea>>
}