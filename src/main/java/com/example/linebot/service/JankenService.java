package com.example.linebot.service;
import com.example.linebot.data.JankenLog;
import com.linecorp.bot.webhook.model.ImageMessageContent;
import org.springframework.stereotype.Service;
import com.example.linebot.data.Blob;
import com.example.linebot.data.JankenAPI;
import org.springframework.core.io.Resource;
@Service
public class JankenService {
    private JankenLog jankenLog;
    private  Blob blob;

    private  JankenAPI jankenAPI;

    public JankenService(Blob blob,JankenAPI jankenAPI,JankenLog jankenLog){
        this.blob=blob;
        this.jankenAPI=jankenAPI;
        this.jankenLog=jankenLog;
    }

    public JankenResult doJanken(ImageMessageContent imc)
        throws Exception{
        Resource imageRessource= blob.getImageResource(imc);
        JankenResponse jankenResponse =jankenAPI.playGame(imageRessource);
        jankenLog.insert(jankenResponse);
        JankenResult jankenResult=new JankenResult(imageRessource.contentLength(),jankenResponse);
        return jankenResult;
    }

}
