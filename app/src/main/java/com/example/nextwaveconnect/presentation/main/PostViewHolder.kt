package com.example.nextwaveconnect.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nextwaveconnect.databinding.PostListItemBinding
import com.example.nextwaveconnect.domain.Post

class PostViewHolder(private val binding: PostListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) = with(binding) {
        tvPost.text = post.text
        tvEmail.text = post.email
    }

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            return PostViewHolder(
                PostListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}