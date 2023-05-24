package com.example.pallete.screens.profileedit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.auth.AuthManager
import com.example.pallete.interactors.RetrofitServicesInteractor
import com.example.pallete.interactors.RetrofitServicesInteractorImpl
import com.example.pallete.models.user.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class EditProfileViewModel : ViewModel() {
    private val retrofitServicesInteractor: RetrofitServicesInteractor = RetrofitServicesInteractorImpl()
    private val id: MutableLiveData<Int> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val updatedUser: MutableLiveData<User> = MutableLiveData()

    init {
        val user = AuthManager.getUser()
        id.value = user.userId
        name.value = user.name
        description.value = user.description ?: ""
    }

    fun onEditName(text: String) {
        name.value = text
    }
    fun onEditDescription(text: String) {
        description.value = text
    }

    fun edit() {
        val userId = AuthManager.getUser().userId
        val disposable = retrofitServicesInteractor.updateUser(userId, name.value!!, description.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({newUser ->
                Log.d("Edited User", newUser.toString())
                AuthManager.setUser(newUser)
                updatedUser.value = newUser
            }, {

            })
    }
}