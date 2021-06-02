package com.flowz.agromailjobtask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.flowz.agromailjobtask.paging.FarmerPagingSource
import com.flowz.agromailjobtask.ui.network.ApiServiceCalls
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FarmerViewModel @Inject constructor(private val apiService: ApiServiceCalls) : ViewModel() {

    val farmersDataFromNetwork = Pager(PagingConfig(pageSize = 1)){
        FarmerPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)

}