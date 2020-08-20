package com.itv.product.pg_service.service

import com.itv.product.pg_service.config.Properties.client
import com.itv.product.pg_service.model.entity.CarbonIntensityResponse
import com.itv.product.pg_service.model.entity.CarbonRegionData
import com.itv.product.pg_service.model.entity.CarbonRegions
import com.itv.product.pg_service.model.enums.CarbonIntensityRegion
import io.ktor.client.request.get
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@io.ktor.util.KtorExperimentalAPI
suspend fun getCarbonIntensityData(): CarbonIntensityResponse =
    client.get("https://api.carbonintensity.org.uk/regional")

@io.ktor.util.KtorExperimentalAPI
suspend fun getRegionalList(): List<CarbonRegions> = getCarbonIntensityData().data.let { it }

@io.ktor.util.KtorExperimentalAPI
suspend fun getRegionData(region: CarbonIntensityRegion): CarbonRegionData? {
    logger.info { "Retrieving carbon intensity data for $region... " }
    getRegionalList().apply {
        val validRegion = map { it ->
            it.regions.filter { it.region == region }
        }
        logger.info { "Returning data... $validRegion" }
        return validRegion.first().firstOrNull()
    }
}

fun regionalEnumCheck(value: String): CarbonIntensityRegion? = CarbonIntensityRegion.values().find { it.name == value }
