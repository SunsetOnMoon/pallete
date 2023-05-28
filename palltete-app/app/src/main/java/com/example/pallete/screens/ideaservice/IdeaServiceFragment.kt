package com.example.pallete.screens.ideaservice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pallete.MAIN
import com.example.pallete.R
import com.example.pallete.databinding.FragmentIdeaServiceBinding

class IdeaServiceFragment : Fragment() {
    lateinit var binding: FragmentIdeaServiceBinding
    private lateinit var viewModel: IdeaServiceViewModel
    private lateinit var adapter: IdeaAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val manager = LinearLayoutManager(activity)
//        binding.rvIdeas.layoutManager = manager
        adapter = IdeaAdapter()
        binding.rvIdeas.adapter = adapter
//        Log.d("Adapter", adapter.data.toString())
        viewModel = ViewModelProvider(this)[IdeaServiceViewModel::class.java]
        viewModel.ideas.observe(viewLifecycleOwner, Observer {
            adapter.setIdeasData(it)
            Log.d("Adapter", adapter.data.toString())
        })

        binding.btnCreate.setOnClickListener {
            MAIN.navController.navigate(R.id.action_idea_service_fragment_to_editIdeaFragment)
        }
    }

}