package com.example.pallete.screens.profileedit

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
import com.example.pallete.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    lateinit var binding: FragmentEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]

        viewModel.updatedUser.observe(viewLifecycleOwner, Observer {
            MAIN.navController.navigate(R.id.action_editProfileFragment_to_profile_fragment)
        })

        binding.teName.doOnTextChanged { text, start, before, count ->
            viewModel.onEditName(text.toString())
        }
        binding.teDescription.doOnTextChanged { text, start, before, count ->
            viewModel.onEditDescription(text.toString())
        }
        binding.btnEdit.setOnClickListener {
            viewModel.edit()
        }

    }
}