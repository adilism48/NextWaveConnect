package com.example.nextwaveconnect.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nextwaveconnect.databinding.FragmentSettingsBinding
import com.example.nextwaveconnect.presentation.auth.AuthActivity
import com.example.nextwaveconnect.presentation.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.bLogout.setOnClickListener {
            logout()
        }

        binding.bChangePass.setOnClickListener {
            val currentUserEmail = auth.currentUser?.email

            if (currentUserEmail != null) {
                auth.sendPasswordResetEmail(currentUserEmail).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            context,
                            "To change pass follow instructions sent to email",
                            Toast.LENGTH_LONG,
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Error",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
            logout()
        }

        val myRef = Firebase.database.getReference("users")

        myRef.child(auth.currentUser?.uid ?: "No UID").get().addOnSuccessListener {
            binding.sEmailVisible.isChecked = it.child("showEmail").value as Boolean
            binding.tvInterest.text = it.child("interest").value.toString()
        }

        binding.sEmailVisible.setOnCheckedChangeListener { _, isChecked ->
            myRef.child(auth.currentUser?.uid ?: "No UID")
                .updateChildren(hashMapOf<String, Any?>("showEmail" to isChecked))
        }

        binding.tvEmail.text = auth.currentUser?.email ?: "Not auth"

        binding.bSaveInterests.setOnClickListener {
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
                myRef.child(auth.currentUser?.uid ?: "No UID")
                    .updateChildren(hashMapOf<String, Any?>("interest" to tag))
                myRef.child(auth.currentUser?.uid ?: "No UID").get().addOnSuccessListener {
                    binding.tvInterest.text = it.child("interest").value.toString()
                }
                Toast.makeText(
                    context,
                    "Interests changed",
                    Toast.LENGTH_LONG,
                ).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logout() {
        auth.signOut()
        startActivity(
            Intent(
                context,
                AuthActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        )
    }
}