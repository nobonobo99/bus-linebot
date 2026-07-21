package com.example.linebot.presentation.replier;

import com.example.linebot.service.JankenResponse;
import com.example.linebot.service.JankenResult;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;
public class JankenReply implements Replier {

    public static final String MESSAGE_FORMAT="あなた：%s,相手:%s\n結果:%s";
    private  final JankenResult jankenResult;
    public  JankenReply(JankenResult jankenResult){
        this.jankenResult=jankenResult;
    }


    @Override
    public Message reply(){
        JankenResponse res = jankenResult.response();
        String message=String.format(MESSAGE_FORMAT,res.jibun(), res.aite(), res.kekka());
        return new TextMessage(message);
    }
}

