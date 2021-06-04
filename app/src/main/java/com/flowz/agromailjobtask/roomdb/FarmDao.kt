package com.flowz.agromailjobtask.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flowz.agromailjobtask.models.roomdbmodels.Farm
import com.flowz.agromailjobtask.models.roomdbmodels.RdbFarmer
import com.flowz.agromailjobtask.models.roomdbmodels.UriConverters
import kotlinx.coroutines.flow.Flow

@Dao
interface FarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarm(farm: Farm)

    @Delete
    suspend fun deleteFarm(farm: Farm)

    @Update
    suspend fun updateFarm(farm: Farm)

    @TypeConverters(UriConverters::class)
    @Query("SELECT * FROM farm_table ")
    fun getAllFarms(): LiveData<List<Farm>>

    @TypeConverters(UriConverters::class)
    @Query("SELECT * FROM farm_table  WHERE farmName LIKE :searchQuery")
    fun searchFarm(searchQuery: String): Flow<List<Farm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarmList(farms: List<Farm>)

    @Query("DELETE FROM farm_table")
    suspend fun deleteAllFarms()
}