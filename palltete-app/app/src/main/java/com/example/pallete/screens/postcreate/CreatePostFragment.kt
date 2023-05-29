package com.example.pallete.screens.postcreate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.setupWithNavController
import com.example.pallete.MAIN
import com.example.pallete.R
import com.example.pallete.databinding.FragmentCreatePostBinding
import com.example.pallete.databinding.FragmentEditPostBinding

class CreatePostFragment : Fragment() {
    lateinit var binding: FragmentCreatePostBinding
    private lateinit var viewModel: CreatePostViewModel

    private val REQUEST_IMAGE_PICK = 123
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatePostBinding.inflate(layoutInflater, container, false)
        val toolbar = binding.toolbar.toolbarActionbar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        MAIN.navController.enableOnBackPressed(true)
        toolbar.setupWithNavController(MAIN.navController)
        binding.imgPicture.alpha = 0.5F
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreatePostViewModel::class.java]

        viewModel.topics.observe(viewLifecycleOwner, Observer {
            val topicsName: List<String> = viewModel.topics.value?.map { it.name }!!
            binding.atvTopic.setAdapter(ArrayAdapter(requireContext(), R.layout.listitem_dropdown, topicsName))
//            binding.atvTopic.setSelection(viewModel.post.value?.topicId!!)
        })

        binding.teName.doOnTextChanged { text, start, before, count ->
            viewModel.onEditTitle(text.toString())
        }
        binding.teDescription.doOnTextChanged { text, start, before, count ->
            viewModel.onEditDescription(text.toString())
        }
        binding.atvTopic.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            val topic = viewModel.topics.value?.find { it.name == selectedItem }
            viewModel.onChooseTopic(topic?.topicId!!)
        }

        binding.btnChoose.setOnClickListener {
            openGallery()
        }
        binding.btnCreate.setOnClickListener {
            MAIN.navController.navigate(R.id.action_createPostFragment_to_profile_fragment)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            binding.imgPicture.setImageURI(selectedImageUri)
            binding.imgPicture.alpha = 1F
        }
    }

}