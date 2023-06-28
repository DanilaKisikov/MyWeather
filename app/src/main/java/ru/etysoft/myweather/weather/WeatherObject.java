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

    public Condition getCondition() {
        return condition;
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

//            CLEAR
//            CLOUDY,
//            SNOW,
//            SUNNY,
//            RAIN,
//            THUNDER

            case 1000:
                if (WeatherGetter.isNight(date)) {
                    condition = Condition.CLEAR;
                } else {
                    condition = Condition.SUNNY;
                }
                break;
            case 1003:
            case 1006:
            case 1009:
            case 1063:
            case 1066:
            case 1069:
            case 1072:
                condition = Condition.CLOUDY;
                break;
            case 1030:
            case 1135:
            case 1147:
                condition = Condition.MIST;
                break;
            case 1087:
                condition = Condition.THUNDER;
                break;
            case 1114:
            case 1117:
            case 1204:
            case 1207:
            case 1210:
            case 1213:
            case 1216:
            case 1219:
            case 1222:
            case 1225:
            case 1237:
            case 1273:
            case 1276:
            case 1279:
            case 1282:
                condition = Condition.SNOW;
                break;
            case 1150:
            case 1168:
            case 1171:
            case 1180:
            case 1183:
            case 1186:
            case 1189:
            case 1192:
            case 1195:
            case 1198:
            case 1201:
            case 1240:
            case 1243:
            case 1246:
            case 1249:
            case 1252:
            case 1255:
            case 1258:
            case 1261:
            case 1264:
                condition = Condition.RAIN;
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

        try {
            weatherObject.setChanceOfRain(response.getDouble("chance_of_rain"));
        } catch (Exception ignored) {
        }

        try {
            weatherObject.setChanceOfShow(response.getDouble("chance_of_snow"));
        } catch (Exception ignored) {
        }

        weatherObject.setCondition(response.getJSONObject("condition").getInt("code"));

        return weatherObject;
    }

}
