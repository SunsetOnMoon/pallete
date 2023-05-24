package com.example.pallete.screens.ideaedit

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pallete.databinding.ListitemColorBinding

class ColorAdapter: RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    var colors: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListitemColorBinding.inflate(inflater, parent, false)

        return ColorViewHolder(binding)
    }

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]
        val context = holder.itemView.context

        with (holder.binding) {
//            imgColor.setBackgroundColor(Color.parseColor(color))
            imgColor.background.setTint(Color.parseColor(color))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setColorsData(colors: List<String>) {
        this.colors = colors
        notifyDataSetChanged()
    }

    class ColorViewHolder(val binding: ListitemColorBinding): RecyclerView.ViewHolder(binding.root)
}