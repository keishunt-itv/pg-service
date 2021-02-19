package com.itv.product.pg_service

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.itv.product.pg_service.config.Properties
import com.itv.product.pg_service.endpoints.initialiseCarbonEndpoints
import com.itv.product.pg_service.endpoints.initialiseInfraEndpoints
import com.itv.product.pg_service.endpoints.initialiseSwaggerEndpoint
import com.itv.product.pg_service.service.addToDb
import com.itv.product.pg_service.service.getCarbonIntensityData
import com.itv.product.pg_service.service.migrateDb
import com.itv.product.pg_service.service.pollCarbonIntensityApi
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.request.header
import io.ktor.routing.Routing
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import io.ktor.util.KtorExperimentalAPI
import io.ktor.webjars.Webjars
import kotlinx.coroutines.launch
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@KtorExperimentalAPI
fun Application.module() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        anyHost()
    }

//    install(MicrometerMetrics) {
//        initInfluxConfig()
//        registry = metricRegistry
//    }

    install(ContentNegotiation) {

        val javaTimeModule = JavaTimeModule().addSerializer(
            ZonedDateTime::class.java,
            ZonedDateTimeSerializer(DateTimeFormatter.ISO_INSTANT)
        )

        jackson {
            registerModule(Jdk8Module())
            registerModule(javaTimeModule)
            registerModule(ParameterNamesModule())
            registerModule(Jackson2HalModule())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        }
    }

    install(CallId) {
        retrieve { it.request.header(Properties.X_TRACE_ID) }
        generate { UUID.randomUUID().toString() }
        verify { it.isNotEmpty() }
    }

    install(CallLogging) {
        callIdMdc(Properties.X_TRACE_ID)
    }

    install(Webjars)
    install(Routing) {

        routing {
            initialiseInfraEndpoints()
            initialiseSwaggerEndpoint()
            initialiseCarbonEndpoints()
        }
    }

    launch {
//        pollCarbonIntensityApi()
        addToDb()
    }
    migrateDb()
}

@KtorExperimentalAPI
fun main() {
    embeddedServer(Jetty, Properties.server_port, module = Application::module).start(wait = true)
}
