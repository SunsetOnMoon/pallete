package com.example.pallete.interactors

import android.util.Log
import com.example.pallete.common.Common
import com.example.pallete.models.idea.Idea
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
}