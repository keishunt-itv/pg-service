package com.itv.product.pg_service.endpoints

import com.itv.product.pg_service.service.getRegionData
import com.itv.product.pg_service.service.getRegionalList
import com.itv.product.pg_service.service.regionalEnumCheck
import io.ktor.application.call
import io.ktor.features.BadRequestException
import io.ktor.features.NotFoundException
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
fun Routing.initialiseCarbonEndpoints() {

    route("/all") {
        get {
            try {
                val result = getRegionalList()
                call.respond(HttpStatusCode.OK, result)
            } catch (e: BadRequestException) {
                call.respond(HttpStatusCode.BadRequest, "bad request ... $e")
            }
        }
    }

    route("/region/{region}") {
        get {
            try {
                val requestedRegion = call.parameters["region"]?.toUpperCase()
                val enumRegion = regionalEnumCheck(requestedRegion ?: "invalid")
                    ?: throw Exception("$requestedRegion does not exist on enum CarbonIntensityRegion ").also {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            "400 Bad Request: Invalid region selected - $requestedRegion"
                        )
                    }
                val result = getRegionData(enumRegion)
                result?.let { call.respond(HttpStatusCode.OK, it) } ?: call.respond(HttpStatusCode.NotFound)
            } catch (e: NotFoundException) {
                call.respond(HttpStatusCode.NotFound, "Incorrect path.... $e")
                throw NotFoundException("Incorrect path : $e")
            }
        }
    }

}
