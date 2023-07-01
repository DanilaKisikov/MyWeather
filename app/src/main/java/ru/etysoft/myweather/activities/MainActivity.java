package ru.etysoft.myweather.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;

import ru.etysoft.myweather.R;
import ru.etysoft.myweather.databinding.ActivityMainBinding;
import ru.etysoft.myweather.location.Location;
import ru.etysoft.myweather.location.LocationHandler;
import ru.etysoft.myweather.location.LocationLoadListener;
import ru.etysoft.myweather.weather.WeatherObject;
import ru.etysoft.myweather.weather.WeekForecast;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static WeekForecast weekForecast;

    private TextView locationName;

    private LinearLayout mainLayout;
    private LinearLayout loadingLayout;
    private LinearLayout errorLayout;

    private static FusedLocationProviderClient mFusedLocationClient;

    private static Location currentLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setContentView(binding.getRoot());

        initViews();

        weatherUpdater();

        localUpdater();

        LocationHandler.initialiseLocations(this, new LocationLoadListener() {
            @Override
            public void onProcessEnds() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        locationName.setText(LocationHandler.getCurrentLocation().getLocationName());
                        updateWeather();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LocationHandler.getCurrentLocation() != null) {
            locationName.setText(LocationHandler.getCurrentLocation().getLocationName());
            updateWeather();
        }
    }

    private void initViews() {
        mainLayout = findViewById(R.id.main_layout);
        loadingLayout = findViewById(R.id.loading_layout);
        errorLayout = findViewById(R.id.error_layout);
        locationName = findViewById(R.id.location_name);

        locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateWeather() {
        errorLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    weekForecast = WeekForecast.getNewWeek();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingLayout.setVisibility(View.GONE);
                            showMainView();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingLayout.setVisibility(View.GONE);
                            showErrorView();
                        }
                    });
                }
            }
        });
        thread.start();
    }

    private void showMainView() {
        WeatherObject currentWeather = weekForecast.getCurrentWeather();
        binding.lastUpdatedTime.setText(getString(R.string.updated_at) + " " +
                currentWeather.getDate().split(" ")[1]);

        binding.temperature.setText((int) currentWeather.getTemperature() + getString(R.string.degrees));

        binding.weatherType.setText(currentWeather.getCondition().toString());

        binding.windSpeed.setText((int) currentWeather.getWindSpeed() + " " + getString(R.string.km_h));

        binding.feelsLikeTemperature.setText((int) currentWeather.getFeelsLikeTemperature() +
                getString(R.string.degrees));

        mainLayout.setVisibility(View.VISIBLE);
    }

    private void showErrorView() {
        loadingLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        errorLayout.setVisibility(View.VISIBLE);
        LinearLayout retryButton = findViewById(R.id.retry_button);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorLayout.setVisibility(View.GONE);
                updateWeather();
            }
        });
    }

    private void localUpdater() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mFusedLocationClient = LocationServices.
                        getFusedLocationProviderClient(MainActivity.this);
                Calendar rightNow = Calendar.getInstance();
                while (!isDestroyed()) {
                    try {
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            throw new Exception();
                        }
                        Task<android.location.Location> task = mFusedLocationClient.getLastLocation()
                                .addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
                                    @Override
                                    public void onComplete(@NonNull Task<android.location.Location> task) {
                                        currentLocal = new Location();
                                        currentLocal.setLongitude(task.getResult().getLongitude());
                                        currentLocal.setLatitude(task.getResult().getLatitude());
                                    }
                                });
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showErrorView();
                            }
                        });
                    }
                    int minute = rightNow.get(Calendar.MINUTE);
                    long time = (15 - (minute % 15)) * 60000;
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        thread.start();
    }

    private void weatherUpdater() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Calendar rightNow = Calendar.getInstance();
                while (!isDestroyed())
                {
                    try {
                        int minute = rightNow.get(Calendar.MINUTE);
                        long time = (16 - (minute % 15)) * 60000;
                        Thread.sleep(time);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateWeather();
                            }
                        });
                    } catch (InterruptedException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showErrorView();
                            }
                        });
                    }
                }
            }
        });
        thread.start();
    }

    public static Location getCurrentLocal() {
        return currentLocal;
    }
}