package com.itv.product.pg_service.endpoints

import com.itv.product.pg_service.model.enums.CarbonIntensityRegion
import com.itv.product.pg_service.service.getCarbonIntensityData
import com.itv.product.pg_service.service.getRegionData
import io.ktor.application.call
import io.ktor.features.BadRequestException
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
fun Routing.initialiseCarbonEndpoints() {
    route("/allregions") {
        get {
            try {
                val result = getCarbonIntensityData()
                call.respond(result)
            } catch (e: BadRequestException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("errors" to listOf("Malformed payload")))
            }
        }
    }

    route("/region/{region}") {
        get {
            try {
                val requestedRegion = call.parameters["region"]?.toUpperCase()
                val enumRegion = enumValueOf<CarbonIntensityRegion>(requestedRegion ?: "invalid")
                val result = getRegionData(enumRegion)
                result?.let { it -> call.respond(HttpStatusCode.OK, it) } ?: call.respond(HttpStatusCode.NotFound)
            } catch (e: BadRequestException) {
                throw BadRequestException("Region invalidr : $e")
            }
        }
    }

}
