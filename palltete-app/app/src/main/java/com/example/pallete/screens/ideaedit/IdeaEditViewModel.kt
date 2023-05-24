package com.example.pallete.screens.ideaedit

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.interactors.RetrofitServicesInteractor
import com.example.pallete.interactors.RetrofitServicesInteractorImpl
import kotlin.random.Random

class IdeaEditViewModel: ViewModel() {
    private val retrofitServicesInteractor: RetrofitServicesInteractor = RetrofitServicesInteractorImpl()
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val colors: MutableLiveData<MutableList<String>> = MutableLiveData()
    val topicId: MutableLiveData<Int> = MutableLiveData()

    init {
        title.value = ""
        description.value = ""
        topicId.value = 0
//        colors.value = emptyList<String>().toMutableList()
    }

    fun generate() {
        title.value = "Mountains"
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
    }
}