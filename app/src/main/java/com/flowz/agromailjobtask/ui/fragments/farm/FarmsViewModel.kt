package com.flowz.agromailjobtask.ui.fragments.farm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.flowz.agromailjobtask.models.roomdbmodels.Farm
import com.flowz.agromailjobtask.models.roomdbmodels.RdbFarmer
import com.flowz.agromailjobtask.network.ApiServiceCalls
import com.flowz.agromailjobtask.paging.FarmerPagingSource
import com.flowz.agromailjobtask.repository.FarmRepository
import com.flowz.agromailjobtask.repository.FarmersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel()
class FarmsViewModel @Inject constructor(private val farmRepository: FarmRepository): ViewModel() {

    val farmsFromDb = farmRepository.farmsFromDb

    fun insertFarm(farm: Farm){
        viewModelScope.launch (Dispatchers.IO){
            farmRepository.insertFarmIntoDb(farm)
        }
    }

    fun insertFarms(farms: List<Farm>){
        viewModelScope.launch (Dispatchers.IO){
            farmRepository.insertListOfFarmsIntoDb(farms)
        }
    }

    fun updateFarm(farm: Farm){
        viewModelScope.launch (Dispatchers.IO){
            farmRepository.updateFarmInDb(farm)
        }
    }

    fun deleteFarm(farm: Farm){
        viewModelScope.launch (Dispatchers.IO){
          farmRepository.deleteFarmInDb(farm)
        }
    }

    fun searchFarmer(searchQuery: String): LiveData<List<Farm>> {
        return farmRepository.searchFarm(searchQuery).asLiveData()
    }





}