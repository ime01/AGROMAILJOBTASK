package com.flowz.agromailjobtask.ui.fragments.farmers

import androidx.core.net.toUri
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.flowz.agromailjobtask.models.networkmodels.Farmer
import com.flowz.agromailjobtask.models.roomdbmodels.Farm
import com.flowz.agromailjobtask.models.roomdbmodels.RdbFarmer
import com.flowz.agromailjobtask.network.ApiServiceCalls
import com.flowz.agromailjobtask.paging.FarmerPagingSource
import com.flowz.agromailjobtask.repository.FarmersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel()
class FarmersViewModel @Inject constructor(private val apiService: ApiServiceCalls, private val farmersRepository: FarmersRepository): ViewModel() {

    val farmersFromDb = farmersRepository.farmersFromDb


    val farmersDataFromNetwork = Pager(PagingConfig(pageSize = 1)){
        FarmerPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)


    fun insertFarmer(farmer: RdbFarmer){
        viewModelScope.launch (Dispatchers.IO){
            farmersRepository.insertFarmerIntoDb(farmer)
        }
    }

    fun insertFarmers(farmers: List<RdbFarmer>){
        viewModelScope.launch (Dispatchers.IO){
            farmersRepository.insertListOfFarmersIntoDb(farmers)
        }
    }

    fun updateFarmer(farmer: RdbFarmer){
        viewModelScope.launch (Dispatchers.IO){
            farmersRepository.updateFarmerInDb(farmer)
        }
    }

    fun deleteFarmer(farmer: RdbFarmer){
        viewModelScope.launch (Dispatchers.IO){
            farmersRepository.deleteFarmerInDb(farmer)
        }
    }

    fun searchFarmer(searchQuery: String): LiveData<List<RdbFarmer>> {
        return farmersRepository.searchFarmers(searchQuery).asLiveData()
    }





}