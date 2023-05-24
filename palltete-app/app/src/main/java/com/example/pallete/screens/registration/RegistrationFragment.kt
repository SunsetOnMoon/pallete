package com.example.pallete.screens.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pallete.MAIN
import com.example.pallete.R
import com.example.pallete.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    lateinit var binding: FragmentRegistrationBinding
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.name.observe(viewLifecycleOwner, this)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

//        viewModel.name.observe(viewLifecycleOwner, Observer { text ->
//
//        })

        viewModel.user.observe(viewLifecycleOwner, Observer {
            MAIN.navController.navigate(R.id.action_registrationFragment_to_gallery_fragment)
        })

        binding.teName.doOnTextChanged { text, start, before, count ->
            viewModel.onEditName(text.toString())
            Log.d("editEmail", text.toString())
        }
        binding.teEmail.doOnTextChanged { text, start, before, count ->
            viewModel.onEditEmail(text.toString())
            Log.d("editEmail", text.toString())
        }
        binding.tePassword.doOnTextChanged { text, start, before, count ->
            viewModel.onEditPassword(text.toString())
            Log.d("editPassword", text.toString())
        }
        binding.btnRegister.setOnClickListener{
            viewModel.register()

        }
    }

}