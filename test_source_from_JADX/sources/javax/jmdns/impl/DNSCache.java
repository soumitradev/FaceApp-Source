package javax.jmdns.impl;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

public class DNSCache extends AbstractMap<String, List<? extends DNSEntry>> {
    public static final DNSCache EmptyCache = new _EmptyCache();
    private transient Set<Entry<String, List<? extends DNSEntry>>> _entrySet;

    protected static class _CacheEntry implements Entry<String, List<? extends DNSEntry>> {
        private String _key;
        private List<? extends DNSEntry> _value;

        protected _CacheEntry(String key, List<? extends DNSEntry> value) {
            this._key = key != null ? key.trim().toLowerCase() : null;
            this._value = value;
        }

        protected _CacheEntry(Entry<String, List<? extends DNSEntry>> entry) {
            if (entry instanceof _CacheEntry) {
                this._key = ((_CacheEntry) entry).getKey();
                this._value = ((_CacheEntry) entry).getValue();
            }
        }

        public String getKey() {
            return this._key != null ? this._key : "";
        }

        public List<? extends DNSEntry> getValue() {
            return this._value;
        }

        public List<? extends DNSEntry> setValue(List<? extends DNSEntry> value) {
            List<? extends DNSEntry> oldValue = this._value;
            this._value = value;
            return oldValue;
        }

        public boolean isEmpty() {
            return getValue().isEmpty();
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
            return this._key == null ? 0 : this._key.hashCode();
        }

        public synchronized String toString() {
            StringBuffer aLog;
            aLog = new StringBuffer(200);
            aLog.append("\n\t\tname '");
            aLog.append(this._key);
            aLog.append("' ");
            if (this._value == null || this._value.isEmpty()) {
                aLog.append(" no entries");
            } else {
                for (DNSEntry entry : this._value) {
                    aLog.append("\n\t\t\t");
                    aLog.append(entry.toString());
                }
            }
            return aLog.toString();
        }
    }

    static final class _EmptyCache extends DNSCache {
        _EmptyCache() {
        }

        public int size() {
            return 0;
        }

        public boolean isEmpty() {
            return true;
        }

        public boolean containsKey(Object key) {
            return false;
        }

        public boolean containsValue(Object value) {
            return false;
        }

        public List<DNSEntry> get(Object key) {
            return null;
        }

        public Set<String> keySet() {
            return Collections.emptySet();
        }

        public Collection<List<? extends DNSEntry>> values() {
            return Collections.emptySet();
        }

        public Set<Entry<String, List<? extends DNSEntry>>> entrySet() {
            return Collections.emptySet();
        }

        public boolean equals(Object o) {
            return (o instanceof Map) && ((Map) o).size() == 0;
        }

        public List<? extends DNSEntry> put(String key, List<? extends DNSEntry> list) {
            return null;
        }

        public int hashCode() {
            return 0;
        }
    }

    public DNSCache() {
        this(1024);
    }

    public DNSCache(DNSCache map) {
        this(map != null ? map.size() : 1024);
        if (map != null) {
            putAll(map);
        }
    }

    public DNSCache(int initialCapacity) {
        this._entrySet = null;
        this._entrySet = new HashSet(initialCapacity);
    }

    public Set<Entry<String, List<? extends DNSEntry>>> entrySet() {
        if (this._entrySet == null) {
            this._entrySet = new HashSet();
        }
        return this._entrySet;
    }

    protected Entry<String, List<? extends DNSEntry>> getEntry(String key) {
        String stringKey = key != null ? key.trim().toLowerCase() : null;
        for (Entry<String, List<? extends DNSEntry>> entry : entrySet()) {
            if (stringKey != null) {
                if (stringKey.equals(entry.getKey())) {
                    return entry;
                }
            } else if (entry.getKey() == null) {
                return entry;
            }
        }
        return null;
    }

    public List<? extends DNSEntry> put(String key, List<? extends DNSEntry> value) {
        List<? extends DNSEntry> oldValue;
        synchronized (this) {
            oldValue = null;
            Entry<String, List<? extends DNSEntry>> oldEntry = getEntry(key);
            if (oldEntry != null) {
                oldValue = (List) oldEntry.setValue(value);
            } else {
                entrySet().add(new _CacheEntry(key, value));
            }
        }
        return oldValue;
    }

    protected Object clone() throws CloneNotSupportedException {
        return new DNSCache(this);
    }

    public synchronized Collection<DNSEntry> allValues() {
        List<DNSEntry> allValues;
        allValues = new ArrayList();
        for (List<? extends DNSEntry> entry : values()) {
            if (entry != null) {
                allValues.addAll(entry);
            }
        }
        return allValues;
    }

    public synchronized Collection<? extends DNSEntry> getDNSEntryList(String name) {
        Collection<? extends DNSEntry> entryList;
        entryList = _getDNSEntryList(name);
        if (entryList != null) {
            entryList = new ArrayList(entryList);
        } else {
            entryList = Collections.emptyList();
        }
        return entryList;
    }

    private Collection<? extends DNSEntry> _getDNSEntryList(String name) {
        return (Collection) get(name != null ? name.toLowerCase() : null);
    }

    public synchronized DNSEntry getDNSEntry(DNSEntry dnsEntry) {
        DNSEntry result;
        result = null;
        if (dnsEntry != null) {
            Collection<? extends DNSEntry> entryList = _getDNSEntryList(dnsEntry.getKey());
            if (entryList != null) {
                for (DNSEntry testDNSEntry : entryList) {
                    if (testDNSEntry.isSameEntry(dnsEntry)) {
                        result = testDNSEntry;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public synchronized DNSEntry getDNSEntry(String name, DNSRecordType type, DNSRecordClass recordClass) {
        DNSEntry result;
        result = null;
        Collection<? extends DNSEntry> entryList = _getDNSEntryList(name);
        if (entryList != null) {
            for (DNSEntry testDNSEntry : entryList) {
                if (testDNSEntry.getRecordType().equals(type) && (DNSRecordClass.CLASS_ANY == recordClass || testDNSEntry.getRecordClass().equals(recordClass))) {
                    result = testDNSEntry;
                    break;
                }
            }
        }
        return result;
    }

    public synchronized Collection<? extends DNSEntry> getDNSEntryList(String name, DNSRecordType type, DNSRecordClass recordClass) {
        Collection<? extends DNSEntry> entryList;
        entryList = _getDNSEntryList(name);
        if (entryList != null) {
            entryList = new ArrayList(entryList);
            Iterator<? extends DNSEntry> i = entryList.iterator();
            while (i.hasNext()) {
                DNSEntry testDNSEntry = (DNSEntry) i.next();
                if (!(testDNSEntry.getRecordType().equals(type) && (DNSRecordClass.CLASS_ANY == recordClass || testDNSEntry.getRecordClass().equals(recordClass)))) {
                    i.remove();
                }
            }
        } else {
            entryList = Collections.emptyList();
        }
        return entryList;
    }

    public synchronized boolean addDNSEntry(DNSEntry dnsEntry) {
        boolean result;
        result = false;
        if (dnsEntry != null) {
            List<DNSEntry> aNewValue;
            Entry<String, List<? extends DNSEntry>> oldEntry = getEntry(dnsEntry.getKey());
            if (oldEntry != null) {
                aNewValue = new ArrayList((Collection) oldEntry.getValue());
            } else {
                aNewValue = new ArrayList();
            }
            aNewValue.add(dnsEntry);
            if (oldEntry != null) {
                oldEntry.setValue(aNewValue);
            } else {
                entrySet().add(new _CacheEntry(dnsEntry.getKey(), aNewValue));
            }
            result = true;
        }
        return result;
    }

    public synchronized boolean removeDNSEntry(DNSEntry dnsEntry) {
        boolean result;
        result = false;
        if (dnsEntry != null) {
            Entry<String, List<? extends DNSEntry>> existingEntry = getEntry(dnsEntry.getKey());
            if (existingEntry != null) {
                result = ((List) existingEntry.getValue()).remove(dnsEntry);
                if (((List) existingEntry.getValue()).isEmpty()) {
                    entrySet().remove(existingEntry);
                }
            }
        }
        return result;
    }

    public synchronized boolean replaceDNSEntry(DNSEntry newDNSEntry, DNSEntry existingDNSEntry) {
        boolean result;
        result = false;
        if (!(newDNSEntry == null || existingDNSEntry == null)) {
            if (newDNSEntry.getKey().equals(existingDNSEntry.getKey())) {
                List<DNSEntry> aNewValue;
                Entry<String, List<? extends DNSEntry>> oldEntry = getEntry(newDNSEntry.getKey());
                if (oldEntry != null) {
                    aNewValue = new ArrayList((Collection) oldEntry.getValue());
                } else {
                    aNewValue = new ArrayList();
                }
                aNewValue.remove(existingDNSEntry);
                aNewValue.add(newDNSEntry);
                if (oldEntry != null) {
                    oldEntry.setValue(aNewValue);
                } else {
                    entrySet().add(new _CacheEntry(newDNSEntry.getKey(), aNewValue));
                }
                result = true;
            }
        }
        return result;
    }

    public synchronized String toString() {
        StringBuffer aLog;
        aLog = new StringBuffer(2000);
        aLog.append("\t---- cache ----");
        for (Entry<String, List<? extends DNSEntry>> entry : entrySet()) {
            aLog.append("\n\t\t");
            aLog.append(entry.toString());
        }
        return aLog.toString();
    }
}
