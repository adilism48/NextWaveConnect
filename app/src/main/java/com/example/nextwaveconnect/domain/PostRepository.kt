package com.example.nextwaveconnect.domain

import com.google.firebase.database.ValueEventListener

interface PostRepository {
    fun addPost(post: Post)
    fun getPosts(listener: ValueEventListener)
    fun getRecommendedPosts(listener: ValueEventListener, currentTag: String)
}