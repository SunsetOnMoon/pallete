package com.example.pallete.screens.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.auth.AuthManager
import com.example.pallete.interactors.RetrofitServicesInteractor
import com.example.pallete.interactors.RetrofitServicesInteractorImpl
import com.example.pallete.models.post.Post
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProfileViewModel : ViewModel() {
    val name: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val subscribers: MutableLiveData<String> = MutableLiveData()
    val subscriptions: MutableLiveData<String> = MutableLiveData()
    val posts: MutableLiveData<List<Post>> = MutableLiveData()

    private val retrofitServicesInteractor: RetrofitServicesInteractor = RetrofitServicesInteractorImpl()

    init {
        val user = AuthManager.getUser()
        Log.d("UserInfo", user.toString())
        name.value = user.name
        description.value = user.description ?: ""
        subscribers.value = user.subscribers.toString()
        subscriptions.value = user.subscriptions.toString()

        val disposable = retrofitServicesInteractor.getUserPosts(user.userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({newPosts ->
                posts.value = newPosts
            }, {

            })
    }
}