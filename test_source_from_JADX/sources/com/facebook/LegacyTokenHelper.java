package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class LegacyTokenHelper {
    public static final String APPLICATION_ID_KEY = "com.facebook.TokenCachingStrategy.ApplicationId";
    public static final String DECLINED_PERMISSIONS_KEY = "com.facebook.TokenCachingStrategy.DeclinedPermissions";
    public static final String DEFAULT_CACHE_KEY = "com.facebook.SharedPreferencesTokenCachingStrategy.DEFAULT_KEY";
    public static final String EXPIRATION_DATE_KEY = "com.facebook.TokenCachingStrategy.ExpirationDate";
    private static final long INVALID_BUNDLE_MILLISECONDS = Long.MIN_VALUE;
    private static final String IS_SSO_KEY = "com.facebook.TokenCachingStrategy.IsSSO";
    private static final String JSON_VALUE = "value";
    private static final String JSON_VALUE_ENUM_TYPE = "enumType";
    private static final String JSON_VALUE_TYPE = "valueType";
    public static final String LAST_REFRESH_DATE_KEY = "com.facebook.TokenCachingStrategy.LastRefreshDate";
    public static final String PERMISSIONS_KEY = "com.facebook.TokenCachingStrategy.Permissions";
    private static final String TAG = LegacyTokenHelper.class.getSimpleName();
    public static final String TOKEN_KEY = "com.facebook.TokenCachingStrategy.Token";
    public static final String TOKEN_SOURCE_KEY = "com.facebook.TokenCachingStrategy.AccessTokenSource";
    private static final String TYPE_BOOLEAN = "bool";
    private static final String TYPE_BOOLEAN_ARRAY = "bool[]";
    private static final String TYPE_BYTE = "byte";
    private static final String TYPE_BYTE_ARRAY = "byte[]";
    private static final String TYPE_CHAR = "char";
    private static final String TYPE_CHAR_ARRAY = "char[]";
    private static final String TYPE_DOUBLE = "double";
    private static final String TYPE_DOUBLE_ARRAY = "double[]";
    private static final String TYPE_ENUM = "enum";
    private static final String TYPE_FLOAT = "float";
    private static final String TYPE_FLOAT_ARRAY = "float[]";
    private static final String TYPE_INTEGER = "int";
    private static final String TYPE_INTEGER_ARRAY = "int[]";
    private static final String TYPE_LONG = "long";
    private static final String TYPE_LONG_ARRAY = "long[]";
    private static final String TYPE_SHORT = "short";
    private static final String TYPE_SHORT_ARRAY = "short[]";
    private static final String TYPE_STRING = "string";
    private static final String TYPE_STRING_LIST = "stringList";
    private SharedPreferences cache;
    private String cacheKey;

    public LegacyTokenHelper(Context context) {
        this(context, null);
    }

    public LegacyTokenHelper(Context context, String cacheKey) {
        Validate.notNull(context, "context");
        this.cacheKey = Utility.isNullOrEmpty(cacheKey) ? DEFAULT_CACHE_KEY : cacheKey;
        Context applicationContext = context.getApplicationContext();
        this.cache = (applicationContext != null ? applicationContext : context).getSharedPreferences(this.cacheKey, 0);
    }

    public Bundle load() {
        Bundle settings = new Bundle();
        for (String key : this.cache.getAll().keySet()) {
            try {
                deserializeKey(key, settings);
            } catch (JSONException e) {
                LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error reading cached value for key: '");
                stringBuilder.append(key);
                stringBuilder.append("' -- ");
                stringBuilder.append(e);
                Logger.log(loggingBehavior, 5, str, stringBuilder.toString());
                return null;
            }
        }
        return settings;
    }

    public void save(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        Editor editor = this.cache.edit();
        for (String key : bundle.keySet()) {
            try {
                serializeKey(key, bundle, editor);
            } catch (JSONException e) {
                LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error processing value for key: '");
                stringBuilder.append(key);
                stringBuilder.append("' -- ");
                stringBuilder.append(e);
                Logger.log(loggingBehavior, 5, str, stringBuilder.toString());
                return;
            }
        }
        editor.apply();
    }

    public void clear() {
        this.cache.edit().clear().apply();
    }

    public static boolean hasTokenInformation(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        String token = bundle.getString(TOKEN_KEY);
        if (token != null) {
            if (token.length() != 0) {
                if (bundle.getLong(EXPIRATION_DATE_KEY, 0) == 0) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public static String getToken(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        return bundle.getString(TOKEN_KEY);
    }

    public static void putToken(Bundle bundle, String value) {
        Validate.notNull(bundle, "bundle");
        Validate.notNull(value, "value");
        bundle.putString(TOKEN_KEY, value);
    }

    public static Date getExpirationDate(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        return getDate(bundle, EXPIRATION_DATE_KEY);
    }

    public static void putExpirationDate(Bundle bundle, Date value) {
        Validate.notNull(bundle, "bundle");
        Validate.notNull(value, "value");
        putDate(bundle, EXPIRATION_DATE_KEY, value);
    }

    public static long getExpirationMilliseconds(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        return bundle.getLong(EXPIRATION_DATE_KEY);
    }

    public static void putExpirationMilliseconds(Bundle bundle, long value) {
        Validate.notNull(bundle, "bundle");
        bundle.putLong(EXPIRATION_DATE_KEY, value);
    }

    public static Set<String> getPermissions(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        ArrayList<String> arrayList = bundle.getStringArrayList(PERMISSIONS_KEY);
        if (arrayList == null) {
            return null;
        }
        return new HashSet(arrayList);
    }

    public static void putPermissions(Bundle bundle, Collection<String> value) {
        Validate.notNull(bundle, "bundle");
        Validate.notNull(value, "value");
        bundle.putStringArrayList(PERMISSIONS_KEY, new ArrayList(value));
    }

    public static void putDeclinedPermissions(Bundle bundle, Collection<String> value) {
        Validate.notNull(bundle, "bundle");
        Validate.notNull(value, "value");
        bundle.putStringArrayList(DECLINED_PERMISSIONS_KEY, new ArrayList(value));
    }

    public static AccessTokenSource getSource(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        if (bundle.containsKey(TOKEN_SOURCE_KEY)) {
            return (AccessTokenSource) bundle.getSerializable(TOKEN_SOURCE_KEY);
        }
        return bundle.getBoolean(IS_SSO_KEY) ? AccessTokenSource.FACEBOOK_APPLICATION_WEB : AccessTokenSource.WEB_VIEW;
    }

    public static void putSource(Bundle bundle, AccessTokenSource value) {
        Validate.notNull(bundle, "bundle");
        bundle.putSerializable(TOKEN_SOURCE_KEY, value);
    }

    public static Date getLastRefreshDate(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        return getDate(bundle, LAST_REFRESH_DATE_KEY);
    }

    public static void putLastRefreshDate(Bundle bundle, Date value) {
        Validate.notNull(bundle, "bundle");
        Validate.notNull(value, "value");
        putDate(bundle, LAST_REFRESH_DATE_KEY, value);
    }

    public static long getLastRefreshMilliseconds(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        return bundle.getLong(LAST_REFRESH_DATE_KEY);
    }

    public static void putLastRefreshMilliseconds(Bundle bundle, long value) {
        Validate.notNull(bundle, "bundle");
        bundle.putLong(LAST_REFRESH_DATE_KEY, value);
    }

    public static String getApplicationId(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        return bundle.getString(APPLICATION_ID_KEY);
    }

    public static void putApplicationId(Bundle bundle, String value) {
        Validate.notNull(bundle, "bundle");
        bundle.putString(APPLICATION_ID_KEY, value);
    }

    static Date getDate(Bundle bundle, String key) {
        if (bundle == null) {
            return null;
        }
        long n = bundle.getLong(key, Long.MIN_VALUE);
        if (n == Long.MIN_VALUE) {
            return null;
        }
        return new Date(n);
    }

    static void putDate(Bundle bundle, String key, Date date) {
        bundle.putLong(key, date.getTime());
    }

    private void serializeKey(String key, Bundle bundle, Editor editor) throws JSONException {
        List<String> value = bundle.get(key);
        if (value != null) {
            String supportedType = null;
            JSONArray jsonArray = null;
            JSONObject json = new JSONObject();
            if (value instanceof Byte) {
                supportedType = TYPE_BYTE;
                json.put("value", ((Byte) value).intValue());
            } else if (value instanceof Short) {
                supportedType = TYPE_SHORT;
                json.put("value", ((Short) value).intValue());
            } else if (value instanceof Integer) {
                supportedType = TYPE_INTEGER;
                json.put("value", ((Integer) value).intValue());
            } else if (value instanceof Long) {
                supportedType = TYPE_LONG;
                json.put("value", ((Long) value).longValue());
            } else if (value instanceof Float) {
                supportedType = TYPE_FLOAT;
                json.put("value", ((Float) value).doubleValue());
            } else if (value instanceof Double) {
                supportedType = TYPE_DOUBLE;
                json.put("value", ((Double) value).doubleValue());
            } else if (value instanceof Boolean) {
                supportedType = TYPE_BOOLEAN;
                json.put("value", ((Boolean) value).booleanValue());
            } else if (value instanceof Character) {
                supportedType = TYPE_CHAR;
                json.put("value", value.toString());
            } else if (value instanceof String) {
                supportedType = TYPE_STRING;
                json.put("value", (String) value);
            } else if (value instanceof Enum) {
                supportedType = TYPE_ENUM;
                json.put("value", value.toString());
                json.put(JSON_VALUE_ENUM_TYPE, value.getClass().getName());
            } else {
                jsonArray = new JSONArray();
                int i$ = 0;
                int len$;
                if (value instanceof byte[]) {
                    supportedType = TYPE_BYTE_ARRAY;
                    byte[] arr$ = (byte[]) value;
                    len$ = arr$.length;
                    while (i$ < len$) {
                        jsonArray.put(arr$[i$]);
                        i$++;
                    }
                } else if (value instanceof short[]) {
                    supportedType = TYPE_SHORT_ARRAY;
                    short[] arr$2 = (short[]) value;
                    len$ = arr$2.length;
                    while (i$ < len$) {
                        jsonArray.put(arr$2[i$]);
                        i$++;
                    }
                } else if (value instanceof int[]) {
                    supportedType = TYPE_INTEGER_ARRAY;
                    int[] arr$3 = (int[]) value;
                    len$ = arr$3.length;
                    while (i$ < len$) {
                        jsonArray.put(arr$3[i$]);
                        i$++;
                    }
                } else if (value instanceof long[]) {
                    supportedType = TYPE_LONG_ARRAY;
                    long[] arr$4 = (long[]) value;
                    len$ = arr$4.length;
                    while (i$ < len$) {
                        jsonArray.put(arr$4[i$]);
                        i$++;
                    }
                } else if (value instanceof float[]) {
                    supportedType = TYPE_FLOAT_ARRAY;
                    float[] arr$5 = (float[]) value;
                    len$ = arr$5.length;
                    while (i$ < len$) {
                        jsonArray.put((double) arr$5[i$]);
                        i$++;
                    }
                } else if (value instanceof double[]) {
                    supportedType = TYPE_DOUBLE_ARRAY;
                    double[] arr$6 = (double[]) value;
                    len$ = arr$6.length;
                    while (i$ < len$) {
                        jsonArray.put(arr$6[i$]);
                        i$++;
                    }
                } else if (value instanceof boolean[]) {
                    supportedType = TYPE_BOOLEAN_ARRAY;
                    boolean[] arr$7 = (boolean[]) value;
                    len$ = arr$7.length;
                    while (i$ < len$) {
                        jsonArray.put(arr$7[i$]);
                        i$++;
                    }
                } else if (value instanceof char[]) {
                    supportedType = TYPE_CHAR_ARRAY;
                    char[] arr$8 = (char[]) value;
                    len$ = arr$8.length;
                    while (i$ < len$) {
                        jsonArray.put(String.valueOf(arr$8[i$]));
                        i$++;
                    }
                } else if (value instanceof List) {
                    supportedType = TYPE_STRING_LIST;
                    for (String v : value) {
                        jsonArray.put(v == null ? JSONObject.NULL : v);
                    }
                } else {
                    jsonArray = null;
                }
            }
            if (supportedType != null) {
                json.put(JSON_VALUE_TYPE, supportedType);
                if (jsonArray != null) {
                    json.putOpt("value", jsonArray);
                }
                editor.putString(key, json.toString());
            }
        }
    }

    private void deserializeKey(String key, Bundle bundle) throws JSONException {
        JSONObject json = new JSONObject(this.cache.getString(key, "{}"));
        String valueType = json.getString(JSON_VALUE_TYPE);
        if (valueType.equals(TYPE_BOOLEAN)) {
            bundle.putBoolean(key, json.getBoolean("value"));
            return;
        }
        int i = 0;
        JSONArray jsonArray;
        if (valueType.equals(TYPE_BOOLEAN_ARRAY)) {
            jsonArray = json.getJSONArray("value");
            boolean[] array = new boolean[jsonArray.length()];
            while (i < array.length) {
                array[i] = jsonArray.getBoolean(i);
                i++;
            }
            bundle.putBooleanArray(key, array);
        } else if (valueType.equals(TYPE_BYTE)) {
            bundle.putByte(key, (byte) json.getInt("value"));
        } else if (valueType.equals(TYPE_BYTE_ARRAY)) {
            jsonArray = json.getJSONArray("value");
            byte[] array2 = new byte[jsonArray.length()];
            while (i < array2.length) {
                array2[i] = (byte) jsonArray.getInt(i);
                i++;
            }
            bundle.putByteArray(key, array2);
        } else if (valueType.equals(TYPE_SHORT)) {
            bundle.putShort(key, (short) json.getInt("value"));
        } else if (valueType.equals(TYPE_SHORT_ARRAY)) {
            jsonArray = json.getJSONArray("value");
            short[] array3 = new short[jsonArray.length()];
            while (i < array3.length) {
                array3[i] = (short) jsonArray.getInt(i);
                i++;
            }
            bundle.putShortArray(key, array3);
        } else if (valueType.equals(TYPE_INTEGER)) {
            bundle.putInt(key, json.getInt("value"));
        } else if (valueType.equals(TYPE_INTEGER_ARRAY)) {
            jsonArray = json.getJSONArray("value");
            int[] array4 = new int[jsonArray.length()];
            while (i < array4.length) {
                array4[i] = jsonArray.getInt(i);
                i++;
            }
            bundle.putIntArray(key, array4);
        } else if (valueType.equals(TYPE_LONG)) {
            bundle.putLong(key, json.getLong("value"));
        } else if (valueType.equals(TYPE_LONG_ARRAY)) {
            jsonArray = json.getJSONArray("value");
            long[] array5 = new long[jsonArray.length()];
            while (i < array5.length) {
                array5[i] = jsonArray.getLong(i);
                i++;
            }
            bundle.putLongArray(key, array5);
        } else if (valueType.equals(TYPE_FLOAT)) {
            bundle.putFloat(key, (float) json.getDouble("value"));
        } else if (valueType.equals(TYPE_FLOAT_ARRAY)) {
            jsonArray = json.getJSONArray("value");
            float[] array6 = new float[jsonArray.length()];
            while (i < array6.length) {
                array6[i] = (float) jsonArray.getDouble(i);
                i++;
            }
            bundle.putFloatArray(key, array6);
        } else if (valueType.equals(TYPE_DOUBLE)) {
            bundle.putDouble(key, json.getDouble("value"));
        } else if (valueType.equals(TYPE_DOUBLE_ARRAY)) {
            jsonArray = json.getJSONArray("value");
            double[] array7 = new double[jsonArray.length()];
            while (i < array7.length) {
                array7[i] = jsonArray.getDouble(i);
                i++;
            }
            bundle.putDoubleArray(key, array7);
        } else if (valueType.equals(TYPE_CHAR)) {
            String charString = json.getString("value");
            if (charString != null && charString.length() == 1) {
                bundle.putChar(key, charString.charAt(0));
            }
        } else if (valueType.equals(TYPE_CHAR_ARRAY)) {
            jsonArray = json.getJSONArray("value");
            char[] array8 = new char[jsonArray.length()];
            for (int i2 = 0; i2 < array8.length; i2++) {
                String charString2 = jsonArray.getString(i2);
                if (charString2 != null && charString2.length() == 1) {
                    array8[i2] = charString2.charAt(0);
                }
            }
            bundle.putCharArray(key, array8);
        } else if (valueType.equals(TYPE_STRING)) {
            bundle.putString(key, json.getString("value"));
        } else if (valueType.equals(TYPE_STRING_LIST)) {
            jsonArray = json.getJSONArray("value");
            int numStrings = jsonArray.length();
            ArrayList<String> stringList = new ArrayList(numStrings);
            while (i < numStrings) {
                Object jsonStringValue = jsonArray.get(i);
                stringList.add(i, jsonStringValue == JSONObject.NULL ? null : (String) jsonStringValue);
                i++;
            }
            bundle.putStringArrayList(key, stringList);
        } else if (valueType.equals(TYPE_ENUM)) {
            try {
                bundle.putSerializable(key, Enum.valueOf(Class.forName(json.getString(JSON_VALUE_ENUM_TYPE)), json.getString("value")));
            } catch (ClassNotFoundException e) {
            } catch (IllegalArgumentException e2) {
            }
        }
    }
}