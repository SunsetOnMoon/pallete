package com.example.pallete.screens.registration

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.auth.AuthManager
import com.example.pallete.interactors.RetrofitServicesInteractor
import com.example.pallete.interactors.RetrofitServicesInteractorImpl
import com.example.pallete.models.user.User
import com.example.pallete.models.user.UserRegister
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RegistrationViewModel: ViewModel() {
    val name: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val user: MutableLiveData<User> = MutableLiveData()
    private val retrofitServicesInteractor: RetrofitServicesInteractor = RetrofitServicesInteractorImpl()
//    val name: LiveData<String>
//        get() = _name
//    val email: LiveData<String>
//        get() = _email
//    val password: LiveData<String>
//        get() = _password

    init {
        name.value = ""
        email.value = ""
        password.value = ""
    }

    fun onEditName(text: String) {
        name.value = text
    }

    fun onEditEmail(text: String) {
        email.value = text
    }

    fun onEditPassword(text: String) {
        password.value = text
    }

    fun register() {
        Log.d("Login is", name.value!!)
        Log.d("Email is", email.value!!)
        Log.d("Password is", password.value!!)

        val disposable = retrofitServicesInteractor.registerUser(UserRegister(name.value!!, email.value!!, password.value!!))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({newUser ->
                Log.d("NewUser:", newUser.toString())
                AuthManager.setUser(newUser)
                user.value = newUser
            }, {error ->
                Error(error)
            })
    }
}