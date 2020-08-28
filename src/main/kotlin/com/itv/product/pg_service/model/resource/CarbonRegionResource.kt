package com.itv.product.pg_service.model.resource

import com.itv.product.pg_service.model.entity.CarbonRegionData
import com.itv.product.pg_service.model.entity.CarbonRegions
import com.itv.product.pg_service.model.entity.GenerationMix
import com.itv.product.pg_service.model.entity.Intensity
import com.itv.product.pg_service.model.enums.CarbonIntensityRegion

data class CarbonRegionResource(
    val region: CarbonIntensityRegion,
    val intensity: Intensity,
    val generationMix: List<GenerationMix>
)

data class CarbonRegionListResource(
    val regions: List<CarbonRegionResource>
)

fun toCarbonRegionDataResource(carbonRegionData: CarbonRegionData): CarbonRegionResource {
    return CarbonRegionResource(
        region = carbonRegionData.region,
        intensity = carbonRegionData.intensity,
        generationMix = carbonRegionData.generationMix
    )
}

fun toCarbonRegionListResource(carbonRegions: CarbonRegions): CarbonRegionListResource {
    val mappedRegions = carbonRegions.regions.map { toCarbonRegionDataResource(it) }
    return CarbonRegionListResource(
        regions = mappedRegions
    )
}
