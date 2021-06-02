package com.flowz.agromailjobtask.models


import com.google.gson.annotations.SerializedName

data class FarmerResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
)