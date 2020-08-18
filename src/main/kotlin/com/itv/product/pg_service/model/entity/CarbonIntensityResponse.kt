package com.itv.product.pg_service.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.itv.product.pg_service.model.enums.CarbonIntensityRegion

@JsonIgnoreProperties(ignoreUnknown = true)
data class CarbonIntensityResponse(
    val shortname : CarbonIntensityRegion,
    val intensity : Intensity,
    val generationmix : List<GenerationMix>
)
