package com.flowz.agromailjobtask.models.networkmodels


data class Pagination(
    val currentPage: Int,
    val next: Int,
    val perPage: Int,
    val previous: Int,
    val total: Int,
    val totalPages: Int
)