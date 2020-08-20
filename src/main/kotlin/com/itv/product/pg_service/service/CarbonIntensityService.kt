package com.itv.product.pg_service.service

import com.itv.product.pg_service.config.Properties.client
import com.itv.product.pg_service.model.entity.CarbonIntensityResponse
import com.itv.product.pg_service.model.entity.CarbonRegionData
import com.itv.product.pg_service.model.enums.CarbonIntensityRegion
import io.ktor.client.request.get
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@io.ktor.util.KtorExperimentalAPI
suspend fun getCarbonIntensityData(): CarbonIntensityResponse {
    logger.info { "Making call to collect carbon intensity data... " }
    return client.get("https://api.carbonintensity.org.uk/regional")
}

suspend fun getRegionData(region: CarbonIntensityRegion): CarbonRegionData? {
    logger.info { "Retrieving carbon intensity data for $region... " }
    getCarbonIntensityData().data.apply {
        val validRegion = map { regions ->
            regions.regions.filter {
                it.region == region
            }
        }
        logger.info { validRegion }
        return validRegion.first().first()
    }
}
