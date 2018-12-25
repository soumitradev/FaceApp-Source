package org.catrobat.catroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;
import android.view.ViewGroup;
import com.github.mrengineer13.snackbar.SnackBar.Builder;
import com.github.mrengineer13.snackbar.SnackBar.OnMessageClickListener;
import java.util.HashSet;
import java.util.Set;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public final class SnackbarUtil {
    public static final String SHOWN_HINT_LIST = "shown_hint_list";
    private static ViewGroup activeSnack = null;

    private SnackbarUtil() {
    }

    public static void showHintSnackbar(final Activity activity, @StringRes int resourceId) {
        final String messageId = activity.getResources().getResourceName(resourceId);
        String message = activity.getString(resourceId);
        if (!wasHintAlreadyShown(activity, messageId) && areHintsEnabled(activity)) {
            activeSnack = (ViewGroup) new Builder(activity).withMessage(message).withActionMessage(activity.getResources().getString(R.string.got_it)).withTextColorId(R.color.solid_black).withBackgroundColorId(R.color.snackbar).withOnClickListener(new OnMessageClickListener() {
                public void onMessageClick(Parcelable token) {
                    SnackbarUtil.setHintShown(activity, messageId);
                }
            }).withDuration(Short.valueOf((short) 0)).show().getContainerView();
        }
    }

    public static void hideActiveSnack() {
        if (activeSnack != null) {
            activeSnack.setVisibility(4);
        }
    }

    public static void showActiveSnack() {
        if (activeSnack != null) {
            activeSnack.setVisibility(0);
        }
    }

    public static void setHintShown(Activity activity, String messageId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        Set<String> hintList = getStringSetFromSharedPreferences(activity);
        hintList.add(messageId);
        prefs.edit().putStringSet(SHOWN_HINT_LIST, hintList).commit();
    }

    public static boolean wasHintAlreadyShown(Activity activity, String messageId) {
        return getStringSetFromSharedPreferences(activity).contains(messageId);
    }

    public static boolean areHintsEnabled(Activity activity) {
        return PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(SettingsFragment.SETTINGS_SHOW_HINTS, false);
    }

    private static Set<String> getStringSetFromSharedPreferences(Context context) {
        return new HashSet(PreferenceManager.getDefaultSharedPreferences(context).getStringSet(SHOWN_HINT_LIST, new HashSet()));
    }
}
