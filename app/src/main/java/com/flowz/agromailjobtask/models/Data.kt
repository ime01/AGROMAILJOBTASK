package com.flowz.agromailjobtask.models


import com.google.gson.annotations.SerializedName

data class Data(
    val farmers: List<Farmer>,
    val imgBaseUrl: String,
    val pagination: Pagination
)