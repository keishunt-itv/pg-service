package com.itv.product.pg_service.service

import com.itv.product.pg_service.config.Properties.client
import com.itv.product.pg_service.model.entity.CarbonIntensityResponse
import com.itv.product.pg_service.model.entity.CarbonRegionData
import com.itv.product.pg_service.model.entity.CarbonRegions
import com.itv.product.pg_service.model.enums.CarbonIntensityRegion
import com.itv.product.pg_service.model.resource.CarbonRegionListResource
import com.itv.product.pg_service.model.resource.CarbonRegionResource
import com.itv.product.pg_service.model.resource.toCarbonRegionListResource
import com.itv.product.pg_service.repositories.RegionRepository
import com.itv.product.pg_service.repositories.daos.toCarbonIntensityRegionInsert
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@io.ktor.util.KtorExperimentalAPI
suspend fun getCarbonIntensityData(): List<CarbonRegionData> {
    return try {
        val carbonResponse = client.get<CarbonIntensityResponse> {
            url("https://api.carbonintensity.org.uk/regional")
        }
        logger.info { "Returning CI Data .... ${carbonResponse.data.size}   ${carbonResponse.data.first().regions}" }
        carbonResponse.data.first().regions
    } catch (e: Exception) {
        logger.error { "Issue retrieving data .... $e" }
        throw e
    }
}

@KtorExperimentalAPI
fun addToDb() {
    try {
        runBlocking {
            getCarbonIntensityData().map { it.toCarbonIntensityRegionInsert() }.apply {
                this.forEach { carbonIntensityRegionInsert ->
                    RegionRepository.addRegionToDatabase(carbonIntensityRegionInsert)
                    logger.info { "Adding ${carbonIntensityRegionInsert.region} to database" }
                }
            }
        }
    }catch (e: Exception) {
        logger.error { "Exception in adding to db $e" }
    }
}

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
