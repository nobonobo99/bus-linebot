package com.example.linebot.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BusResponse(
        String direction,
        @JsonProperty("date_label") String dateLabel,
        @JsonProperty("departure_time") String departureTime,
        String route,
        String operator,
        @JsonProperty("boarding_stop") String boardingStop,
        String arrival,
        String message,
        String error
) {}
