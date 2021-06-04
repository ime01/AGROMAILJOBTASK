package com.flowz.agromailjobtask.repository

import com.flowz.agromailjobtask.models.roomdbmodels.Farm
import com.flowz.agromailjobtask.models.roomdbmodels.RdbFarmer
import com.flowz.agromailjobtask.roomdb.FarmDao
import com.flowz.agromailjobtask.roomdb.FarmerDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FarmRepository @Inject constructor(private val dao: FarmDao) {

    val farmsFromDb = dao.getAllFarms()

    fun searchFarm(searchQuery: String): Flow<List<Farm>> {
        return dao.searchFarm(searchQuery)
    }

    suspend fun insertFarmIntoDb(farm: Farm){
        dao.insertFarm(farm)
    }

    suspend fun insertListOfFarmsIntoDb(farms: List<Farm>){
        dao.insertFarmList(farms)
    }

    suspend fun updateFarmInDb(farm: Farm){
        dao.updateFarm(farm)
    }

    suspend fun deleteFarmInDb(farm: Farm){
        dao.deleteFarm(farm)
    }

    suspend fun clearAllFarmsInDb(){
        dao.deleteAllFarms()
    }


}