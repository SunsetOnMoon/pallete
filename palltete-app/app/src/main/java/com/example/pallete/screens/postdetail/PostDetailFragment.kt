package com.example.pallete.screens.postdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pallete.R
import com.example.pallete.databinding.FragmentPostDetailBinding

class PostDetailFragment : Fragment() {
    lateinit var binding: FragmentPostDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}