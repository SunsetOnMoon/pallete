package com.example.pallete.posts

import com.example.pallete.models.post.Post

object PostManager {
    private var post: Post? = null
    fun setPost(post: Post) {
        this.post = post
    }

    fun getPost() : Post {
        return post!!
    }
}