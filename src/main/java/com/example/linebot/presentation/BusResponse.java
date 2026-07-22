package com.example.linebot.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BusResponse(
        String direction,
        @JsonProperty("next_buses") List<String> nextBuses,
        String message,
        String error
) {}
