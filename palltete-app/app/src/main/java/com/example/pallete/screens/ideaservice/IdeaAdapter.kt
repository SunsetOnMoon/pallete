package com.example.pallete.screens.ideaservice

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.pallete.databinding.ListitemIdeaBinding
import com.example.pallete.models.idea.Idea

class IdeaAdapter: RecyclerView.Adapter<IdeaAdapter.IdeaViewHolder>() {

    var data: List<Idea> = emptyList()
    val ideaForDelete: MutableLiveData<Idea> = MutableLiveData()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListitemIdeaBinding.inflate(inflater, parent, false)

        return IdeaViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: IdeaViewHolder, position: Int) {
        val idea = data[position]
        val context = holder.itemView.context

        with(holder.binding) {
            txtName.text = idea.name

            btnClose.setOnClickListener {
                ideaForDelete.value = idea
            }
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun setIdeasData(ideas: List<Idea>) {
        this.data = ideas
        notifyDataSetChanged()
    }
    class IdeaViewHolder(val binding: ListitemIdeaBinding) : RecyclerView.ViewHolder(binding.root)
}