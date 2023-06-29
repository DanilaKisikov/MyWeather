package ru.etysoft.myweather.location;

import android.graphics.Color;

import java.util.Random;

public class Location {

    private String locationName;

    private boolean isDeletable;

    private double latitude;

    private double longitude;

    private boolean isCurrentLoc = false;

    private int color;

    public Location(String locationName, boolean isDeletable, double latitude, double longitude) {
        this.locationName = locationName;
        this.isDeletable = isDeletable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.generateColor();
    }

    public Location(String locationName, boolean isRealCurrent) {
        this.locationName = locationName;
        this.isCurrentLoc = isRealCurrent;
        this.generateColor();
    }

    public String getLocationName() {
        return locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isDeletable() {
        return isDeletable;
    }

    public boolean isCurrentLoc() {
        return isCurrentLoc;
    }

    public int getColor() {
        return color;
    }

    private void generateColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        this.color = color;
    }
}
