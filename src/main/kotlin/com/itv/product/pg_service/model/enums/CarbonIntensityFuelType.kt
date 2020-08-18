package com.itv.product.pg_service.model.enums

enum class CarbonIntensityFuelType(val label: String)  {
    GAS("Gas"),
    COAL("Coal"),
    NUCLEAR("Nuclear"),
    BIOMASS("Biomass"),
    HYDRO("Hydro"),
    IMPORTS("Imports"),
    SOLAR("Solar"),
    WIND("Wind"),
    OTHER("Other")
}
