package com.example.pallete.screens.feed

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pallete.R
import com.example.pallete.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: FeedViewModel
    private lateinit var adapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(layoutInflater, container, false)
        val toolbar = binding.toolbar.toolbarActionbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(activity)
        binding.rvPosts.layoutManager = manager
        adapter = PostAdapter()
        binding.rvPosts.adapter = adapter
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            adapter.setPostsData(it)
            Log.d("Adapter", adapter.data.toString())
        })
        viewModel.users.observe(viewLifecycleOwner, Observer {
            adapter.setUserData(it)

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_activity_main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as android.widget.SearchView

        searchView.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("Searching", newText ?: "")
                viewModel.search(newText)
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

}