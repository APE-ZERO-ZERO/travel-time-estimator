package com.oocode.assignment2023;

import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

// 51.534327 -0.012768 51.503070 -0.280302
public class TravelTimeEstimator {
    public static void main(String[] args) throws Exception {
        System.out.println(travelTimeInMinutes(args));
    }

    // Name + signature of this method, "travelTimeInMinutes", must not change
    // i.e. no change to return type, modifier ("static"), exception, parameter
    public static int travelTimeInMinutes(String[] args) throws Exception {
        String s = null;

        Request request = buildRequest(args);

        try (Response r = new OkHttpClient().newCall(request).execute()) {
            if (r.isSuccessful()) {
                try (ResponseBody rb = r.body()) {
                    s = JsonParser.parseString(rb.string())
                            .getAsJsonObject()
                            .get("transit_time_minutes").getAsString();
                }
            }
        }
        return Integer.parseInt(s);
    }

    public static Request buildRequest(String[] args) {
        String startLat = args[0], startLong = args[1],
                endLat = args[2], endLong = args[3];
        String x = startLat + "," + startLong, y = endLat + "," + endLong;
        System.out.println(x + "  "  + y);
        //System.out.println("curl -H \"Citymapper-Partner-Key: dRywdoAozF2ptKzMxiHmG7PLi0LAGyao” \"https://api.external.citymapper.com/api/1/traveltimes?start=" + x + "&end=" + y + "\"");
        return new Request.Builder()
                .url("https://api.external.citymapper.com" +
                        "/api/1/traveltimes?" +
                        "start=" + x + "&end=" + y)
                .addHeader("Citymapper-Partner-Key",
                        System.getenv("CITYMAPPER_KEY"))
                .build();
    }
}
