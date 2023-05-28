package com.example.pallete.screens.postdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.setupWithNavController
import com.example.pallete.MAIN
import com.example.pallete.R
import com.example.pallete.auth.AuthManager
import com.example.pallete.databinding.FragmentPostDetailBinding
import com.example.pallete.posts.PostManager
import com.squareup.picasso.Picasso

class PostDetailFragment : Fragment() {
    lateinit var binding: FragmentPostDetailBinding
    private lateinit var viewModel: PostDetailViewModel
    private lateinit var adapter: CommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostDetailBinding.inflate(layoutInflater, container, false)
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
        viewModel = ViewModelProvider(this)[PostDetailViewModel::class.java]
        adapter = CommentAdapter()
        binding.rvComments.adapter = adapter
        viewModel.post.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.imagePath).into(binding.imgPicture)
            binding.txtTitle.text = it.title
            binding.txtRate.text = it.rate.toString()
            binding.txtDescription.text= it.description
            if (it.authorId == AuthManager.getUser().userId) {
                binding.imgEdit.visibility = View.VISIBLE
            } else {
                binding.imgEdit.visibility = View.GONE
            }
        })
        viewModel.authorName.observe(viewLifecycleOwner, Observer {
            binding.txtAuthor.text = it
        })

        viewModel.comments.observe(viewLifecycleOwner, Observer {
            adapter.setPostsData(it)
        })
        viewModel.commentators.observe(viewLifecycleOwner, Observer {
            adapter.setUserData(it)
        })

        binding.imgEdit.setOnClickListener {
            PostManager.setPost(viewModel.post.value!!)
            MAIN.navController.navigate(R.id.action_postDetailFragment_to_editPostFragment)
        }
    }
}