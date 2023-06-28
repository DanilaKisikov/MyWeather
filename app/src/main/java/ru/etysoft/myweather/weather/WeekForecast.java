package ru.etysoft.myweather.weather;

import static ru.etysoft.myweather.weather.WeatherDay.getDayWeather;
import static ru.etysoft.myweather.weather.WeatherGetter.getResponse;
import static ru.etysoft.myweather.weather.WeatherObject.getWeatherObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ru.etysoft.myweather.location.LocationHandler;

public class WeekForecast {

    public static final int WEEK = 7;

    private WeatherObject currentWeather;

    private ArrayList<WeatherDay> days = new ArrayList<>();

    public WeatherObject getCurrentWeather() {
        return currentWeather;
    }

    public ArrayList<WeatherDay> getDays() {
        return days;
    }

    public static WeekForecast getNewWeek() throws IOException, JSONException {

        WeekForecast week = new WeekForecast();

        String jsonString = getResponse(LocationHandler.getCurrentLocation());

        JSONObject object = new JSONObject(jsonString);

        JSONObject current = object.getJSONObject("current");
        week.currentWeather = getWeatherObject(current);

        JSONArray daysJson = object.getJSONObject("forecast").getJSONArray("forecastday");

        int count = daysJson.length();
        for (int i = 0; i < count; i++) {
            JSONObject dayObject = daysJson.getJSONObject(i);

            JSONObject thisDay = dayObject.getJSONObject("day");
            WeatherDay weatherDay = getDayWeather(thisDay);

            JSONArray hoursJson = dayObject.getJSONArray("hour");
            for (int a = 0; a < 24; a++) {
                JSONObject thisHour = hoursJson.getJSONObject(a);
                WeatherObject thisWeatherObject = getWeatherObject(thisHour);

                weatherDay.getHours().add(thisWeatherObject);
            }

            week.days.add(weatherDay);
        }

        return week;
    }
}
