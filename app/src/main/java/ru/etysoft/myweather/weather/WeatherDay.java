package ru.etysoft.myweather.weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherDay extends WeatherObject {
    private double maxTemp;
    private double minTemp;
    private double avgTemp;
    private double maxWind;

    private ArrayList<WeatherObject> hours = new ArrayList<>();

    public ArrayList<WeatherObject> getHours() {
        return hours;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public double getMaxWind() {
        return maxWind;
    }

    public void setMaxWind(double maxWind) {
        this.maxWind = maxWind;
    }

    public static WeatherDay getDayWeather(JSONObject thisDay) throws JSONException {
        WeatherDay weatherDay = new WeatherDay();
        weatherDay.setMaxTemp(thisDay.getDouble("maxtemp_c"));
        weatherDay.setMinTemp(thisDay.getDouble("mintemp_c"));
        weatherDay.setAvgTemp(thisDay.getDouble("avgtemp_c"));

        weatherDay.setCondition(thisDay.getJSONObject("condition").getInt("code"));
        weatherDay.setChanceOfRain(thisDay.getDouble("daily_chance_of_rain"));
        weatherDay.setChanceOfShow(thisDay.getDouble("daily_chance_of_snow"));

        weatherDay.setMaxWind(thisDay.getDouble("maxwind_kph"));

        try {
            weatherDay.setDate(thisDay.getString("date"));
        } catch (Exception e) {

        }

        return weatherDay;
    }
}
