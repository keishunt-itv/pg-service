package com.itv.product.pg_service.config

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.header
import org.slf4j.MDC
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule
import java.util.*

object Properties {
    const val X_TRACE_ID = "X-Trace-Id"

    private val config = systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("application.properties")

    val server_port = config[Key("server.port", intType)]

    val client = HttpClient {
        install(JsonFeature) {
            serializer = JacksonSerializer {
                this.registerModule(JavaTimeModule()).registerModule(Jackson2HalModule())
            }
            defaultRequest {
                header(X_TRACE_ID, MDC.get(X_TRACE_ID) ?: UUID.randomUUID().toString())
            }
        }
    }

//    @io.ktor.util.KtorExperimentalAPI
//    val jacksonHttpClient = HttpClient {
//        install(JsonFeature) { serializer = JacksonSerializer {} }
//    }

    val influxUrl = config[Key("influx.url", stringType)]
    val influxDb = config[Key("influx.db", stringType)]
    val influxStep = config[Key("influx.step", longType)]

    private val gitConfig = systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("git.properties")

    val commitId = gitConfig[Key("git.commit.id", stringType)]
    val commitMessage = gitConfig[Key("git.commit.message.full", stringType)]
    val commitTime = gitConfig[Key("git.commit.time", stringType)]

    val env = config[Key("_environment", stringType)]
    val host = config[Key("hostname", stringType)]

    val postgres_host = config[Key("postgres.host", stringType)]
    val postgres_port = config[Key("postgres.port", intType)]
    val postgres_database = config[Key("postgres.database", stringType)]
    val postgres_username = config[Key("postgres.username", stringType)]
    val postgres_password = config[Key("postgres.password", stringType)]
    val postgres_schema = config[Key("postgres.schema", stringType)]
    val maximum_pool_size = config[Key("postgres.maximumPoolSize", intType)]
    val minimum_idle_connections = config[Key("postgres.minimumIdleConnections", intType)]
}
