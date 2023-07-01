package ru.etysoft.myweather.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import ru.etysoft.myweather.R;
import ru.etysoft.myweather.adapter.Adapter;
import ru.etysoft.myweather.location.LocationHandler;

public class LocationActivity extends AppCompatActivity {

    private ImageView arrowBack;

    private ImageView addButton;

    private RecyclerView recycler;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initViews() {
        arrowBack = findViewById(R.id.arrow_back);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new Adapter(LocationHandler.getLocations(), this);
        recycler = findViewById(R.id.recycler);
        recycler.setAdapter(adapter);

        addButton = findViewById(R.id.add_location);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // BottomSheet add
            }
        });
    }
}