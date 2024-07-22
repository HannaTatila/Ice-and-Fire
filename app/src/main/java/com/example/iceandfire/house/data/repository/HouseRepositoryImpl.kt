package com.example.iceandfire.house.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.iceandfire.house.data.datasource.HousePagingSource
import com.example.iceandfire.house.domain.model.House
import com.example.iceandfire.house.domain.repository.HouseRepository
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE_DEFAULT = 15

class HouseRepositoryImpl(
    private val housePagingSource: HousePagingSource
) : HouseRepository {

    override fun getHouses(): Flow<PagingData<House>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE_DEFAULT, enablePlaceholders = false),
            pagingSourceFactory = { housePagingSource }
        ).flow
    }
}
