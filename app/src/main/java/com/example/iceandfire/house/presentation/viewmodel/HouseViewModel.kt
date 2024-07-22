package com.example.iceandfire.house.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.iceandfire.house.domain.model.House
import com.example.iceandfire.house.domain.usecase.GetHousesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class HouseViewModel(
    private val getHousesUseCase: GetHousesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun getHouses(): Flow<PagingData<House>> {
        return getHousesUseCase()
            .flowOn(dispatcher)
            .cachedIn(viewModelScope)
    }

    fun onHouseItemClicked(url: String) {
        // Will be implemented
    }
}