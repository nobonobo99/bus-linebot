package com.example.linebot.data;
import com.linecorp.bot.messaging.client.MessagingApiBlobClient;
import com.linecorp.bot.webhook.model.ImageMessageContent;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import com.linecorp.bot.client.base.BlobContent;
import com.linecorp.bot.client.base.Result;
import java.io.InputStream;
@Repository
public class Blob {

    //
    //
    private MessagingApiBlobClient blob;

    public Blob(MessagingApiBlobClient blob){
        this.blob=blob;
    }

    public Resource getImageResource(ImageMessageContent imc)
        throws Exception{
        //例外スロー
        //送られたLINEのメッセージIDを取得
        String msbId = imc.id();
        //BLObからメッセージIDと対応する画像の取得準備
        Result<BlobContent>blobContentResult= blob.getMessageContent(msbId).get();
        try(InputStream is = blobContentResult.body().byteStream()){
            //画像をバイトデータで取得
            //画像が期限切れの場合は例外発生
            LINEImageResource resource=new LINEImageResource(is.readAllBytes());
            return resource;
        }
    }
}
