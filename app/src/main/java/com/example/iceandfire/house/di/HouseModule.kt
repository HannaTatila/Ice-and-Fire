package com.example.iceandfire.house.di

import com.example.iceandfire.core.network.IceAndFireApiService
import com.example.iceandfire.house.data.datasource.HousePagingSource
import com.example.iceandfire.house.data.mapper.HouseMapper
import com.example.iceandfire.house.data.repository.HouseRepositoryImpl
import com.example.iceandfire.house.data.service.HouseService
import com.example.iceandfire.house.domain.repository.HouseRepository
import com.example.iceandfire.house.domain.usecase.GetHousesUseCase
import com.example.iceandfire.house.presentation.viewmodel.HouseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val houseModule = module {
    viewModel {
        HouseViewModel(
            getHousesUseCase = GetHousesUseCase(get())
        )
    }

    factory<HouseRepository> {
        HouseRepositoryImpl(
            housePagingSource = HousePagingSource(
                houseService = IceAndFireApiService.buildService(HouseService::class.java),
                houseMapper = HouseMapper()
            )
        )
    }
}
