package ru.etysoft.myweather.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import ru.etysoft.myweather.R;
import ru.etysoft.myweather.adapter.Adapter;
import ru.etysoft.myweather.location.Location;
import ru.etysoft.myweather.location.LocationHandler;

public class LocationActivity extends AppCompatActivity {

    private ImageView arrowBack;

    private RecyclerView recycler;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        initArrowBack();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initArrowBack() {
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
    }


}