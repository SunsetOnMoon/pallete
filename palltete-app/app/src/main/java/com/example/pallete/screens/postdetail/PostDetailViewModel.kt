package com.example.pallete.screens.postdetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.interactors.RetrofitServicesInteractor
import com.example.pallete.interactors.RetrofitServicesInteractorImpl
import com.example.pallete.models.Comment
import com.example.pallete.models.post.Post
import com.example.pallete.models.user.User
import com.example.pallete.posts.PostManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PostDetailViewModel : ViewModel() {
    val post: MutableLiveData<Post> = MutableLiveData()
    val authorName: MutableLiveData<String> = MutableLiveData()
    val comments: MutableLiveData<List<Comment>> = MutableLiveData()
    val commentators: MutableLiveData<List<User>> = MutableLiveData()

    private val retrofitServicesInteractor: RetrofitServicesInteractor = RetrofitServicesInteractorImpl()
    init {
        post.value = PostManager.getPost()
        val disposableAuthor = retrofitServicesInteractor.getUserById(post.value!!.authorId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({newUser ->
                Log.d("AuthorName", newUser.toString())
                authorName.value = newUser.name
            }, {

            })

        val disposableComments = retrofitServicesInteractor.getPostComments(post.value!!.postId)
            .subscribeOn(Schedulers.io())
            .flatMap { newComments ->
                retrofitServicesInteractor.getUsers()
                    .subscribeOn(Schedulers.io())
                    .map { newUsers ->
                        Pair(newComments, newUsers)
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({pair ->
                comments.value = pair.first!!
                commentators.value = pair.second!!
            },
            {

            })
    }
}