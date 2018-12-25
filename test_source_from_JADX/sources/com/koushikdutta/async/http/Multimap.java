package com.koushikdutta.async.http;

import android.net.Uri;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class Multimap extends LinkedHashMap<String, List<String>> implements Iterable<NameValuePair> {
    private static final StringDecoder QUERY_DECODER = new C11401();
    private static final StringDecoder URL_DECODER = new C11412();

    public interface StringDecoder {
        String decode(String str);
    }

    /* renamed from: com.koushikdutta.async.http.Multimap$1 */
    static class C11401 implements StringDecoder {
        C11401() {
        }

        public String decode(String s) {
            return Uri.decode(s);
        }
    }

    /* renamed from: com.koushikdutta.async.http.Multimap$2 */
    static class C11412 implements StringDecoder {
        C11412() {
        }

        public String decode(String s) {
            return URLDecoder.decode(s);
        }
    }

    protected List<String> newList() {
        return new ArrayList();
    }

    public String getString(String name) {
        List<String> ret = (List) get(name);
        if (ret != null) {
            if (ret.size() != 0) {
                return (String) ret.get(0);
            }
        }
        return null;
    }

    public void add(String name, String value) {
        List<String> ret = (List) get(name);
        if (ret == null) {
            ret = newList();
            put(name, ret);
        }
        ret.add(value);
    }

    public void put(String name, String value) {
        List<String> ret = newList();
        ret.add(value);
        put(name, ret);
    }

    public Multimap(List<NameValuePair> pairs) {
        for (NameValuePair pair : pairs) {
            add(pair.getName(), pair.getValue());
        }
    }

    public Multimap(Multimap m) {
        putAll(m);
    }

    public static Multimap parse(String value, String delimiter, boolean unquote, StringDecoder decoder) {
        Multimap map = new Multimap();
        if (value == null) {
            return map;
        }
        for (String part : value.split(delimiter)) {
            String[] pair = part.split("=", 2);
            String key = pair[0].trim();
            String v = null;
            if (pair.length > 1) {
                v = pair[1];
            }
            if (unquote && v != null && v.endsWith("\"") && v.startsWith("\"")) {
                v = v.substring(1, v.length() - 1);
            }
            if (decoder != null) {
                key = decoder.decode(key);
                v = decoder.decode(v);
            }
            map.add(key, v);
        }
        return map;
    }

    public static Multimap parseSemicolonDelimited(String header) {
        return parse(header, ";", true, null);
    }

    public static Multimap parseCommaDelimited(String header) {
        return parse(header, ",", true, null);
    }

    public static Multimap parseQuery(String query) {
        return parse(query, "&", false, QUERY_DECODER);
    }

    public static Multimap parseUrlEncoded(String query) {
        return parse(query, "&", false, URL_DECODER);
    }

    public Iterator<NameValuePair> iterator() {
        ArrayList<NameValuePair> ret = new ArrayList();
        for (String name : keySet()) {
            for (String value : (List) get(name)) {
                ret.add(new BasicNameValuePair(name, value));
            }
        }
        return ret.iterator();
    }
}
