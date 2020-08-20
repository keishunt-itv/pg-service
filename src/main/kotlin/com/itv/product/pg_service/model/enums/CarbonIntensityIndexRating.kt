package com.itv.product.pg_service.model.enums

import com.fasterxml.jackson.annotation.JsonProperty

enum class CarbonIntensityIndexRating(val label: String) {
    @JsonProperty("very low")
    VERY_LOW("very low"),
    @JsonProperty("low")
    LOW("low"),
    @JsonProperty("moderate")
    MODERATE("moderate"),
    @JsonProperty("high")
    HIGH("high"),
    @JsonProperty("very high")
    VERY_HIGH("very high")
}
