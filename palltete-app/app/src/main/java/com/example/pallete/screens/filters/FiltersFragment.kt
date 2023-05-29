package com.example.pallete.screens.filters

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
import com.example.pallete.databinding.FragmentEditIdeaBinding
import com.example.pallete.databinding.FragmentFiltersBinding


class FiltersFragment : Fragment() {
    lateinit var binding: FragmentFiltersBinding
    private lateinit var viewModel: FilterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFiltersBinding.inflate(layoutInflater, container, false)
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
        viewModel = ViewModelProvider(this)[FilterViewModel::class.java]

        viewModel.isSetted.observe(viewLifecycleOwner, Observer {
            MAIN.navController.navigate(R.id.action_filtersFragment_to_gallery_fragment)
        })
        binding.btnSumbit.setOnClickListener {
            if (binding.filterLandscape.isChecked)
                viewModel.filter(1)
            else if (binding.filterPortrait.isChecked)
                viewModel.filter(2)
            else if (binding.filterSample.isChecked)
                viewModel.filter(3)
            else if (binding.filterCharacter.isChecked)
                viewModel.filter(4)
            else if (binding.filterInterior.isChecked)
                viewModel.filter(5)
            else if (binding.filterItem.isChecked)
                viewModel.filter(6)
        }
    }

}