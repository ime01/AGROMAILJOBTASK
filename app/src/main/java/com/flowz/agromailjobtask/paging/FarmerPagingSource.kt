package com.flowz.agromailjobtask.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.flowz.agromailjobtask.models.networkmodels.Farmer
import com.flowz.agromailjobtask.network.ApiServiceCalls

class FarmerPagingSource(private val apiService: ApiServiceCalls): PagingSource<Int, Farmer>() {

    override fun getRefreshKey(state: PagingState<Int, Farmer>): Int? {
      return null
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Farmer> {
        return try {

            val itemLimit = 10
            val numberOfPages = 20
            val currentPage = params.key ?:1

            val response = apiService.getFarmers(itemLimit, numberOfPages)
//            Log.e(TAG, "$response")

            val data = response.body()?.data?.farmers?: emptyList()

            val responseData = mutableListOf<Farmer>()
            responseData.addAll(data)

            Log.e(TAG, "$responseData")

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage==1) null else -1,
                nextKey = currentPage.plus(1)
            )

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }


    companion object{
        const val TAG = "Paging Source"
    }

}

