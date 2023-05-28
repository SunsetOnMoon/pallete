package com.example.pallete.screens.feed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.interactors.RetrofitServicesInteractor
import com.example.pallete.interactors.RetrofitServicesInteractorImpl
import com.example.pallete.models.post.Post
import com.example.pallete.models.user.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FeedViewModel : ViewModel() {
    private val retrofitServicesInteractor: RetrofitServicesInteractor = RetrofitServicesInteractorImpl()
    val posts: MutableLiveData<List<Post>> = MutableLiveData()
    val users: MutableLiveData<List<User>> = MutableLiveData()

    init {
        val disposablePosts = retrofitServicesInteractor.getAllPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newPosts ->
                posts.value = newPosts
            }, {

            })

        val disposableUsers = retrofitServicesInteractor.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({newUsers ->
                users.value = newUsers
            }, {

            })
    }

    fun search(query: String?) {
        Log.d("Query", query ?: "")
        val disposable = retrofitServicesInteractor.searchPosts(query ?: "")
            .subscribeOn(Schedulers.io())
            .delay(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({newPosts ->
                Log.d("Searched Posts", newPosts.toString())
                posts.value = newPosts
            }, {

            })
    }
}