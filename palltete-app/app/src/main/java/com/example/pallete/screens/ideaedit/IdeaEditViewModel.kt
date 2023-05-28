package com.example.pallete.screens.ideaedit

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.auth.AuthManager
import com.example.pallete.interactors.RetrofitServicesInteractor
import com.example.pallete.interactors.RetrofitServicesInteractorImpl
import com.example.pallete.models.idea.Idea
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.random.Random

class IdeaEditViewModel: ViewModel() {
    private val retrofitServicesInteractor: RetrofitServicesInteractor = RetrofitServicesInteractorImpl()
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val colors: MutableLiveData<MutableList<String>> = MutableLiveData()
    val topicId: MutableLiveData<Int> = MutableLiveData()
    val idea: MutableLiveData<Idea> = MutableLiveData()

    val successfulSet: MutableLiveData<Boolean> = MutableLiveData()

    init {
        title.value = ""
        description.value = ""
        topicId.value = 0
//        colors.value = emptyList<String>().toMutableList()
    }

    fun generate() {
        val enabledNouns = listOf<String>("River", "Mountain", "Girl", "Orange", "Table", "Boy", "Family", "Card")
        val enabledAdjectives = listOf<String>("Beautiful", "Amazing", "Wonderful", "Fast")
        val noun = enabledNouns[Random.nextInt(enabledNouns.size)]
        val adjective = enabledAdjectives[Random.nextInt(enabledAdjectives.size)]
        title.value = "$adjective $noun"
        description.value = "Mountains with river and trees"
        colors.value = emptyList<String>().toMutableList()
//        if (!colors.value.isNullOrEmpty())
//            colors.value!!.removeAll(colors.value!!)
        val colorsCount = Random.nextInt(4) + 2
        for (i in 0..colorsCount) {
            val color = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            val hex = String.format("#%06X", 0xFFFFFF and color)
            colors.value!!.add(hex)
        }
        Log.d("Colors", colors.value.toString())

        val disposable = retrofitServicesInteractor.addIdea(title.value!!, description.value, topicId = 1, AuthManager.getUser().userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newIdea ->
                idea.value = newIdea
            }, {

            })
    }

    fun setColors() {
        for (color in colors.value!!) {
            val colorDisposable = retrofitServicesInteractor.setIdeaColors(idea.value?.ideaId!!, color)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    successfulSet.value = it
                }, {

                })
        }
    }
}