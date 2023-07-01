package ru.etysoft.myweather.location;

import android.graphics.Color;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Random;

@Entity
public class Location {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String locationName;

    private boolean isDeletable = false;

    private double latitude;

    private double longitude;

    private boolean isLocal = false;

    private int color;

    private boolean isCurrentLocation;

    @Ignore
    public Location() {
    }

    @Ignore
    public Location(String locationName, boolean isDeletable, double latitude, double longitude,
                    boolean isCurrentLocation) {
        this.locationName = locationName;
        this.isDeletable = isDeletable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.generateColor();
        this.isCurrentLocation = isCurrentLocation;
    }

    @Ignore
    public Location(String locationName, boolean isLocal, boolean isCurrentLocation) {
        this.locationName = locationName;
        this.isLocal = isLocal;
        this.isDeletable = false;
        this.generateColor();
        this.isCurrentLocation = isCurrentLocation;
    }

    public Location(long id, String locationName, boolean isDeletable, double latitude,
                    double longitude, boolean isLocal, int color, boolean isCurrentLocation) {
        this.id = id;
        this.locationName = locationName;
        this.isDeletable = isDeletable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isLocal = isLocal;
        this.color = color;
        this.isCurrentLocation = isCurrentLocation;
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

    public boolean isLocal() {
        return isLocal;
    }

    public int getColor() {
        return color;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setDeletable(boolean deletable) {
        isDeletable = deletable;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCurrentLocation() {
        return isCurrentLocation;
    }

    public void setCurrentLocation(boolean currentLocation) {
        isCurrentLocation = currentLocation;
    }

    private void generateColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        this.color = color;
    }
}
