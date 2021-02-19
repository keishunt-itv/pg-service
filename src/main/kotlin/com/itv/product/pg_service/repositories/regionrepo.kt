package com.itv.product.pg_service.repositories

import com.itv.product.pg_service.config.DatabaseConfig
import com.itv.product.pg_service.repositories.daos.CarbonIntensityRegionInsert
import org.jdbi.v3.core.Handle

object RegionRepository {
        fun addRegionToDatabase(carbonIntensityRegionInsert: CarbonIntensityRegionInsert): Int {
            return DatabaseConfig.jdbi().inTransaction<Int, Exception> { handle ->

                val insertStatement =
                    """INSERT INTO project_green.regional_data(region, forecast, fuel, index_rating, percentage)
                                   VALUES(:region,:forecast,:fuel,:index_rating,:percentage)"""
                handle.createUpdate(
                    insertStatement
                )
                    .bind("region", carbonIntensityRegionInsert.region)
                    .bind("forecast", carbonIntensityRegionInsert.forecast)
                    .bind("fuel", carbonIntensityRegionInsert.fuel)
                    .bind("index_rating", carbonIntensityRegionInsert.indexRating)
                    .bind("percentage", carbonIntensityRegionInsert.percentage)
                    .executeAndReturnGeneratedKeys("id").mapTo(Int::class.java).first()

            }
        }

    fun truncateRegions(handle: Handle) {
        handle.createUpdate("""
        truncate table project_green.regional_data;
        """).execute()
    }
}
