package hr.foi.air.discountlocator.core;

import android.content.Context;

public class PreferenceManagerHelper {
    /**
     * Enables the change of the value of the preference for buying the map through in-app.
     * @param mapBought If true, the map will be available.
     * @param context   The application context.
     */
    public static void setMapBought(Boolean mapBought, Context context)
    {
        android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean("map_in_app", mapBought)
                .commit();
    }

    /**
     * Gets the value of the preference for map in-app.
     * @param context   The application context.
     * @return          If exists, returns stored value, otherwise, returns false.
     */
    public static boolean getMapBought(Context context)
    {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("map_in_app", false);
    }
}
