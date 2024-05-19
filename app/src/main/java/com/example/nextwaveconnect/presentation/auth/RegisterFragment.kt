package com.example.nextwaveconnect.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nextwaveconnect.R
import com.example.nextwaveconnect.databinding.FragmentRegisterBinding
import com.example.nextwaveconnect.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        myRef = Firebase.database.getReference("users")
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.bRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signUp(email, password).observe(viewLifecycleOwner) {
                    if (it.isSuccess) {
                        myRef.child(auth.currentUser?.uid ?: "noUID").setValue(User(name, auth.currentUser?.email, true, "other"))
                        Toast.makeText(
                            context,
                            "Account created",
                            Toast.LENGTH_SHORT,
                        ).show()
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.auth_container, LoginFragment())
                            .addToBackStack(null)
                            .commit()
                    } else if (it.isFailure) {
                        Toast.makeText(
                            context,
                            "Already been registered",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}