package com.itv.product.pg_service.service

import com.itv.product.pg_service.config.Properties.client
import com.itv.product.pg_service.model.entity.CarbonRegionData
import com.itv.product.pg_service.model.entity.CarbonRegions
import com.itv.product.pg_service.model.enums.CarbonIntensityRegion
import io.ktor.client.request.get
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@io.ktor.util.KtorExperimentalAPI
suspend fun getCarbonIntensityData(): String =
    client.get("https://api.carbonintensity.org.uk/regional")

@io.ktor.util.KtorExperimentalAPI
fun getRegionalList(): List<CarbonRegions> = retrieveCarbonCache().data.let { it }

@io.ktor.util.KtorExperimentalAPI
fun getRegionData(region: CarbonIntensityRegion): CarbonRegionData? {
    getRegionalList().apply {
        val selectedRegion = map { it ->
            it.regions.filter { it.region == region }
        }
        return selectedRegion.first().firstOrNull()
            .also { logger.info { "Retrieving data for $region ....... $selectedRegion" } }
    }
}

fun regionalEnumCheck(value: String): CarbonIntensityRegion? = CarbonIntensityRegion.values().find { it.name == value }
