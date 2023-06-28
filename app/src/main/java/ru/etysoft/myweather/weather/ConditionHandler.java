package ru.etysoft.myweather.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import ru.etysoft.myweather.R;

public class ConditionHandler {

    public static Drawable getIcon(Condition condition, Context context) {
        Drawable drawable = null;
        switch (condition) {
            case CLOUDY:
                drawable = context.getDrawable(R.drawable.cloudy);
                break;
            case CLEAR:
        }
        return drawable;
    }

//    public static String getString(Condition condition) {
//        String result = condition.toString().toLowerCase();
//
//        result
//    }
}
