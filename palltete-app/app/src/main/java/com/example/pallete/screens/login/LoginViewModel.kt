package com.example.pallete.screens.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.auth.AuthManager
import com.example.pallete.interactors.RetrofitServicesInteractor
import com.example.pallete.interactors.RetrofitServicesInteractorImpl
import com.example.pallete.models.user.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel: ViewModel() {
    private val retrofitServicesInteractor: RetrofitServicesInteractor = RetrofitServicesInteractorImpl()
    val name: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val user: MutableLiveData<User> = MutableLiveData()
    init {
        name.value = ""
        password.value = ""
    }

    fun onEditName(text: String) {
        name.value = text
    }
    fun onEditPassword(text: String) {
        password.value = text
    }

    fun signIn() {
        val disposable = retrofitServicesInteractor.loginUser(name.value!!, password.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({newUser ->
                user.value = newUser
                AuthManager.setUser(user.value!!)
                Log.d("LoginUser:", newUser.toString())
            }, {error ->
                Error(error)
            })
    }
}