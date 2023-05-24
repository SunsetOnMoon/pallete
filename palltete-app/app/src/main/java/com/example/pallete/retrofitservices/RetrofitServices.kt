package com.example.pallete.retrofitservices

import com.example.pallete.models.idea.Idea
import com.example.pallete.models.user.LoginUser
import com.example.pallete.models.user.User
import com.example.pallete.models.user.UserRegister
import com.example.pallete.models.user.UserUpdate
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {
    //---------------------------USERS-------------------------------------
    @GET("/api/users")
    fun getUsers(): Call<List<User>>

    @GET("/api/users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @POST("/api/users/register")
    fun registerUser(@Body userRegister: UserRegister) : Call<User>

    @POST("/api/users/valid")
    fun loginUser(@Body loginUser: LoginUser) : Call<User>

    @PUT("/api/users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: UserUpdate): Call<User>


    //--------------------------IDEAS---------------------------------------
    @GET("/api/ideas/user/{id}")
    fun getUserIdeas(@Path("id") userId: Int): Call<List<Idea>>



}