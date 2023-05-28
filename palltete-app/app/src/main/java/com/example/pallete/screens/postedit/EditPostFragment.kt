package com.example.pallete.screens.postedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.setupWithNavController
import com.example.pallete.MAIN
import com.example.pallete.R
import com.example.pallete.databinding.FragmentEditPostBinding
import com.squareup.picasso.Picasso

class EditPostFragment : Fragment() {
    lateinit var binding: FragmentEditPostBinding
    private lateinit var viewModel: EditPostViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditPostBinding.inflate(layoutInflater, container, false)
        val toolbar = binding.toolbar.toolbarActionbar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        MAIN.navController.enableOnBackPressed(true)
        toolbar.setupWithNavController(MAIN.navController)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[EditPostViewModel::class.java]
        viewModel.post.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.imagePath).into(binding.imgPicture)
            binding.teName.setText(it.title ?: "")
            binding.teDescription.setText(it.description ?: "")
        })
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

        binding.btnEdit.setOnClickListener {
            viewModel.update(viewModel.title.value!!, viewModel.description.value, viewModel.topicId.value!!)
        }

        viewModel.successfulUpdate.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                MAIN.navController.navigate(R.id.action_editPostFragment_to_postDetailFragment)
            }
        })
    }
}