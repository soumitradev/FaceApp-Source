package javax.jmdns.impl;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class JmDNSImpl$ServiceTypeEntry extends AbstractMap<String, String> implements Cloneable {
    private final Set<Entry<String, String>> _entrySet = new HashSet();
    private final String _type;

    /* renamed from: javax.jmdns.impl.JmDNSImpl$ServiceTypeEntry$SubTypeEntry */
    private static class SubTypeEntry implements Entry<String, String>, Serializable, Cloneable {
        private static final long serialVersionUID = 9188503522395855322L;
        private final String _key;
        private final String _value;

        public SubTypeEntry(String subtype) {
            this._value = subtype != null ? subtype : "";
            this._key = this._value.toLowerCase();
        }

        public String getKey() {
            return this._key;
        }

        public String getValue() {
            return this._value;
        }

        public String setValue(String value) {
            throw new UnsupportedOperationException();
        }

        public boolean equals(Object entry) {
            boolean z = false;
            if (!(entry instanceof Entry)) {
                return false;
            }
            if (getKey().equals(((Entry) entry).getKey()) && getValue().equals(((Entry) entry).getValue())) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = this._key == null ? 0 : this._key.hashCode();
            if (this._value != null) {
                i = this._value.hashCode();
            }
            return hashCode ^ i;
        }

        public SubTypeEntry clone() {
            return this;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this._key);
            stringBuilder.append("=");
            stringBuilder.append(this._value);
            return stringBuilder.toString();
        }
    }

    public JmDNSImpl$ServiceTypeEntry(String type) {
        this._type = type;
    }

    public String getType() {
        return this._type;
    }

    public Set<Entry<String, String>> entrySet() {
        return this._entrySet;
    }

    public boolean contains(String subtype) {
        return subtype != null && containsKey(subtype.toLowerCase());
    }

    public boolean add(String subtype) {
        if (subtype != null) {
            if (!contains(subtype)) {
                this._entrySet.add(new SubTypeEntry(subtype));
                return true;
            }
        }
        return false;
    }

    public Iterator<String> iterator() {
        return keySet().iterator();
    }

    public JmDNSImpl$ServiceTypeEntry clone() {
        JmDNSImpl$ServiceTypeEntry entry = new JmDNSImpl$ServiceTypeEntry(getType());
        for (Entry<String, String> subTypeEntry : entrySet()) {
            entry.add((String) subTypeEntry.getValue());
        }
        return entry;
    }

    public String toString() {
        StringBuilder aLog = new StringBuilder(200);
        if (isEmpty()) {
            aLog.append("empty");
        } else {
            for (String value : values()) {
                aLog.append(value);
                aLog.append(", ");
            }
            aLog.setLength(aLog.length() - 2);
        }
        return aLog.toString();
    }
}
