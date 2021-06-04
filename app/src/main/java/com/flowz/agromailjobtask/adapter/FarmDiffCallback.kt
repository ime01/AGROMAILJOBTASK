package com.flowz.agromailjobtask.adapter

import androidx.recyclerview.widget.DiffUtil
import com.flowz.agromailjobtask.models.roomdbmodels.Farm


class FarmDiffCallback : DiffUtil.ItemCallback<Farm>(){
    override fun areItemsTheSame(oldItem: Farm, newItem: Farm): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Farm, newItem: Farm): Boolean {
        return oldItem == newItem
    }
}