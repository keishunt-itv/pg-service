package com.itv.product.pg_service.model.entity

import com.itv.product.pg_service.model.enums.CarbonIntensityIndexRating

data class Intensity (
    val forecast : Int,
    val index : CarbonIntensityIndexRating
)
