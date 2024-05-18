package com.example.nextwaveconnect.domain

class AddPostUseCase(private val postRepository: PostRepository) {
    fun addPost(post: Post) {
        postRepository.addPost(post)
    }
}