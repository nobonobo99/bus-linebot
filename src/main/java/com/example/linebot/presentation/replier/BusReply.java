package com.example.linebot.presentation.replier;

import com.example.linebot.service.BusService;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;

public class BusReply implements Replier {

    private final BusService busService;
    private final String direction;

    public BusReply(BusService busService, String direction) {
        this.busService = busService;
        this.direction = direction;
    }

    @Override
    public Message reply() {
        String responseText = busService.getNextBusMessage(direction);
        return new TextMessage(responseText);
    }
}