package ru.etysoft.myweather.weather;

public class WeatherObject {

    private String lastUpdate;

    private double temperature;

    private double windSpeed;

    private double feelsLikeTemperature;

    private Condition condition;

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
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
            case 1003: condition = Condition.CLOUDY;
                break;

        }
    }
}
