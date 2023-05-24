package com.example.pallete.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pallete.MAIN
import com.example.pallete.R
import com.example.pallete.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        viewModel.name.observe(viewLifecycleOwner, Observer {text ->
            binding.txtName.text = text
        })
        viewModel.description.observe(viewLifecycleOwner, Observer {text ->
            binding.txtDescription.text = text
        })
        viewModel.subscribers.observe(viewLifecycleOwner, Observer {text ->
            binding.txtSubsribersCount.text = text
        })
        viewModel.subscriptions.observe(viewLifecycleOwner, Observer {text ->
            binding.txtSubsCount.text = text
        })

        binding.btnEdit.setOnClickListener {
            MAIN.navController.navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
    }
}