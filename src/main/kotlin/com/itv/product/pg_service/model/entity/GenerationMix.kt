package com.itv.product.pg_service.model.entity

import com.itv.product.pg_service.model.enums.CarbonIntensityFuelType

data class GenerationMix(
    val fuel : CarbonIntensityFuelType,
    val perc : Int
)
