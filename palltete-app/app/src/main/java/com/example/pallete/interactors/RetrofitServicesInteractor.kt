package com.example.pallete.interactors

import com.example.pallete.models.Comment
import com.example.pallete.models.idea.Idea
import com.example.pallete.models.post.Post
import com.example.pallete.models.topic.Topic
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
    fun setIdeaColors(ideaId: Int, color: String): Single<Boolean>
    fun addIdea(name: String, description: String?, topicId: Int, userId: Int): Single<Idea>
    fun deleteIdea(ideaId: Int): Single<Boolean>

    fun getAllPosts(): Single<List<Post>>
    fun getUserPosts(authorId: Int): Single<List<Post>>
    fun updatePost(postId: Int, title: String, description: String?, ideaId: Int?, topicId: Int): Single<Post>
    fun searchPosts(query: String) : Single<List<Post>>

    fun getPostComments(postId: Int): Single<List<Comment>>

    fun getTopics(): Single<List<Topic>>
}