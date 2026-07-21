package com.example.linebot.data;

import com.example.linebot.value.BusResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

@Repository
public class BusAPI {

    @Value("${bus.api.url}")
    private String apiUrl;

    private final RestClient restClient;

    public BusAPI() {
        this.restClient = RestClient.create();
    }

    public BusResponse fetchNextBus(String direction) {
        return restClient.get()
                .uri(apiUrl + "?direction={direction}", direction)
                .retrieve()
                .body(BusResponse.class);
    }
}