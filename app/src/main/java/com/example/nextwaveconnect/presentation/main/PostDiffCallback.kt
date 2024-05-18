package com.example.nextwaveconnect.presentation.main

import androidx.recyclerview.widget.DiffUtil
import com.example.nextwaveconnect.domain.Post

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}