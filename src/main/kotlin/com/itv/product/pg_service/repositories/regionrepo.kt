package com.itv.product.pg_service.repositories

import com.itv.product.pg_service.config.DatabaseConfig
import com.itv.product.pg_service.exceptions.RegionNotFoundException
import com.itv.product.pg_service.repositories.daos.CarbonIntensityRegionDao
import com.itv.product.pg_service.repositories.daos.CarbonIntensityRegionInsert
import com.itv.product.pg_service.repositories.daos.RegionFuelDao
import com.itv.product.pg_service.repositories.daos.RegionFuelInsert
import org.jdbi.v3.core.Handle

object RegionRepository {

    private const val REGION_DAO_QUERY: String = """SELECT
            rd.id,
            rd.region,
            rd.forecast,
            rd.index_rating
            FROM project_green.regional_data rd"""

    private const val FUEL_DAO_QUERY: String = """SELECT
            fd.id,
            fd.region_id,
            fd.fuel,
            fd.percentage
            FROM project_green.fuel_data fd"""

    private fun getRegionDao(regionName: String, handle: Handle): CarbonIntensityRegionDao? =
        handle.createQuery(
            "$REGION_DAO_QUERY WHERE rd.region = :regionName"
        )
            .bind("regionName", regionName)
            .mapTo(CarbonIntensityRegionDao::class.java)
            .firstOrNull()

    fun getFuelDao(regionName: String, handle: Handle): List<RegionFuelDao> =
        handle.createQuery(
            "$FUEL_DAO_QUERY WHERE rd.region = :regionName"
        )
            .bind("regionName", regionName)
            .mapTo(RegionFuelDao::class.java)
            .list()

    private fun addRegionToDatabase(carbonIntensityRegionInsert: CarbonIntensityRegionInsert, handle: Handle): Int {
            val insertStatement =
                """INSERT INTO project_green.regional_data(region, forecast, index_rating)
                                   VALUES(:region,:forecast,:index_rating)"""
            return handle.createUpdate(
                insertStatement
            )
                .bind("region", carbonIntensityRegionInsert.region)
                .bind("forecast", carbonIntensityRegionInsert.forecast)
                .bind("index_rating", carbonIntensityRegionInsert.indexRating)
                .executeAndReturnGeneratedKeys("id").mapTo(Int::class.java).first()

    }

    private fun addFuelToDatabase(fuelInsert: RegionFuelInsert, regionName: String, handle: Handle): Int {
            val region = (
                    getRegionDao(regionName, handle)
                        ?: throw RegionNotFoundException("Region Data Not Found $regionName")
                    )
            val insertStatement =
                """INSERT INTO project_green.fuel_data(region_id, fuel, percentage)
                                   VALUES(:region_id,:fuel,:percentage)"""
            return handle.createUpdate(
                insertStatement
            )
                .bind("region_id", region.id)
                .bind("fuel", fuelInsert.fuel)
                .bind("percentage", fuelInsert.percentage)
                .executeAndReturnGeneratedKeys("id").mapTo(Int::class.java).first()
        }

    fun addToDatabase(carbonIntensityRegionInsert: CarbonIntensityRegionInsert, fuelInsert: List<RegionFuelInsert>) {
        return DatabaseConfig.jdbi().inTransaction<Unit, Exception> { handle ->
            addRegionToDatabase(carbonIntensityRegionInsert = carbonIntensityRegionInsert, handle = handle)
            fuelInsert.forEach { regionFuelInsert ->
                addFuelToDatabase(
                    fuelInsert = regionFuelInsert,
                    regionName = carbonIntensityRegionInsert.region,
                    handle = handle
                )
            }
        }
    }

    fun truncateWholeDatabase() =
        DatabaseConfig.jdbi().inTransaction<Unit, Exception> { handle ->
            truncateFuels(handle)
            truncateRegions(handle)
        }

    private fun truncateRegions(handle: Handle) {
        handle.createUpdate(
            """
        truncate table project_green.regional_data cascade;
        """
        ).execute()
    }

    fun truncateFuels(handle: Handle) {
        handle.createUpdate(
            """
        truncate table project_green.fuel_data cascade;
        """
        ).execute()
    }
}
