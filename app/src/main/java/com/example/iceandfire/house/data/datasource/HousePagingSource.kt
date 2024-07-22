package com.example.iceandfire.house.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.iceandfire.house.data.mapper.HouseMapper
import com.example.iceandfire.house.data.service.HouseService
import com.example.iceandfire.house.domain.model.House

private const val PAGE_SIZE_DEFAULT = "15"

class HousePagingSource(
    private val houseService: HouseService,
    private val houseMapper: HouseMapper
) : PagingSource<Int, House>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, House> {
        return try {
            val nextPage = params.key ?: 1
            val response = houseService.getHousesPaging(nextPage.toString(), PAGE_SIZE_DEFAULT)
                .map { houseResponse -> houseMapper.toHouse(houseResponse) }
            LoadResult.Page(
                data = response,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, House>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}