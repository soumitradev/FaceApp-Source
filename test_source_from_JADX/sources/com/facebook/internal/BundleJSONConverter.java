package com.facebook.internal;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BundleJSONConverter {
    private static final Map<Class<?>, Setter> SETTERS = new HashMap();

    public interface Setter {
        void setOnBundle(Bundle bundle, String str, Object obj) throws JSONException;

        void setOnJSON(JSONObject jSONObject, String str, Object obj) throws JSONException;
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter$1 */
    static class C08251 implements Setter {
        C08251() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putBoolean(key, ((Boolean) value).booleanValue());
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            json.put(key, value);
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter$2 */
    static class C08262 implements Setter {
        C08262() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putInt(key, ((Integer) value).intValue());
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            json.put(key, value);
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter$3 */
    static class C08273 implements Setter {
        C08273() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putLong(key, ((Long) value).longValue());
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            json.put(key, value);
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter$4 */
    static class C08284 implements Setter {
        C08284() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putDouble(key, ((Double) value).doubleValue());
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            json.put(key, value);
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter$5 */
    static class C08295 implements Setter {
        C08295() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putString(key, (String) value);
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            json.put(key, value);
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter$6 */
    static class C08306 implements Setter {
        C08306() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            throw new IllegalArgumentException("Unexpected type from JSON");
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            JSONArray jsonArray = new JSONArray();
            for (String stringValue : (String[]) value) {
                jsonArray.put(stringValue);
            }
            json.put(key, jsonArray);
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter$7 */
    static class C08317 implements Setter {
        C08317() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            JSONArray jsonArray = (JSONArray) value;
            ArrayList<String> stringArrayList = new ArrayList();
            if (jsonArray.length() == 0) {
                bundle.putStringArrayList(key, stringArrayList);
                return;
            }
            int i = 0;
            while (i < jsonArray.length()) {
                Object current = jsonArray.get(i);
                if (current instanceof String) {
                    stringArrayList.add((String) current);
                    i++;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected type in an array: ");
                    stringBuilder.append(current.getClass());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            bundle.putStringArrayList(key, stringArrayList);
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            throw new IllegalArgumentException("JSONArray's are not supported in bundles.");
        }
    }

    static {
        SETTERS.put(Boolean.class, new C08251());
        SETTERS.put(Integer.class, new C08262());
        SETTERS.put(Long.class, new C08273());
        SETTERS.put(Double.class, new C08284());
        SETTERS.put(String.class, new C08295());
        SETTERS.put(String[].class, new C08306());
        SETTERS.put(JSONArray.class, new C08317());
    }

    public static JSONObject convertToJSON(Bundle bundle) throws JSONException {
        JSONObject json = new JSONObject();
        for (String key : bundle.keySet()) {
            List<String> value = bundle.get(key);
            if (value != null) {
                if (value instanceof List) {
                    JSONArray jsonArray = new JSONArray();
                    for (String stringValue : value) {
                        jsonArray.put(stringValue);
                    }
                    json.put(key, jsonArray);
                } else if (value instanceof Bundle) {
                    json.put(key, convertToJSON((Bundle) value));
                } else {
                    Setter setter = (Setter) SETTERS.get(value.getClass());
                    if (setter == null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported type: ");
                        stringBuilder.append(value.getClass());
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    setter.setOnJSON(json, key, value);
                }
            }
        }
        return json;
    }

    public static Bundle convertToBundle(JSONObject jsonObject) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator<String> jsonIterator = jsonObject.keys();
        while (jsonIterator.hasNext()) {
            String key = (String) jsonIterator.next();
            Object value = jsonObject.get(key);
            if (value != null) {
                if (value != JSONObject.NULL) {
                    if (value instanceof JSONObject) {
                        bundle.putBundle(key, convertToBundle((JSONObject) value));
                    } else {
                        Setter setter = (Setter) SETTERS.get(value.getClass());
                        if (setter == null) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unsupported type: ");
                            stringBuilder.append(value.getClass());
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        setter.setOnBundle(bundle, key, value);
                    }
                }
            }
        }
        return bundle;
    }
}
