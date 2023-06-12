package com.oocode.assignment2023;

import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

// 51.534327 -0.012768 51.503070 -0.280302
public class TravelTimeEstimator {
    public static void main(String[] args) throws Exception {
        System.out.println(travelTimeInMinutes(args));
    }

    // Name + signature of this method, "travelTimeInMinutes", must not change
    // i.e. no change to return type, modifier ("static"), exception, parameter
    public static int travelTimeInMinutes(String[] args) throws Exception {
        Integer s = null;

        if(args.length != 4) {
            throw new Exception("Error 100: Input must have 4 entries in total.");
        }

        Request request = buildRequest(args);

        try {
            String responseString = executeRequest(request);
            return evaluateResponse(responseString);
        } catch (Exception e) {
            throw e;
        }
    }

    public static Request buildRequest(String[] args) {
        String startLat = args[0], startLong = args[1],
                endLat = args[2], endLong = args[3];
        String x = startLat + "," + startLong, y = endLat + "," + endLong;
        System.out.println(x + "  "  + y);
        return new Request.Builder()
                .url("https://api.external.citymapper.com" +
                        "/api/1/traveltimes?" +
                        "start=" + x + "&end=" + y)
                .addHeader("Citymapper-Partner-Key",
                        System.getenv("CITYMAPPER_KEY"))
                .build();
    }

    public static String executeRequest(Request request) throws Exception {
        try {
            Response r = new OkHttpClient().newCall(request).execute();
            if (r.isSuccessful()) {
                return r.body().string();
            } else {
                throw new Exception("Error 101: Request did not yield a good response.");
            }
        } catch (Exception e) {
            System.out.println("Request was not successful");
            throw new Exception("Error 101: Request did not yield a good response.");
        }
    }

    public static int evaluateResponse(String responseString) {
        int transitTime = JsonParser.parseString(responseString)
                .getAsJsonObject()
                .get("transit_time_minutes").getAsInt();
        int walkingTime = JsonParser.parseString(responseString)
                .getAsJsonObject()
                .get("walk_travel_time_minutes").getAsInt();
        int a = Math.min(walkingTime, transitTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("GMT+1"));
        if (checkTheTime(a, zonedDateTime)) {
            return a;
        } else {
            throw new RuntimeException("Error 200: Be careful! Journey will end after midnight.");
        }
    }

    public static boolean checkTheTime(int minutes, ZonedDateTime zonedDateTime) {
        // returns true if the estimated end of the travel is before midnight of the current day
        // returns false otherwise
        return zonedDateTime.getDayOfMonth() == zonedDateTime.plusMinutes(minutes).getDayOfMonth();
    }
}
