package com.example.pallete.screens.postcreate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.interactors.RetrofitServicesInteractor
import com.example.pallete.interactors.RetrofitServicesInteractorImpl
import com.example.pallete.models.post.Post
import com.example.pallete.models.topic.Topic
import com.example.pallete.posts.PostManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CreatePostViewModel: ViewModel() {

    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val topicId: MutableLiveData<Int> = MutableLiveData()

    val topics: MutableLiveData<List<Topic>> = MutableLiveData()
    private val retrofitServicesInteractor: RetrofitServicesInteractor = RetrofitServicesInteractorImpl()
    init {
        title.value = ""
        description.value = ""
        topicId.value = -1

        val disposable = retrofitServicesInteractor.getTopics()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                topics.value = it
            }, {

            })
    }

    fun onEditTitle(text: String) {
        title.value = text
    }

    fun onEditDescription(text: String) {
        description.value = text
    }

    fun onChooseTopic(id: Int) {
        topicId.value = id
    }

}