package com.example.nextwaveconnect.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nextwaveconnect.R
import com.example.nextwaveconnect.databinding.FragmentPostsBinding
import com.example.nextwaveconnect.domain.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: PostViewModel
    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]

        binding.bSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.bSend.setOnClickListener {
            val userUID = auth.currentUser?.uid
            val text = binding.etNewPost.text.toString()
            val tags = binding.groupTag.checkedRadioButtonId

            if (tags == -1) {
                Toast.makeText(
                    context,
                    "You need to choose tag",
                    Toast.LENGTH_LONG,
                ).show()
            } else {
                val tag = (when (tags) {
                    binding.rbIT.id -> "it"
                    binding.rbMemes.id -> "memes"
                    else -> "other"
                })
                viewModel.addPost(Post(userUID, text, tag))
            }
        }

        val myRef = Firebase.database.getReference("users")

        myRef.child(auth.currentUser?.uid ?: "No UID").get().addOnSuccessListener {
            val currentTag = it.child("interest").value.toString()

            binding.sRec.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.getRecommendedPosts(postsListener, currentTag)
                } else {
                    viewModel.getPost(postsListener)
                }
            }

            if (binding.sRec.isChecked) {
                viewModel.getRecommendedPosts(postsListener, currentTag)
            } else {
                viewModel.getPost(postsListener)
            }
        }

        initRcView()
        return binding.root
    }

    private fun initRcView() = with(binding) {
        adapter = PostAdapter()
        rcViewPosts.layoutManager = LinearLayoutManager(context)
        rcViewPosts.adapter = adapter
    }

    private val postsListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val posts = mutableListOf<Post>()
            for (dataSnapshot in snapshot.children) {
                val post = dataSnapshot.getValue(Post::class.java)
                post?.let { posts.add(it) }
            }

            adapter.submitList(posts)
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}