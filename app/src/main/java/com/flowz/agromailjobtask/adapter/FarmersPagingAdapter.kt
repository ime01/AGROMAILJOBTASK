package com.flowz.agromailjobtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.flowz.agromailjobtask.R
import com.flowz.agromailjobtask.databinding.FarmerItemBinding
import com.flowz.agromailjobtask.models.Farmer

class FarmersPagingAdapter(private val listener: RowClickListener ): PagingDataAdapter<Farmer, FarmersPagingAdapter.MyViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(FarmerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.binding.apply {

            val imageLink = currentItem?.passportPhoto

            holder.itemView.apply {
                farmerName.text = "${currentItem?.fullName}"
                phoneNumber.text = "${currentItem?.mobileNo}"
                farmerState.text = "${currentItem?.state}"
                farmerLga.text = "${currentItem?.lga}"

//                farmerPassportPhoto.load(imageLink){
//                    error(R.drawable.ic_baseline_error_outline_24)
//                    placeholder(R.drawable.ic_baseline_person_24)
//                    crossfade(true)
//                    crossfade(1000)
//            }
                    Glide.with(farmerPassportPhoto)
                        .load(imageLink)
                        .circleCrop()
                        .placeholder(R.drawable.ic_baseline_person_24)
                        .error(R.drawable.ic_baseline_error_outline_24)
                        .fallback(R.drawable.ic_baseline_person_24)
                        .into(farmerPassportPhoto)

            }


        }


    }

    interface RowClickListener{
        fun onEditClickListener(farmer: Farmer)
        fun onDeleteClickListener(farmer: Farmer)
    }


    inner class MyViewHolder(val binding: FarmerItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.editFarmer.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                listener.onEditClickListener(item!!)
            }
            binding.deleteFarmer.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                listener.onDeleteClickListener(item!!)
            }
        }
    }


    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Farmer>(){
            override fun areItemsTheSame(oldItem: Farmer, newItem: Farmer): Boolean {
                return oldItem.farmerId == newItem.farmerId
            }

            override fun areContentsTheSame(oldItem: Farmer, newItem: Farmer): Boolean {
                return oldItem == newItem
            }

        }
    }

}