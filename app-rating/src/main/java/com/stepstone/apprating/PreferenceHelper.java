package com.stepstone.apprating;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Date;

final class PreferenceHelper {

    private static final String PREF_FILE_NAME = "android_rate_pref_file";

    private static final String PREF_KEY_INSTALL_DATE = "android_rate_install_date";

    private static final String PREF_KEY_LAUNCH_TIMES = "android_rate_launch_times";

    private static final String PREF_KEY_IS_AGREE_SHOW_DIALOG = "android_rate_is_agree_show_dialog";

    private static final String PREF_KEY_REMIND_INTERVAL = "android_rate_remind_interval";

    private PreferenceHelper() {
    }

    static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    static Editor getPreferencesEditor(Context context) {
        return getPreferences(context).edit();
    }

    /**
     * Clear data in shared preferences.<br/>
     *
     * @param context context
     */
    static void clearSharedPreferences(Context context) {
        Editor editor = getPreferencesEditor(context);
        editor.remove(PREF_KEY_INSTALL_DATE);
        editor.remove(PREF_KEY_LAUNCH_TIMES);
        editor.apply();
    }

    /**
     * Set agree flag about show dialog.<br/>
     * If it is false, rate dialog will never shown unless data is cleared.
     *
     * @param context context
     * @param isAgree agree with showing rate dialog
     */
    static void setAgreeShowDialog(Context context, boolean isAgree) {
        getPreferencesEditor(context).putBoolean(PREF_KEY_IS_AGREE_SHOW_DIALOG, isAgree).apply();
    }

    static boolean getIsAgreeShowDialog(Context context) {
        return getPreferences(context).getBoolean(PREF_KEY_IS_AGREE_SHOW_DIALOG, true);
    }

    static void setRemindInterval(Context context) {
        Editor editor = getPreferencesEditor(context);
        editor.remove(PREF_KEY_REMIND_INTERVAL);
        editor.putLong(PREF_KEY_REMIND_INTERVAL, new Date().getTime());
        editor.apply();
    }

    static long getRemindInterval(Context context) {
        return getPreferences(context).getLong(PREF_KEY_REMIND_INTERVAL, 0);
    }

    static void setInstallDate(Context context) {
        getPreferencesEditor(context).putLong(PREF_KEY_INSTALL_DATE, new Date().getTime()).apply();
    }

    static long getInstallDate(Context context) {
        return getPreferences(context).getLong(PREF_KEY_INSTALL_DATE, 0);
    }

    static void setLaunchTimes(Context context, int launchTimes) {
        getPreferencesEditor(context).putInt(PREF_KEY_LAUNCH_TIMES, launchTimes).apply();
    }

    static int getLaunchTimes(Context context) {
        return getPreferences(context).getInt(PREF_KEY_LAUNCH_TIMES, 0);
    }

    static boolean isFirstLaunch(Context context) {
        return getPreferences(context).getLong(PREF_KEY_INSTALL_DATE, 0) == 0L;
    }

}