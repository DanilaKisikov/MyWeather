package ru.etysoft.myweather.location;

import java.util.ArrayList;
import java.util.HashMap;

public class LocationHandler {

    private static HashMap<String, Location> locations = new HashMap<>();

    private static Location currentLocation;

    public static Location getCurrentLocation() {
        return currentLocation;
    }

    public static Location getLocation(String locationName) {
        return locations.get(locationName);
    }

    public static ArrayList<Location> getLocations() {
        return new ArrayList<Location>(locations.values());
    }

    public static ArrayList<String> getLocationNames() {
        return new ArrayList<String>(locations.keySet());
    }

    public static void setCurrentLocation(Location currentLocation) {
        LocationHandler.currentLocation = currentLocation;
    }

    public static void initialiseLocations() {
        currentLocation = new Location("Saint-Petersburg", false,
                59.83216778150685, 30.401565521624423);
        // ...
    }
}
