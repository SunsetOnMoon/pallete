package com.example.pallete.screens.postdetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pallete.databinding.ListitemCommentBinding
import com.example.pallete.models.Comment
import com.example.pallete.models.post.Post
import com.example.pallete.models.user.User

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    var data: List<Comment> = emptyList()
    var commentators: List<User> = emptyList()
    inner class CommentViewHolder(val binding: ListitemCommentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListitemCommentBinding.inflate(inflater, parent, false)

        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = data[position]

        with (holder.binding) {
            txtCommentContent.text = comment.content
            val user = commentators.find { it.userId == comment.authorId }
            txtAuthor.text = user?.name ?: "Unknown"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPostsData(comments: List<Comment>) {
        this.data = comments
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUserData(users: List<User>) {
        this.commentators = users
        notifyDataSetChanged()
    }
}