package com.example.linebot.data;
import com.example.linebot.service.JankenResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
@Repository
public class JankenAPI {
    @Value("${janken.api.url}")
    private String API_URL;

    public JankenResponse playGame(Resource imageResource) {
        if (!imageResource.exists()) {
            throw new IllegalArgumentException("ファイルが存在しません" + imageResource.getFilename());
        }


        //MultivalueMapで送信データを定義する
        //HTML<inputに対応する>
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", imageResource);

        //
        //
        JankenResponse response = RestClient.builder().requestFactory(
                        new BufferingClientHttpRequestFactory(
                                new SimpleClientHttpRequestFactory()))
                .build()
                .post()
                .uri(API_URL)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(map)
                .retrieve()
                .body(JankenResponse.class);

        System.out.println(response);
        return response;
    }

}
