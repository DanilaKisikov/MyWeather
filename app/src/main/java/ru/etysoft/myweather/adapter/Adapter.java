package ru.etysoft.myweather.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.etysoft.myweather.R;
import ru.etysoft.myweather.location.Location;
import ru.etysoft.myweather.location.LocationHandler;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private List<Location> locationList = new ArrayList<>();

    private LayoutInflater inflater;

    public Adapter(ArrayList<Location> locationList, Context context) {
        this.locationList = locationList;
        inflater = LayoutInflater.from(context);
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(this.inflater.inflate(R.layout.location_item, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Location location = locationList.get(position);
        String name = location.getLocationName();
        holder.locationName.setText(name);

        holder.coloredRound.setText(String.valueOf(name.charAt(0)));
        holder.coloredRound.setBackgroundTintList(ColorStateList.valueOf(location.getColor()));

        if (!location.isCurrentLocation()) {
            holder.layout.setBackgroundTintList(ColorStateList.valueOf
                    (holder.layout.getContext().getColor(R.color.white)));

            holder.selectedLayout.setVisibility(View.GONE);
        }

        holder.layout.setOnTouchListener(new View.OnTouchListener() {

            Long startTime;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();

                long time;

                if (action == MotionEvent.ACTION_DOWN) {
                    startTime = System.currentTimeMillis();
                    return true;
                }

                if (action != MotionEvent.ACTION_UP) return true;

                if (System.currentTimeMillis() - startTime < 400) {
                    holder.layout.setBackgroundTintList(ColorStateList.valueOf
                            (holder.layout.getContext().getColor(R.color.super_light_blue)));
                    holder.selectedLayout.setVisibility(View.VISIBLE);

                    int i = LocationHandler.updateCurrentLocation(location);

                    notifyItemChanged(i);
                    return true;
                }

                // BottomSheet change

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.getLocationList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout;
        private TextView locationName;
        private TextView coloredRound;
        private LinearLayout selectedLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = (LinearLayout) itemView;
            this.locationName = itemView.findViewById(R.id.location_name);
            this.coloredRound = itemView.findViewById(R.id.colored_round);
            this.selectedLayout = itemView.findViewById(R.id.is_selected);
        }
    }
}
