package com.example.nextwaveconnect.presentation.main

import androidx.lifecycle.ViewModel
import com.example.nextwaveconnect.data.PostRepositoryImpl
import com.example.nextwaveconnect.domain.AddPostUseCase
import com.example.nextwaveconnect.domain.GetPostsUseCase
import com.example.nextwaveconnect.domain.Post
import com.google.firebase.database.ValueEventListener

class PostViewModel: ViewModel() {
    private val repository = PostRepositoryImpl
    private val addPostUseCase = AddPostUseCase(repository)
    private val getPostsUseCase = GetPostsUseCase(repository)

    fun addPost(post: Post) {
        addPostUseCase.addPost(post)
    }

    fun getPost(listener: ValueEventListener) {
        getPostsUseCase.getPosts(listener)
    }
}