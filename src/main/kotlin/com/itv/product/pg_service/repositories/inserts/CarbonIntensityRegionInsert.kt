package com.itv.product.pg_service.repositories.inserts

import com.itv.product.pg_service.model.entity.CarbonRegionData

data class CarbonIntensityRegionInsert(
    val region: String,
    val forecast: Int,
    val indexRating: String,
)


fun CarbonRegionData.toCarbonIntensityRegionInsert() =
    CarbonIntensityRegionInsert(
        region = this.region.name,
        forecast = this.intensity.forecast,
        indexRating = this.intensity.index.name,
    )
