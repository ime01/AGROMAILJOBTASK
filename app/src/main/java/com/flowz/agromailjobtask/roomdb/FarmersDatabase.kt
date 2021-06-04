package com.flowz.agromailjobtask.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flowz.agromailjobtask.models.roomdbmodels.Farm
import com.flowz.agromailjobtask.models.roomdbmodels.RdbFarmer
import com.flowz.agromailjobtask.models.roomdbmodels.UriConverters


@Database(entities = [RdbFarmer::class, Farm::class], version = 1, exportSchema = false)
@TypeConverters(UriConverters::class)
abstract class FarmersDatabase : RoomDatabase(){

    abstract fun farmersDao(): FarmerDao
    abstract fun farmDao(): FarmDao
}