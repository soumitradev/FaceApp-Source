package org.catrobat.catroid.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import java.util.Locale;

public final class UtilDeviceInfo {
    private static final String SERVER_VALUE_FOR_UNDEFINED_COUNTRY = "aq";

    private UtilDeviceInfo() {
        throw new AssertionError();
    }

    public static String getUserEmail(Context context) {
        if (context == null) {
            return null;
        }
        Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
        if (accounts.length > 0) {
            return accounts[0].name;
        }
        return null;
    }

    public static String getUserLanguageCode() {
        return Locale.getDefault().getLanguage();
    }

    public static String getUserCountryCode() {
        String country = Locale.getDefault().getCountry();
        if (country.isEmpty()) {
            return SERVER_VALUE_FOR_UNDEFINED_COUNTRY;
        }
        return country;
    }
}
