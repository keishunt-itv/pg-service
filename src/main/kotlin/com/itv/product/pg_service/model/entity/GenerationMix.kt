package com.itv.product.pg_service.model.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.itv.product.pg_service.model.enums.CarbonIntensityFuelType

data class GenerationMix(
    val fuel: CarbonIntensityFuelType,
    @JsonProperty("perc")
    val percentage: Int
)
