package ru.etysoft.myweather.weather;

import org.json.JSONObject;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherGetter {

    private static OkHttpClient client = new OkHttpClient();

    private static final String KEY = "174b5b1ad29746299cf230242231505";

    public static String getResponse(String location) throws IOException {
        String myUrl = "https://api.weatherapi.com/v1/current.json?key=" + KEY +
                "&q=metar:" + location + "&aqi=no";

        Request request = new Request.Builder()
                .url(myUrl)
                .build();

        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();

        return jsonResponse;

    }

    public static WeatherObject updateHashMap(String location) throws IOException {

        String response = getResponse(location);

        WeatherObject weatherObject = new WeatherObject();

        try {
            JSONObject json = new JSONObject(response);

            JSONObject current = json.getJSONObject("current");

            weatherObject.setLastUpdate(current.getString("last_updated"));

            weatherObject.setTemperature(current.getDouble("temp_c"));

            weatherObject.setFeelsLikeTemperature(current.getDouble("feelslike_c"));

            weatherObject.setWindSpeed(current.getDouble("wind_kph"));

            weatherObject.setCondition(current.getJSONObject("condition").getInt("code"));


        } catch (Exception e) {

        }

        return weatherObject;
    }
}
