package org.catrobat.catroid.common;

public abstract class DroneConfigPreference {
    public static final String TAG = DroneConfigPreference.class.getSimpleName();

    public enum Preferences {
        FIRST,
        SECOND,
        THIRD,
        FOURTH;

        public static String[] getPreferenceCodes() {
            String[] valueStrings = new String[values().length];
            for (int i = 0; i < values().length; i++) {
                valueStrings[i] = values()[i].name();
            }
            return valueStrings;
        }

        public String getPreferenceCode() {
            return getPreferenceCode(this);
        }

        public static String getPreferenceCode(Preferences preference) {
            return preference.name();
        }

        public static Preferences getPreferenceFromPreferenceCode(String preferenceCode) {
            if (preferenceCode == null) {
                return FIRST;
            }
            try {
                return valueOf(preferenceCode);
            } catch (IllegalArgumentException e) {
                return FIRST;
            }
        }
    }
}
