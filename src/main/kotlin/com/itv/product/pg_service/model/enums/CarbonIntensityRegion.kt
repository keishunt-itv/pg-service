package com.itv.product.pg_service.model.enums

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
enum class CarbonIntensityRegion(val label: String) {
    @JsonProperty("North Scotland")
    NORTH_SCOTLAND("North Scotland"),
    @JsonProperty("South Scotland")
    SOUTH_SCOTLAND("South Scotland"),
    @JsonProperty("North East England")
    NORTH_EAST_ENGLAND("North East England"),
    @JsonProperty("North West England")
    NORTH_WEST_ENGLAND("North West England"),
    @JsonProperty("North Wales & Merseyside")
    NORTH_WALES_MERSEYSIDE("North Wales & Merseyside"),
    @JsonProperty("South Wales")
    SOUTH_WALES("South Wales"),
    @JsonProperty("West Midlands")
    WEST_MIDLANDS("West Midlands"),
    @JsonProperty("East Midlands")
    EAST_MIDLANDS("East Midlands"),
    @JsonProperty("South East England")
    SOUTH_EAST_ENGLAND("South East England"),
    @JsonProperty("London")
    LONDON("London"),
    @JsonProperty("East England")
    EAST_ENGLAND("East England"),
    @JsonProperty("South England")
    SOUTH_ENGLAND("South England"),
    @JsonProperty("South West England")
    SOUTH_WEST_ENGLAND("South West England"),
    @JsonProperty("Yorkshire")
    YORKSHIRE("Yorkshire"),
    @JsonProperty("England")
    ENGLAND("England"),
    @JsonProperty("Scotland")
    SCOTLAND("Scotland"),
    @JsonProperty("Wales")
    WALES("Wales"),
    @JsonProperty("GB")
    GB("GB")
}
