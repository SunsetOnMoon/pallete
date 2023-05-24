package com.example.pallete.screens.ideaedit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pallete.R
import com.example.pallete.databinding.FragmentEditIdeaBinding
import com.example.pallete.screens.login.LoginViewModel

class EditIdeaFragment : Fragment() {
    lateinit var binding: FragmentEditIdeaBinding
    private lateinit var viewModel: IdeaEditViewModel
    private lateinit var adapter: ColorAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = GridLayoutManager(activity, 8)
        binding.rvColors.layoutManager = manager
        adapter = ColorAdapter()
        binding.rvColors.adapter = adapter
        viewModel = ViewModelProvider(this)[IdeaEditViewModel::class.java]

        viewModel.title.observe(viewLifecycleOwner, Observer {
            binding.teName.setText(it)
        })

        viewModel.description.observe(viewLifecycleOwner, Observer {
            binding.teDescription.setText(it)
        })

        viewModel.colors.observe(viewLifecycleOwner, Observer {
            adapter.setColorsData(it)
            Log.d("Adapter", it.toString())
        })
        binding.btnGenerate.setOnClickListener {
            viewModel.generate()
        }
    }
}