package com.example.pallete.screens.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pallete.MAIN
import com.example.pallete.R
import com.example.pallete.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.user.observe(viewLifecycleOwner, Observer {
            MAIN.navController.navigate(R.id.action_loginFragment_to_gallery_fragment)
        })

        binding.teName.doOnTextChanged { text, start, before, count ->
            viewModel.onEditName(text.toString())
        }
        binding.tePassword.doOnTextChanged { text, start, before, count ->
            viewModel.onEditPassword(text.toString())
        }
        binding.btnRegistration.setOnClickListener {
            MAIN.navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.btnSignin.setOnClickListener {
            viewModel.signIn()
        }
    }
}