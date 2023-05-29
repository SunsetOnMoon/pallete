package com.example.pallete.screens.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.pallete.MAIN
import com.example.pallete.R
import com.example.pallete.databinding.FragmentProfileBinding
import com.example.pallete.itemdecoration.GridDividerItemDecoration

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var adapter: PostAdapter
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
        adapter = PostAdapter()
        binding.rvPictures.adapter = adapter
        binding.rvPictures.addItemDecoration(GridDividerItemDecoration(2, R.color.background_dark))

        viewModel.posts.observe(viewLifecycleOwner, Observer {
            adapter.setPostsData(it)
            Log.d("Adapter", adapter.data.toString())
        })
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

        binding.btnCreate.setOnClickListener {
            MAIN.navController.navigate(R.id.action_profile_fragment_to_createPostFragment)
        }
    }
}