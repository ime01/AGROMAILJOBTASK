package com.flowz.agromailjobtask.models.roomdbmodels

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "farmer_table")
data class RdbFarmer (
    val farmerId: String? = null,
    val fullName: String? = null,
    val lga: String? = null,
    val passportPhoto: Uri? = null,
    val state: String? = null,
): Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

class UriConverters {
    @TypeConverter
    fun fromString(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        return uri.toString()
    }

}