package com.example.linebot.service;
import com.example.linebot.data.JankenLog;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SenrekiService {
    private JankenLog jankenLog;
    public  SenrekiService(JankenLog jankenLog){
        this.jankenLog=jankenLog;
    }

    public Senreki calcSenreki(){
        List<JankenResponse>jankenResponses=jankenLog.selectAll();
        //ゲーム回数の計算
        int gameCount= jankenResponses.size();
        //勝ちの回数を計算
        String keyword="勝ち";
        int jibunWinCount =(int)jankenResponses.stream()
                .skip(1)
                .filter(response->response.kekka()!=null)
                .filter(response -> response.kekka().equals(keyword))
                .count();
        //ゲーム回数、勝ちの回数から勝率(float)で計算
        float jibunWinRate=0;
        if (gameCount>0)
            jibunWinRate=((float)jibunWinCount/(float)gameCount)*100;
        //ゲーム回数が0の時は勝率も0
        if (gameCount==0)
            jibunWinRate=0;


        //Senrekiインスタンスを作成、戻り値に

        return new Senreki(gameCount,jibunWinCount,jibunWinRate);
    }
}
