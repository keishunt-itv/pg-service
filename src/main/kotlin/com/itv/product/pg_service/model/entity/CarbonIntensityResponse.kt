package com.itv.product.pg_service.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.itv.product.pg_service.model.enums.CarbonIntensityRegion

@JsonIgnoreProperties(ignoreUnknown = true)
data class CarbonIntensityResponse(
    val data: List<CarbonRegions>
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "data")
data class CarbonRegions(
    val regions: List<CarbonRegionData>
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "regions")
data class CarbonRegionData(
    @JsonProperty("shortname")
    val region: CarbonIntensityRegion,
    val intensity: Intensity,
    @JsonProperty("generationmix")
    val generationMix: List<GenerationMix>
)




