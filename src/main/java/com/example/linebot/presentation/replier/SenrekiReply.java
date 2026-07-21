package com.example.linebot.presentation.replier;

import com.example.linebot.service.Senreki;
import com.example.linebot.service.SenrekiService;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;

public class SenrekiReply implements Replier{
    public static final String MESSAGE_FORMAT="あなたは%s戦中%s勝(勝率%sパーセント)";
    private SenrekiService senrekiService;
    public SenrekiReply(SenrekiService senrekiService){
        this.senrekiService=senrekiService;
    }

    @Override
    public Message reply(){
        Senreki senreki = senrekiService.calcSenreki();
        String message =String.format(MESSAGE_FORMAT, senreki.gameCount(),senreki.jibunWinCount(),senreki.jibunWinRate());
        return new TextMessage(message);
    }
}
