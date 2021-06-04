package com.flowz.agromailjobtask.repository

import com.flowz.agromailjobtask.models.roomdbmodels.RdbFarmer
import com.flowz.agromailjobtask.roomdb.FarmerDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FarmersRepository @Inject constructor(private val dao: FarmerDao) {

    val farmersFromDb = dao.getAllFarmers()

    fun searchFarmers(searchQuery: String): Flow<List<RdbFarmer>> {
        return dao.searchFarmer(searchQuery)
    }

    suspend fun insertFarmerIntoDb(farmer: RdbFarmer){
        dao.insertFarmer(farmer)
    }

    suspend fun insertListOfFarmersIntoDb(farmers: List<RdbFarmer>){
        dao.insertFarmersList(farmers)
    }

    suspend fun updateFarmerInDb(farmer: RdbFarmer){
        dao.updateFarmer(farmer)
    }

    suspend fun deleteFarmerInDb(farmer: RdbFarmer){
        dao.deleteFarmer(farmer)
    }

    suspend fun clearAllFarmersInDb(){
        dao.deleteAllFarmers()
    }


}