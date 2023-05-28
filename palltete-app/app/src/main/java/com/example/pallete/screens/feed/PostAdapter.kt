package com.example.pallete.screens.feed

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pallete.MAIN
import com.example.pallete.R
import com.example.pallete.databinding.ListitemPostBinding
import com.example.pallete.models.post.Post
import com.example.pallete.models.user.User
import com.example.pallete.posts.PostManager
import com.squareup.picasso.Picasso

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    var data: List<Post> = emptyList()
    var users: List<User> = emptyList()
    inner class PostViewHolder(val binding: ListitemPostBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val clickedPost = getItem(position)
                PostManager.setPost(clickedPost)
                MAIN.navController.navigate(R.id.action_feedFragment_to_postDetailFragment)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =  ListitemPostBinding.inflate(inflater, parent, false)

        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = data[position]

        with(holder.binding) {
            Picasso.get().load(post.imagePath).into(imgPicture)
            txtTitle.text = post.title
            txtRate.text = post.rate.toString()
            val user = users.find { it.userId == post.authorId }
            txtAuthor.text = user?.name
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPostsData(posts: List<Post>) {
        this.data = posts
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUserData(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    fun getItem(position: Int) : Post {
        return data[position]
    }
}