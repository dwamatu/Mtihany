package com.example.white.mtihany.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefSettings {
    /**
     * Boolean indicating whether ToS has been accepted.
     */
    public static final String NEXT_PRESSED = "next_status";


    public static boolean isPressed(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(NEXT_PRESSED, false);
    }

    public static void markPressedin(final Context context, boolean newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(NEXT_PRESSED, newValue).apply();
    }
}
