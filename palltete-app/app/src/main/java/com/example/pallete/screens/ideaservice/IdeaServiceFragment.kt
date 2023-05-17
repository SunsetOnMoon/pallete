package com.example.pallete.screens.ideaservice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pallete.R
import com.example.pallete.databinding.FragmentIdeaServiceBinding

class IdeaServiceFragment : Fragment() {
    lateinit var binding: FragmentIdeaServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIdeaServiceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}