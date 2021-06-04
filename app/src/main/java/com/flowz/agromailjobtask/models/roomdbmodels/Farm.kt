package com.flowz.agromailjobtask.models.roomdbmodels

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "farm_table")
data class Farm(
    val farmName: String? = null,
    val farmOwner: String? = null,
    val farmLocation: String? = null,
    val farmCoordinatesLat: Double? = null,
    val farmCoordinatesLng: Double? = null,

): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
