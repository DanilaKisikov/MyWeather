package ru.etysoft.myweather.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.etysoft.myweather.R;
import ru.etysoft.myweather.databinding.ActivityMainBinding;
import ru.etysoft.myweather.location.LocationHandler;
import ru.etysoft.myweather.weather.WeatherObject;
import ru.etysoft.myweather.weather.WeekForecast;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;



    private static WeekForecast weekForecast;

    private TextView locationName;

    private LinearLayout mainLayout;
    private LinearLayout loadingLayout;
    private LinearLayout errorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        initViews();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isDestroyed())
                {
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {

                    }
                }
            }
        });
        thread.start();

        LocationHandler.initialiseLocations();
        locationName.setText(LocationHandler.getCurrentLocation().getLocationName());
        updateWeather();

    }

    private void initViews() {
        mainLayout = findViewById(R.id.main_layout);
        loadingLayout = findViewById(R.id.loading_layout);
        errorLayout = findViewById(R.id.error_layout);
        locationName = findViewById(R.id.location_name);
    }

    private void updateWeather() {
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
}