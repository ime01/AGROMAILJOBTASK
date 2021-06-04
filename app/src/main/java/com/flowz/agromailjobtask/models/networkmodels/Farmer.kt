package com.flowz.agromailjobtask.models.networkmodels


import com.google.gson.annotations.SerializedName

data class Farmer(
    @SerializedName("farmer_id")
    val farmerId: String,
    @SerializedName("full_name")
    val fullName: String,
    val lga: String,
    @SerializedName("mobile_no")
    val mobileNo: String,
    @SerializedName("passport_photo")
    val passportPhoto: String,
    val state: String
)