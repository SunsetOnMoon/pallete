package com.example.pallete.screens.postedit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.interactors.RetrofitServicesInteractor
import com.example.pallete.interactors.RetrofitServicesInteractorImpl
import com.example.pallete.models.post.Post
import com.example.pallete.models.topic.Topic
import com.example.pallete.posts.PostManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class EditPostViewModel : ViewModel() {
    val post: MutableLiveData<Post> = MutableLiveData()
    val topics: MutableLiveData<List<Topic>> = MutableLiveData()
    val successfulUpdate: MutableLiveData<Boolean> = MutableLiveData()

    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val topicId: MutableLiveData<Int> = MutableLiveData()

    private val retrofitServicesInteractor: RetrofitServicesInteractor = RetrofitServicesInteractorImpl()
    init {
        post.value = PostManager.getPost()
        successfulUpdate.value = false
        title.value = post.value?.title
        description.value = post.value?.description ?: ""
        topicId.value = post.value?.topicId
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

    fun update(title: String, description: String?, topicId: Int) {
        val disposable = retrofitServicesInteractor.updatePost(
            postId = post.value?.postId!!,
            title = title,
            description = description,
            ideaId = post.value?.ideaId,
            topicId = topicId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                PostManager.setPost(it)
                successfulUpdate.value = true
            }, {

            })
    }
}