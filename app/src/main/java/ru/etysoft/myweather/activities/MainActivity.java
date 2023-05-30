package ru.etysoft.myweather.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import org.json.JSONException;

import java.io.IOException;

import ru.etysoft.myweather.R;
import ru.etysoft.myweather.weather.WeekForecast;

public class MainActivity extends AppCompatActivity {

    private WeekForecast weekForecast;

    private LinearLayout mainLayout;
    private LinearLayout loadingLayout;
    private LinearLayout errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        try {
            weekForecast = WeekForecast.getNewWeek();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong with WeatherGetter.getResponse");
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Something went wrong with JSON response");
        }


    }
}