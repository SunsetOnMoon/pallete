package com.example.pallete.retrofitservices

import com.example.pallete.models.Comment
import com.example.pallete.models.idea.Idea
import com.example.pallete.models.idea.NewIdea
import com.example.pallete.models.post.Post
import com.example.pallete.models.post.PostSearch
import com.example.pallete.models.post.PostUpdate
import com.example.pallete.models.topic.Topic
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

    @POST("/api/ideas")
    fun addIdea(@Body newIdea: NewIdea): Call<Idea>

    @POST("/api/colors")
    fun setIdeaColors(@Body ideaId: Int, color: String): Call<Boolean>

    @DELETE("/api/ideas/{id}")
    fun deleteIdea(@Path("id") ideaId: Int): Call<Boolean>
    //-------------------------POSTS----------------------------------------
    @GET("/api/posts")
    fun getAllPosts(): Call<List<Post>>

    @GET("/api/posts/user/{id}")
    fun getUserPosts(@Path("id") userId: Int): Call<List<Post>>

    @POST("/api/posts/search")
    fun searchPosts(@Body postSearch: PostSearch = PostSearch()): Call<List<Post>>

    @PUT("/api/posts/{id}")
    fun updatePost(@Path("id") postId: Int, @Body postUpdate: PostUpdate) : Call<Post>

    //-------------------------COMMENTS--------------------------------------
    @GET("/api/comments/post/{id}")
    fun getPostComments(@Path("id") postId: Int): Call<List<Comment>>

    //-------------------------TOPICS----------------------------------------
    @GET("/api/topics")
    fun getTopics(): Call<List<Topic>>
}