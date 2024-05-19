package com.example.nextwaveconnect.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.nextwaveconnect.databinding.PostListItemBinding
import com.example.nextwaveconnect.domain.Post
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PostViewHolder(private val binding: PostListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) = with(binding) {
        val myRef = Firebase.database.getReference("users")

        myRef.child(post.userUID ?: "NO UID").get().addOnSuccessListener {
            tvEmail.text = it.child("email").value.toString()
            tvName.text = it.child("name").value.toString()
            tvEmail.isVisible = it.child("showEmail").value as Boolean
        }

        tvPost.text = post.text
        tvTag.text = post.tag
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