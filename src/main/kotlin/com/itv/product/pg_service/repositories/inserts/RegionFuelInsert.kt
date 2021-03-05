package com.itv.product.pg_service.repositories.inserts

import com.itv.product.pg_service.model.entity.GenerationMix

data class RegionFuelInsert(
    val fuel: String,
    val percentage: Int
)

fun GenerationMix.toRegionFuelInsert() =
    RegionFuelInsert(
        fuel = this.fuel.name,
        percentage = this.percentage
    )
