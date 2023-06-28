package ru.etysoft.myweather.location;

public class Location {

    private String locationName;

    private boolean isDeletable;

    private double latitude;

    private double longitude;

    public Location(String locationName, boolean isDeletable, double latitude, double longitude) {
        this.locationName = locationName;
        this.isDeletable = isDeletable;
        this.latitude = latitude;
        this.longitude = longitude;
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
}
