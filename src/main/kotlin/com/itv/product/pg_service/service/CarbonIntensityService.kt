package com.itv.product.pg_service.service

import com.itv.product.pg_service.config.Properties.client
import com.itv.product.pg_service.model.entity.CarbonRegions
import com.itv.product.pg_service.model.enums.CarbonIntensityRegion
import com.itv.product.pg_service.model.resource.CarbonRegionListResource
import com.itv.product.pg_service.model.resource.CarbonRegionResource
import com.itv.product.pg_service.model.resource.toCarbonRegionListResource
import io.ktor.client.request.get
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@io.ktor.util.KtorExperimentalAPI
suspend fun getCarbonIntensityData(): String =
    client.get("https://api.carbonintensity.org.uk/regional")

@io.ktor.util.KtorExperimentalAPI
fun getRegionalList(): List<CarbonRegions> = retrieveCarbonCache().data.let { it }

@io.ktor.util.KtorExperimentalAPI
fun convertRegionalList(): CarbonRegionListResource = toCarbonRegionListResource(getRegionalList().first())

@io.ktor.util.KtorExperimentalAPI
fun getRegionData(region: CarbonIntensityRegion): CarbonRegionResource? {
    convertRegionalList().apply {
        val selectedRegion = regions.filter { it.region == region }
        val convertedRegion = selectedRegion.first()
        return convertedRegion
            .also { logger.info { "Retrieving data for $region ....... $convertedRegion" } }
    }
}

fun regionalEnumCheck(value: String): CarbonIntensityRegion? = CarbonIntensityRegion.values().find { it.name == value }
