package com.example.nextwaveconnect.domain

import com.google.firebase.database.ValueEventListener

class GetRecommendedPosts (private val postRepository: PostRepository) {
    fun getRecommendedPosts(listener: ValueEventListener, currentTag: String) {
        postRepository.getRecommendedPosts(listener, currentTag)
    }
}