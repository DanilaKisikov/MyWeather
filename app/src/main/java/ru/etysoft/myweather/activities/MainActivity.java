package ru.etysoft.myweather.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import ru.etysoft.myweather.R;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private LinearLayout loadingLayout;
    private LinearLayout errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


    }
}