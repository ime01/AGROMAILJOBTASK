package com.flowz.agromailjobtask.di

import android.content.Context
import androidx.room.Room
import com.flowz.agromailjobtask.network.ApiServiceCalls
import com.flowz.agromailjobtask.roomdb.FarmersDatabase
import com.flowz.agromailjobtask.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun providesRetrofitInstance(BASE_URL: String): ApiServiceCalls =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceCalls::class.java)


    @Provides
    @Singleton
    fun providesFarmersDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, FarmersDatabase::class.java, "farmers.db").build()

    @Provides
    @Singleton
    fun providesFarmersDao (db: FarmersDatabase) = db.farmersDao()



}