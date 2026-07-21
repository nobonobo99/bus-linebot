package com.example.linebot.service;

import com.example.linebot.data.BusAPI;
import com.example.linebot.value.BusResponse;
import org.springframework.stereotype.Service;

@Service
public class BusService {

    private final BusAPI busAPI;

    public BusService(BusAPI busAPI) {
        this.busAPI = busAPI;
    }

    public String getNextBusMessage(String direction) {
        try {
            BusResponse response = busAPI.fetchNextBus(direction);

            if (response == null) {
                return "バス情報の取得に失敗しました。";
            }

            if (response.error() != null) {
                return "エラー: " + response.error();
            }

            if (response.message() != null) {
                return response.message();
            }

            String dirLabel = "to_chitose".equals(response.direction())
                    ? "公立千歳科学技術大学 ➔ 千歳駅"
                    : "千歳駅 ➔ 公立千歳科学技術大学";

            return String.format(
                    "🚌 次のバス案内【%s】\n\n" +
                            "📅 日付: %s\n" +
                            "⏰ 出発: %s（%s）\n" +
                            "🏁 到着: %s\n" +
                            "🚍 路線: %s（%s）",
                    dirLabel,
                    response.dateLabel(),
                    response.departureTime(),
                    response.boardingStop(),
                    response.arrival(),
                    response.route(),
                    response.operator()
            );

        } catch (Exception e) {
            e.printStackTrace();
            return "バス案内システムの呼び出し中にエラーが発生しました。";
        }
    }
}