package com.android.figma_task.figma_screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.figma_task.databinding.ItemFamousBinding
import kotlin.collections.ArrayList

class Adapter(private val famousList: ArrayList<Famous>) :
    RecyclerView.Adapter<Adapter.FamousHolder>() {

    inner class FamousHolder(val binding: ItemFamousBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Famous) {
            binding.apply {
                binding.famousName.setText(model.name)
                binding.characterName.setText(model.caracterName)
                binding.image.setImageResource(model.imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamousHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFamousBinding.inflate(layoutInflater,parent, false)
        return FamousHolder(binding)
    }

    override fun onBindViewHolder(holder: FamousHolder, position: Int) {
        holder.bind(famousList[position])
    }

    override fun getItemCount() = famousList.size
}