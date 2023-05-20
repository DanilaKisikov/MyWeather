package ru.etysoft.myweather.weather;

public class WeatherDay extends WeatherObject {

    private double maxTemp;
    private double minTemp;
    private double avgTemp;

    private double maxWind;

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
}
