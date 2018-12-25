package com.koushikdutta.async.http;

import android.text.TextUtils;
import com.koushikdutta.async.util.TaggedList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Headers {
    final Multimap map = new C11321();

    /* renamed from: com.koushikdutta.async.http.Headers$1 */
    class C11321 extends Multimap {
        C11321() {
        }

        protected List<String> newList() {
            return new TaggedList();
        }
    }

    public Headers(Map<String, List<String>> mm) {
        this.map.putAll(mm);
    }

    public Multimap getMultiMap() {
        return this.map;
    }

    public List<String> getAll(String header) {
        return (List) this.map.get(header.toLowerCase(Locale.US));
    }

    public String get(String header) {
        return this.map.getString(header.toLowerCase(Locale.US));
    }

    public Headers set(String header, String value) {
        if (value == null || !(value.contains("\n") || value.contains("\r"))) {
            String lc = header.toLowerCase(Locale.US);
            this.map.put(lc, value);
            ((TaggedList) this.map.get(lc)).tagNull(header);
            return this;
        }
        throw new IllegalArgumentException("value must not contain a new line or line feed");
    }

    public Headers add(String header, String value) {
        String lc = header.toLowerCase(Locale.US);
        this.map.add(lc, value);
        ((TaggedList) this.map.get(lc)).tagNull(header);
        return this;
    }

    public Headers addLine(String line) {
        if (line != null) {
            String[] parts = line.trim().split(":", 2);
            if (parts.length == 2) {
                add(parts[0].trim(), parts[1].trim());
            } else {
                add(parts[0].trim(), "");
            }
        }
        return this;
    }

    public Headers addAll(String header, List<String> values) {
        for (String v : values) {
            add(header, v);
        }
        return this;
    }

    public Headers addAll(Map<String, List<String>> m) {
        for (String key : m.keySet()) {
            for (String value : (List) m.get(key)) {
                add(key, value);
            }
        }
        return this;
    }

    public Headers addAll(Headers headers) {
        this.map.putAll(headers.map);
        return this;
    }

    public List<String> removeAll(String header) {
        return (List) this.map.remove(header.toLowerCase(Locale.US));
    }

    public String remove(String header) {
        List<String> r = removeAll(header.toLowerCase(Locale.US));
        if (r != null) {
            if (r.size() != 0) {
                return (String) r.get(0);
            }
        }
        return null;
    }

    public Headers removeAll(Collection<String> headers) {
        for (String header : headers) {
            remove(header);
        }
        return this;
    }

    public StringBuilder toStringBuilder() {
        StringBuilder result = new StringBuilder(256);
        for (String key : this.map.keySet()) {
            TaggedList<String> list = (TaggedList) this.map.get(key);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String v = (String) it.next();
                result.append((String) list.tag());
                result.append(": ");
                result.append(v);
                result.append("\r\n");
            }
        }
        result.append("\r\n");
        return result;
    }

    public String toString() {
        return toStringBuilder().toString();
    }

    public String toPrefixString(String prefix) {
        StringBuilder toStringBuilder = toStringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append("\r\n");
        return toStringBuilder.insert(0, stringBuilder.toString()).toString();
    }

    public static Headers parse(String payload) {
        String[] lines = payload.split("\n");
        Headers headers = new Headers();
        for (String line : lines) {
            String line2 = line2.trim();
            if (!TextUtils.isEmpty(line2)) {
                headers.addLine(line2);
            }
        }
        return headers;
    }
}
