package com.example.nextwaveconnect.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.nextwaveconnect.R
import com.example.nextwaveconnect.databinding.FragmentLoginBinding
import com.example.nextwaveconnect.databinding.FragmentPostsBinding
import com.example.nextwaveconnect.presentation.auth.AuthViewModel
import com.example.nextwaveconnect.presentation.auth.RegisterFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.bSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}