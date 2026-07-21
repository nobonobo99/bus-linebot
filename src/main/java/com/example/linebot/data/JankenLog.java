package com.example.linebot.data;
import com.example.linebot.service.JankenResponse;


import java.util.List;

import com.example.linebot.service.JankenResult;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JankenLog {

    private JdbcClient jdbcClient;

    public JankenLog(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    public int insert(JankenResponse response){
        String statement ="insert into janken_log VALUES(?,?,?)";
        int n = jdbcClient.sql(statement)
                .params(response.jibun(),response.aite(),response.kekka())
                .update();
        return n;
    }

    public List<JankenResponse> selectAll(){
        String statement = """
            select jibun,aite,kekka from janken_log
                where kekka !='エラー'
            """;
        List<JankenResponse>selected=jdbcClient.sql(statement)
                .query(JankenResponse.class)
                .list();
        return selected;
    }
}