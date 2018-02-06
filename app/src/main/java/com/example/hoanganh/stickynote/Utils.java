package com.example.hoanganh.stickynote;

import android.app.Activity;
import android.content.Intent;

import com.example.hoanganh.stickynote.R;

/**
 * Created by gilzard123 on 02/01/2018.
 */

public class Utils {
    private static int sTheme;
    public final static int THEME1 = 0;
    public final static int THEME2= 1;

    /** * Set the theme of the Activity, and restart it by creating a new Activity of the same type. */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME1: activity.setTheme(R.style.Default);;break;
            case THEME2: activity.setTheme(R.style.Light); break;
        }
    }
}

