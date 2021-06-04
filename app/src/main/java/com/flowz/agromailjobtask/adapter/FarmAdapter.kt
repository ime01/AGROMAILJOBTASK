package com.flowz.agromailjobtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.databinding.FarmItemBinding
import com.flowz.agromailjobtask.models.roomdbmodels.Farm


class FarmAdapter  (val listener: RowClickListener)  :ListAdapter<Farm, FarmAdapter.FarmViewHolder>(FarmDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  FarmViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.farm_item, parent, false)

        return FarmViewHolder(FarmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: FarmViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                farmName.text = "Farm Name: ${currentItem.farmName}"
                farmOwner.text = "Farm Owner: ${currentItem.farmOwner}"
                farmLocation.text = "Farm Location: ${currentItem.farmLocation}"
            }
        }
    }

    inner class FarmViewHolder(val binding: FarmItemBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                listener.onItemClickListener(item)
            }
        }
    }


    interface RowClickListener{
        fun onItemClickListener(farm: Farm)

    }

}