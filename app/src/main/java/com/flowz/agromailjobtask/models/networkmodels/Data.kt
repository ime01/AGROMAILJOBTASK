package com.flowz.agromailjobtask.models.networkmodels


data class Data(
    val farmers: List<Farmer>,
    val imgBaseUrl: String,
    val pagination: Pagination
)