package com.example.iceandfire.house.data.mapper

import com.example.iceandfire.house.data.model.HouseResponse
import com.example.iceandfire.house.domain.model.House

class HouseMapper {

    fun toHouse(source: HouseResponse): House {
        return House(
            coatOfArms = source.coatOfArms.orEmpty(),
            name = source.name.orEmpty(),
            region = source.region.orEmpty(),
            words = source.words.orEmpty(),
            url = source.url.orEmpty()
        )
    }
}