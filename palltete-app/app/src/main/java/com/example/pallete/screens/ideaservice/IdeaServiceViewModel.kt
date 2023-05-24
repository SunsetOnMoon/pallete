package com.example.pallete.screens.ideaservice

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.auth.AuthManager
import com.example.pallete.interactors.RetrofitServicesInteractor
import com.example.pallete.interactors.RetrofitServicesInteractorImpl
import com.example.pallete.models.idea.Idea
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class IdeaServiceViewModel : ViewModel() {
    private val retrofitServicesInteractor: RetrofitServicesInteractor = RetrofitServicesInteractorImpl()
    val ideas: MutableLiveData<List<Idea>> = MutableLiveData()

    init {
        val userId = AuthManager.getUser().userId
        val disposable = retrofitServicesInteractor.getUserIdeas(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newIdeas ->
                Log.d("IdeaService", newIdeas.toString())
                ideas.value = newIdeas
            }, {

            })
    }

}