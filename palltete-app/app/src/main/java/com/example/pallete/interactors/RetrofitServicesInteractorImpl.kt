package com.example.pallete.interactors

import android.util.Log
import com.example.pallete.common.Common
import com.example.pallete.models.Comment
import com.example.pallete.models.idea.Idea
import com.example.pallete.models.post.Post
import com.example.pallete.models.post.PostSearch
import com.example.pallete.models.post.PostUpdate
import com.example.pallete.models.topic.Topic
import com.example.pallete.models.user.LoginUser
import com.example.pallete.models.user.User
import com.example.pallete.models.user.UserRegister
import com.example.pallete.models.user.UserUpdate
import io.reactivex.rxjava3.core.Single

class RetrofitServicesInteractorImpl(
) : RetrofitServicesInteractor {

    private val retrofitServices = Common.retrofitService


    override fun getUsers(): Single<List<User>> {
        return Single.create { emitter ->
            val result = retrofitServices.getUsers().execute()
            if (result.isSuccessful){
                val response = result.body()!!
                emitter.onSuccess(response)
            }
            else {
                emitter.onError(RuntimeException("Can't get users"))
            }
        }
    }

    override fun registerUser(userRegister: UserRegister): Single<User> {
        return Single.create { emitter ->
//            Log.d("registerUser", userRegister.toString())
            val result = retrofitServices.registerUser(userRegister).execute()
            Log.d("result", result.toString())
            if (result.isSuccessful) {
                val response = result.body()!!
                Log.d("Response", response.toString())
                emitter.onSuccess(response)
            } else {
                emitter.onError(RuntimeException("Can't register new user"))
            }
        }
    }

    override fun getUserById(id: Int): Single<User> {
        return Single.create { emitter ->
            val result = retrofitServices.getUserById(id).execute()
            if (result.isSuccessful) {
                val response = result.body()!!
                emitter.onSuccess(response)
            } else {
                emitter.onError(RuntimeException("Can't get user by id"))
            }
        }
    }

    override fun loginUser(name: String, password: String): Single<User> {
        return Single.create{ emitter ->
            val result = retrofitServices.loginUser(LoginUser(name, password)).execute()
            if (result.isSuccessful)
            {
                val response = result.body()!!
                Log.d("LoginInteractor:", response.toString())
                emitter.onSuccess(response)
            } else {
                emitter.onError(RuntimeException("Can't login user"))
            }
        }
    }

    override fun updateUser(id: Int, name: String, description: String): Single<User> {
        return Single.create { emitter ->
            val result = retrofitServices.updateUser(id, UserUpdate(name, description)).execute()

            if (result.isSuccessful){
                val response = result.body()!!
                Log.d("UpdateInteractor", response.toString())
                emitter.onSuccess(response)
            } else {
                emitter.onError(RuntimeException("Can't update user"))
            }
        }
    }

    override fun getUserIdeas(userId: Int): Single<List<Idea>> {
        return Single.create {emitter ->
            val result = retrofitServices.getUserIdeas(userId).execute()
            if (result.isSuccessful) {
                val response = result.body()!!
                emitter.onSuccess(response)
            } else {
                emitter.onError(RuntimeException("Can't get user ideas"))
            }
        }
    }

    override fun getAllPosts(): Single<List<Post>> {
        return Single.create { emitter ->
            val result = retrofitServices.getAllPosts().execute()

            if (result.isSuccessful) {
                val response = result.body()!!
                emitter.onSuccess(response)
            } else {
                emitter.onError(RuntimeException("Can't get all posts"))
            }
        }
    }

    override fun getUserPosts(authorId: Int): Single<List<Post>> {
        return Single.create { emitter ->
            val result = retrofitServices.getUserPosts(authorId).execute()

            if (result.isSuccessful) {
                val response = result.body()!!
                emitter.onSuccess(response)
            } else {
                emitter.onError(RuntimeException("Can't get user posts"))
            }
        }
    }

    override fun updatePost(postId: Int, title: String, description: String?, ideaId: Int?, topicId: Int): Single<Post> {
        return Single.create { emitter ->
            val response = retrofitServices.updatePost(postId, PostUpdate(
                postId = postId,
                title = title,
                description = description,
                ideaId = ideaId,
                topicId = topicId
            )).execute()
            if (response.isSuccessful) {
                val result = response.body()!!
                emitter.onSuccess(result)
            } else {
                emitter.onError(RuntimeException("Can't update post"))
            }
        }
    }

    override fun searchPosts(query: String): Single<List<Post>> {
        return Single.create { emitter ->
            val result = retrofitServices.searchPosts(PostSearch(query)).execute()

            if (result.isSuccessful) {
                val response = result.body()!!
                emitter.onSuccess(response)
            } else {
                emitter.onError(RuntimeException("Can't search posts"))
            }
        }
    }

    override fun getPostComments(postId: Int): Single<List<Comment>> {
        return Single.create { emitter ->
            val result = retrofitServices.getPostComments(postId).execute()

            if (result.isSuccessful) {
                val response = result.body()!!
                emitter.onSuccess(response)
            } else {
                emitter.onError(RuntimeException("Can't get comments by post"))
            }
        }
    }

    override fun getTopics(): Single<List<Topic>> {
        return Single.create { emitter ->
            val response = retrofitServices.getTopics().execute()

            if (response.isSuccessful) {
                val result = response.body()!!
                emitter.onSuccess(result)
            } else {
                emitter.onError(RuntimeException("Can't get topics"))
            }
        }
    }
}