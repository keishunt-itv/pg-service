package com.itv.product.pg_service.repositories.daos

import com.itv.product.pg_service.model.entity.CarbonRegionData
import org.jdbi.v3.core.mapper.reflect.ColumnName

data class CarbonIntensityRegionDao(
    @ColumnName("region")
    val region : String,
    @ColumnName("forecast")
    val forecast : Int,
    @ColumnName("index_rating")
    val indexRating : String,
    @ColumnName("fuel")
    val fuel : String,
    @ColumnName("percentage")
    val percentage : Int
)

data class CarbonIntensityRegionInsert(
    val region : String,
    val forecast : Int,
    val indexRating : String,
    val fuel : String,
    val percentage : Int
)


fun CarbonRegionData.toCarbonIntensityRegionInsert() =
    CarbonIntensityRegionInsert(
        region = this.region.name,
        forecast = this.intensity.forecast,
        indexRating = this.intensity.index.name,
        fuel = this.generationMix[0].fuel.name,
        percentage = this.generationMix[0].percentage
    )
