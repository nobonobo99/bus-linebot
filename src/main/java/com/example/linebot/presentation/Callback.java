package com.example.linebot.presentation;

import com.example.linebot.presentation.replier.*;
import com.example.linebot.service.JankenResult;
import com.example.linebot.service.JankenService;
import com.example.linebot.service.Senreki;
import com.example.linebot.service.SenrekiService;
import com.linecorp.bot.messaging.model.ImagemapMessage;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;
import com.linecorp.bot.spring.boot.handler.annotation.EventMapping;
import com.linecorp.bot.spring.boot.handler.annotation.LineMessageHandler;
import com.linecorp.bot.webhook.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.linecorp.bot.webhook.model.ImageMessageContent;
import java.util.List;
import com.example.linebot.service.BusService; // ★追加: BusServiceのインポート

@LineMessageHandler
public class Callback {

    private static final Logger log = LoggerFactory.getLogger(Callback.class);

    // フォローイベントに対応する
    @EventMapping
    public Message handleFollow(FollowEvent event) {
        // 実際はこのタイミングでフォロワーのユーザIDをデータベースにに格納しておくなど
        Follow follow = new Follow(event);
        return follow.reply();
    }

    // 文章で話しかけられたとき（テキストメッセージのイベント）に対応する
    @EventMapping
    public List<Message> handleMessage(MessageEvent event)throws Exception{
        MessageContent mc = event.message();
        switch (mc) {
            case TextMessageContent tmc:
                // MessageContent が文字列のメッセージ（TextMessageContent)だったとき、tmc = mc にする
                return handleText(tmc);
            case ImageMessageContent imc:
                //
                //
                return handleJanken(imc);
            default:
                // それ以外のメッセージだったとき
                throw new RuntimeException("対応していないメッセージ");
        }
    }
    private final SenrekiService senrekiService;
    public List<Message> handleText(TextMessageContent tmc) {
        String text = tmc.text();
        // --- ★ここから追加: バス案内（部分一致）の判定 ---
        if (text.contains("行き") || text.contains("大学") || text.contains("大学行き")) {
            BusReply busReply = new BusReply(busService, "to_daigaku");
            return List.of(busReply.reply());
        }

        if (text.contains("帰り") || text.contains("千歳") || text.contains("駅")) {
            BusReply busReply = new BusReply(busService, "to_chitose");
            return List.of(busReply.reply());
        }
        // --- ★ここまで追加 ---
        switch (text) {
            case "戦歴":
                SenrekiReply senrekiReply=new SenrekiReply(senrekiService);
                return List.of(senrekiReply.reply());
                // 何もせずに default に進む
            default:
                // おうむ返しのメッセージを作る
                Parrot parrot = new Parrot(text);
                return List.of(parrot.reply());
        }

    }

    private JankenService jankenService;
    private final BusService busService; // ★追加: BusServiceフィールド

    public Callback(JankenService jankenService,SenrekiService senrekiService,BusService busService) {
        this.jankenService = jankenService;
        this.senrekiService = senrekiService;
        this.busService = busService; // ★追加

    }

    public List<Message> handleJanken(ImageMessageContent imc) throws Exception{
        JankenResult jankenResult = jankenService.doJanken(imc);
        return List.of(new ImageSizeReply(jankenResult).reply(),new JankenReply(jankenResult).reply());
    }
}