package com.example.pallete.posts

import com.example.pallete.models.post.Post

object FilterPostsManager {
    private var posts: List<Post>? = null

    fun setPosts(posts: List<Post>) {
        this.posts = posts
    }

    fun getPosts(): List<Post>? {
        return posts
    }
}