package ru.etysoft.myweather.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherGetter {

    private static OkHttpClient client = new OkHttpClient();

    private static int week = 7;

    private static final String KEY = "174b5b1ad29746299cf230242231505";

    private static String getResponse(String location) throws IOException {
        String myUrl = "https://api.weatherapi.com/v1/forecast.json?key=" + KEY +
                "&q=metar:" + location + "&days=" + week + "&aqi=no&alerts=no";

        Request request = new Request.Builder()
                .url(myUrl)
                .build();

        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();

        return jsonResponse;

    }

    private static WeatherObject updateWeatherObject(JSONObject response) throws IOException {

        WeatherObject weatherObject = new WeatherObject();

        try {
            try {
                weatherObject.setDate(response.getString("last_updated"));
            } catch (Exception e) {
                weatherObject.setDate(response.getString("time"));
            }

            weatherObject.setTemperature(response.getDouble("temp_c"));

            weatherObject.setFeelsLikeTemperature(response.getDouble("feelslike_c"));

            weatherObject.setWindSpeed(response.getDouble("wind_kph"));

            weatherObject.setChanceOfRain(response.getDouble("chance_of_rain"));

            weatherObject.setChanceOfShow(response.getDouble("chance_of_snow"));

            weatherObject.setCondition(response.getJSONObject("condition").getInt("code"));


        } catch (Exception e) {

        }

        return weatherObject;
    }

    private static WeatherDay getDayWeather(JSONObject thisDay) throws JSONException {
        WeatherDay weatherDay = new WeatherDay();
        weatherDay.setMaxTemp(thisDay.getDouble("maxtemp_c"));
        weatherDay.setMinTemp(thisDay.getDouble("mintemp_c"));
        weatherDay.setAvgTemp(thisDay.getDouble("avgtemp_c"));

        weatherDay.setCondition(thisDay.getJSONObject("condition").getInt("code"));
        weatherDay.setChanceOfRain(thisDay.getDouble("daily_chance_of_rain"));
        weatherDay.setChanceOfShow(thisDay.getDouble("daily_chance_of_snow"));

        weatherDay.setMaxWind(thisDay.getDouble("maxwind_kph"));

        return weatherDay;
    }

    public static ArrayList<HashMap<Integer, WeatherObject>> getWeek(String location) throws IOException, JSONException {
        ArrayList<HashMap<Integer, WeatherObject>> list = new ArrayList<>();

        String jsonString = getResponse(location);

        JSONObject object = new JSONObject(jsonString);

        JSONObject current = object.getJSONObject("current");
        WeatherObject currentWeatherObject = updateWeatherObject(current);
        HashMap<Integer, WeatherObject> currentHashMap = new HashMap<>();
        currentHashMap.put(0, currentWeatherObject);
        list.add(currentHashMap);

        JSONArray daysJson = object.getJSONObject("forecast").getJSONArray("forecastday");

        for (int i = 0; i < week; i++) {
            JSONObject dayObject = daysJson.getJSONObject(i);

            JSONObject thisDay = dayObject.getJSONObject("day");
            WeatherDay weatherDay = getDayWeather(thisDay);
            weatherDay.setDate(dayObject.getString("date"));
            currentHashMap.put(24, weatherDay);

            JSONArray hoursJson = dayObject.getJSONArray("hour");
            for (int a=0; a < 24; a++) {
                JSONObject thisHour = hoursJson.getJSONObject(a);
                WeatherObject thisWeatherObject = updateWeatherObject(thisHour);

                currentHashMap.put(a, thisWeatherObject);
            }

            list.add(currentHashMap);
        }

        return list;
    }
}
