package com.example.nextwaveconnect.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nextwaveconnect.R
import com.example.nextwaveconnect.databinding.FragmentResetPassBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ResetPassFragment : Fragment() {
    private var _binding: FragmentResetPassBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPassBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val myRef = Firebase.database.getReference("users")
        val emailList = mutableListOf<String>()

        myRef.get().addOnSuccessListener { dataSnapshot ->
            dataSnapshot.children.forEach { dataSnapshot1 ->
                emailList.add(dataSnapshot1.child("email").value.toString())
            }
        }

        binding.etReset.setOnClickListener {
            if (emailList.contains(binding.etEmail.text.toString())) {
                auth.sendPasswordResetEmail(binding.etEmail.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Check your email to reset password",
                            Toast.LENGTH_LONG,
                        ).show()
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.auth_container, LoginFragment())
                            .commit()
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "Incorrect email",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}