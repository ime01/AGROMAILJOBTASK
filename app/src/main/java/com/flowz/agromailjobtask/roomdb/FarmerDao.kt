package com.flowz.agromailjobtask.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flowz.agromailjobtask.models.roomdbmodels.RdbFarmer
import com.flowz.agromailjobtask.models.roomdbmodels.UriConverters
import kotlinx.coroutines.flow.Flow

@Dao
interface FarmerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarmer(farmer: RdbFarmer)

    @Delete
    suspend fun deleteFarmer(farmer: RdbFarmer)

    @Update
    suspend fun updateFarmer(farmer: RdbFarmer)

    @TypeConverters(UriConverters::class)
    @Query("SELECT * FROM farmer_table ")
    fun getAllFarmers(): LiveData<List<RdbFarmer>>

    @TypeConverters(UriConverters::class)
    @Query("SELECT * FROM farmer_table  WHERE fullName LIKE :searchQuery")
    fun searchFarmer(searchQuery: String): Flow<List<RdbFarmer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFarmersList(farmers: List<RdbFarmer>)

    @Query("DELETE FROM farmer_table")
    suspend fun deleteAllFarmers()
}