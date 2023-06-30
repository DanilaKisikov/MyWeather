package ru.etysoft.myweather.weather;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.etysoft.myweather.location.Location;

public class WeatherGetter {

    private static OkHttpClient client = new OkHttpClient();

    private static final String KEY = "174b5b1ad29746299cf230242231505";

    static String getResponse(Location location) throws IOException {

        if (location.isLocal()) {
            // обновить расположение сейчас
        }

        String myUrl = "https://api.weatherapi.com/v1/forecast.json?key=" + KEY +
                "&q=" + location.getLatitude() + "," + location.getLongitude()
                + "&days=" + WeekForecast.WEEK + "&aqi=no&alerts=no";

        Request request = new Request.Builder()
                .url(myUrl)
                .build();

        System.out.println(myUrl);

        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();

        return jsonResponse;

    }

    public static boolean isNight(String date) {
        try {
            int hour = Integer.parseInt(date.split(" ")[1].split(":")[0]);

            if (hour < 6 | hour > 23) {
                return true;
            }

        } catch (Exception ignored) {
        }
        return false;
    }

}
