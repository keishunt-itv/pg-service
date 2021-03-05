package com.itv.product.pg_service.repositories.daos

import org.jdbi.v3.core.mapper.reflect.ColumnName

data class RegionFuelDao(
    @ColumnName("id")
    val id : Int,
    @ColumnName("region_id")
    val regionId : Int,
    @ColumnName("fuel")
    val fuel : String,
    @ColumnName("percentage")
    val percentage: Int,
)


