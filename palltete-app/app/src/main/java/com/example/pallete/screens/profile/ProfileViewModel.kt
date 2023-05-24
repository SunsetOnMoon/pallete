package com.example.pallete.screens.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.auth.AuthManager

class ProfileViewModel : ViewModel() {
    val name: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val subscribers: MutableLiveData<String> = MutableLiveData()
    val subscriptions: MutableLiveData<String> = MutableLiveData()

    init {
        val user = AuthManager.getUser()
        Log.d("UserInfo", user.toString())
        name.value = user.name
        description.value = user.description ?: ""
        subscribers.value = user.subscribers.toString()
        subscriptions.value = user.subscriptions.toString()
    }
}