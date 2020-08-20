package com.itv.product.pg_service.model.enums

import com.fasterxml.jackson.annotation.JsonProperty

enum class CarbonIntensityFuelType(val label: String) {
    @JsonProperty("gas")
    GAS("gas"),
    @JsonProperty("coal")
    COAL("coal"),
    @JsonProperty("nuclear")
    NUCLEAR("nuclear"),
    @JsonProperty("biomass")
    BIOMASS("biomass"),
    @JsonProperty("hydro")
    HYDRO("hydro"),
    @JsonProperty("imports")
    IMPORTS("imports"),
    @JsonProperty("solar")
    SOLAR("solar"),
    @JsonProperty("wind")
    WIND("wind"),
    @JsonProperty("other")
    OTHER("other")
}
