package com.flowz.agromailjobtask.network

import com.flowz.agromailjobtask.models.networkmodels.FarmerResponse
import com.flowz.agromailjobtask.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceCalls {

    @GET(Constants.END_POINT)
    suspend fun getFarmers(@Query("limit") itemsnumber:Int, @Query("page") pagenumber:Int) : Response<FarmerResponse>

}