package com.itv.product.pg_service.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.itv.product.pg_service.model.CICache
import com.itv.product.pg_service.model.entity.CarbonIntensityResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import java.util.*
import kotlin.concurrent.timerTask


private val logger = KotlinLogging.logger {}

@io.ktor.util.KtorExperimentalAPI
suspend fun updateCache() =
    coroutineScope {
        try {
            val updatedData = async { getCarbonIntensityData().toString() }
            val updatedCache = CICache.updateCache(updatedData.await())
            CICache.updateCache(updatedData.await())
            logger.info { "cache updated = $updatedCache" }
        } catch (e: Exception) {
            logger.error { "Failed to write to cache .... $e" }
        }
    }

fun retrieveCarbonCache(): CarbonIntensityResponse {
    val carbonCache = CICache.cacheString
    val mapper = jacksonObjectMapper()
    mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
    return mapper.readValue<CarbonIntensityResponse>(carbonCache, CarbonIntensityResponse::class.java)
}

@io.ktor.util.KtorExperimentalAPI
fun pollCarbonIntensityApi() = Timer().scheduleAtFixedRate(timerTask {
    logger.info { "Retrieving updated carbon intensity api data ....." }
    try {
        runBlocking { updateCache() }
    } catch (e: Exception) {
        logger.error { "Experienced error in timertask whilst updating api .... $e" }
    }
}, 0, 1000 * 60 * 30)

