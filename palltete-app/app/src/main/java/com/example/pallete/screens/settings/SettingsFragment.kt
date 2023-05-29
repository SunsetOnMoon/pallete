package com.example.pallete.screens.settings

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pallete.R
import com.example.pallete.databinding.FragmentSettingsBinding
import com.example.pallete.resource.Res
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private var selectedColor: Int = Color.BLACK
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        selectedColor = resources.getColor(R.color.text_green)

        binding.btnChoose.setOnClickListener {
            showColorPickerDialog()
        }

        return view
    }

    private fun showColorPickerDialog() {
        val colorPickerDialog = ColorPickerDialogBuilder
            .with(requireContext())
            .setTitle("Select theme color")
            .initialColor(selectedColor)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton("OK"){_, selectedColor, _ ->
                this.selectedColor = selectedColor
                updateColor()
            }
            .setNegativeButton("Cancel"){dialog, _ ->
                dialog.dismiss()
            }
            .build()
            .show()
    }

    private fun updateColor() {
        binding.btnChoose.setBackgroundColor(selectedColor)
        val res = Res(super.getResources(), selectedColor)
        Log.d("Color", selectedColor.toString())
    }

}