package com.example.iceandfire.house.domain.repository

import androidx.paging.PagingData
import com.example.iceandfire.house.domain.model.House
import kotlinx.coroutines.flow.Flow

interface HouseRepository {

    fun getHouses(): Flow<PagingData<House>>
}