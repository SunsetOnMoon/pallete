package com.example.pallete.screens.filters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pallete.models.post.Post
import com.example.pallete.posts.FilterPostsManager

class FilterViewModel: ViewModel() {
    val posts: MutableLiveData<List<Post>> = MutableLiveData()
    val isSetted: MutableLiveData<Boolean> = MutableLiveData()

    fun filter(topicId: Int) {
        posts.value = FilterPostsManager.getPosts()
        posts.value = posts.value?.filter { it.topicId == topicId }
        FilterPostsManager.setPosts(posts.value!!)
        isSetted.value = true
    }
}