package com.example.pallete.screens.ideaedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pallete.R
import com.example.pallete.databinding.FragmentEditIdeaBinding

class EditIdeaFragment : Fragment() {
    lateinit var binding: FragmentEditIdeaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditIdeaBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
}