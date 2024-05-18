package com.example.nextwaveconnect.data

import com.example.nextwaveconnect.domain.Post
import com.example.nextwaveconnect.domain.PostRepository
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object PostRepositoryImpl : PostRepository {
    private val myRef = Firebase.database.getReference("posts")

    override fun addPost(post: Post) {
        myRef.child(myRef.push().key ?: "").setValue(post)
    }

    override fun getPosts(listener: ValueEventListener) {
        myRef.addValueEventListener(listener)
    }
}