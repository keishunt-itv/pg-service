package com.itv.product.pg_service.repositories.daos

import com.itv.product.pg_service.model.entity.CarbonRegionData
import org.jdbi.v3.core.mapper.reflect.ColumnName

data class CarbonIntensityRegionDao(
    @ColumnName("id")
    val id: Int,
    @ColumnName("region")
    val region: String,
    @ColumnName("forecast")
    val forecast: Int,
    @ColumnName("index_rating")
    val indexRating: String,
)

