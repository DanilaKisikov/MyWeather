package ru.etysoft.myweather.location;

import android.app.Activity;
import android.content.Context;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.etysoft.myweather.db.LocationDatabase;

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

    public static void initialiseLocations(Activity activity, LocationLoadListener listener) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LocationDatabase db = Room.databaseBuilder(activity,
                        LocationDatabase.class, "location-database").build();

                List<Location> allLocations = db.getLocationDao().getAllLocations();

                if (allLocations.size() != 0) {
                    for (Location location: allLocations) {
                        locations.put(location.getLocationName(), location);
                        if (location.isCurrentLocation()) {
                            currentLocation = location;
                        }
                    }
                } else {
                    Location local = new Location("Current", true, true);
                    currentLocation = local;
                    locations.put(local.getLocationName(), local);

                    Location spb = new Location("Saint-Petersburg", false, 59.951338, 30.314051, false);
                    locations.put(spb.getLocationName(), spb);
                }

                listener.onProcessEnds();
            }
        });

        thread.start();

    }
}
