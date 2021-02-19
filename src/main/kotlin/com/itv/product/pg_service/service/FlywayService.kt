package com.itv.product.pg_service.service

import com.itv.product.pg_service.config.Properties
import mu.KotlinLogging
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.FlywayException
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

fun migrateDb() {
    try {
        getConnection().migrate()
    } catch (e: FlywayException) {
        logger.error { "Exception during flyway migration $e. Shutting down JVM." }
        shutdown()
    }
}

fun getConnection(): Flyway {
    return Flyway.configure().dataSource(
        "jdbc:postgresql://${Properties.postgres_host}:${Properties.postgres_port}/${Properties.postgres_database}",
        Properties.postgres_username,
        Properties.postgres_password
    ).schemas("public").defaultSchema("public").load()
}

private fun shutdown() {
    exitProcess(1)
}
