package com.example.nextwaveconnect.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nextwaveconnect.R
import com.example.nextwaveconnect.databinding.FragmentLoginBinding
import com.example.nextwaveconnect.presentation.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.bLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signIn(email, password).observe(viewLifecycleOwner) {
                    if (it.isSuccess) {
                        Toast.makeText(
                            context,
                            "Signed In.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        checkAuthState()
                    } else if (it.isFailure) {
                        Toast.makeText(
                            context,
                            "Incorrect Email or Password.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }

        binding.tvRegistration.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_container, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }

        checkAuthState()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkAuthState() {
        if (auth.currentUser != null) {
            val i = Intent(context, MainActivity::class.java)
            startActivity(i)
        }
    }
}