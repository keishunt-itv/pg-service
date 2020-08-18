package com.itv.product.pg_service.infra

import com.itv.product.pg_service.infra.model.HealthCheck
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun doHealthCheck(): HealthCheck {
    logger.info("Performing health check")
    // TODO implement service specific health check
    return HealthCheck(isHealthy = true)
}
