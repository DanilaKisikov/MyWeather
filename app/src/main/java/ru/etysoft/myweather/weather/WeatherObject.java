package ru.etysoft.myweather.weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherObject {

    private String date;

    private double temperature;

    private double windSpeed;

    private double feelsLikeTemperature;

    private Condition condition;

    private double chanceOfRain;

    private double chanceOfShow;

    public double getChanceOfRain() {
        return chanceOfRain;
    }

    public void setChanceOfRain(double chanceOfRain) {
        this.chanceOfRain = chanceOfRain;
    }

    public double getChanceOfShow() {
        return chanceOfShow;
    }

    public void setChanceOfShow(double chanceOfShow) {
        this.chanceOfShow = chanceOfShow;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public void setFeelsLikeTemperature(double feelsLikeTemperature) {
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    public void setCondition(int code) {
        switch (code) {

            case 998:
            case 1000:
            case 1003:
                condition = Condition.CLOUDY;
                break;

        }
    }

    public static WeatherObject getWeatherObject(JSONObject response) throws JSONException {

        WeatherObject weatherObject = new WeatherObject();

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

        return weatherObject;
    }
}
