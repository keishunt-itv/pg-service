package com.itv.product.pg_service.infra.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class HealthCheck(
    val isHealthy: Boolean
)
