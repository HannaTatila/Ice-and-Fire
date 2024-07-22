package com.example.iceandfire.house.data.service

import com.example.iceandfire.house.data.model.HouseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HouseService {

    @GET("houses")
    suspend fun getHousesPaging(
        @Query("page") page: String,
        @Query("pageSize") pageSize: String
    ): List<HouseResponse>
}