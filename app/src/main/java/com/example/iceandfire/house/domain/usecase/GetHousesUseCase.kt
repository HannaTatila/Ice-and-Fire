package com.example.iceandfire.house.domain.usecase

import androidx.paging.PagingData
import com.example.iceandfire.house.domain.model.House
import com.example.iceandfire.house.domain.repository.HouseRepository
import kotlinx.coroutines.flow.Flow

class GetHousesUseCase(private val houseRepository: HouseRepository) {

    operator fun invoke(): Flow<PagingData<House>> {
        return houseRepository.getHouses()
    }
}