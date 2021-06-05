package com.flowz.agromailjobtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.databinding.FarmerItemBinding
import com.flowz.agromailjobtask.models.networkmodels.Farmer
import com.flowz.agromailjobtask.models.roomdbmodels.RdbFarmer


class LocalAdapter  (val listener: RowClickListener)  : ListAdapter<RdbFarmer, LocalAdapter.LocalViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalAdapter.LocalViewHolder {
        return LocalViewHolder(FarmerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                farmerName.text = "${currentItem?.fullName}"
                phoneNumber.text = "${currentItem?.state}"
                farmerState.text = "${currentItem?.lga}"

                val imageurl = currentItem?.passportPhoto

                Glide.with(farmerPassportPhoto)
                    .load(imageurl)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_person_24)
                    .fallback(R.drawable.ic_baseline_person_24)
                    .into(farmerPassportPhoto)
            }
        }
    }

    interface RowClickListener{
        fun onEditClickListener(farmer: RdbFarmer)
        fun onDeleteClickListener(farmer: RdbFarmer)
    }


    inner class LocalViewHolder(val binding: FarmerItemBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.editFarmer.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                listener.onEditClickListener(item)

            }
            binding.deleteFarmer.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                listener.onDeleteClickListener(item)
            }
        }
    }

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<RdbFarmer>(){
            override fun areItemsTheSame(oldItem: RdbFarmer, newItem: RdbFarmer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RdbFarmer, newItem: RdbFarmer): Boolean {
                return oldItem == newItem
            }

        }
    }


}
