package com.thoughtworks.xstream.core.util;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakCache extends AbstractMap {
    private final Map map;

    private interface Visitor {
        Object visit(Object obj);
    }

    public WeakCache() {
        this(new WeakHashMap());
    }

    public WeakCache(Map map) {
        this.map = map;
    }

    public Object get(Object key) {
        Reference reference = (Reference) this.map.get(key);
        return reference != null ? reference.get() : null;
    }

    public Object put(Object key, Object value) {
        Reference ref = (Reference) this.map.put(key, createReference(value));
        return ref == null ? null : ref.get();
    }

    public Object remove(Object key) {
        Reference ref = (Reference) this.map.remove(key);
        return ref == null ? null : ref.get();
    }

    protected Reference createReference(Object value) {
        return new WeakReference(value);
    }

    public boolean containsValue(final Object value) {
        if (((Boolean) iterate(new Visitor() {
            public Object visit(Object element) {
                return element.equals(value) ? Boolean.TRUE : null;
            }
        }, 0)) == Boolean.TRUE) {
            return true;
        }
        return false;
    }

    public int size() {
        if (this.map.size() == 0) {
            return 0;
        }
        final int[] i = new int[]{0};
        iterate(new Visitor() {
            public Object visit(Object element) {
                int[] iArr = i;
                iArr[0] = iArr[0] + 1;
                return null;
            }
        }, 0);
        return i[0];
    }

    public Collection values() {
        final Collection collection = new ArrayList();
        if (this.map.size() != 0) {
            iterate(new Visitor() {
                public Object visit(Object element) {
                    collection.add(element);
                    return null;
                }
            }, 0);
        }
        return collection;
    }

    public Set entrySet() {
        final Set set = new HashSet();
        if (this.map.size() != 0) {
            iterate(new Visitor() {
                public Object visit(Object element) {
                    final Entry entry = (Entry) element;
                    set.add(new Entry() {
                        public Object getKey() {
                            return entry.getKey();
                        }

                        public Object getValue() {
                            return ((Reference) entry.getValue()).get();
                        }

                        public Object setValue(Object value) {
                            return entry.setValue(WeakCache.this.createReference(value));
                        }
                    });
                    return null;
                }
            }, 2);
        }
        return set;
    }

    private Object iterate(Visitor visitor, int type) {
        Object result = null;
        Iterator iter = this.map.entrySet().iterator();
        while (result == null && iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            Object element = ((Reference) entry.getValue()).get();
            if (element != null) {
                switch (type) {
                    case 0:
                        result = visitor.visit(element);
                        break;
                    case 1:
                        result = visitor.visit(entry.getKey());
                        break;
                    case 2:
                        result = visitor.visit(entry);
                        break;
                    default:
                        break;
                }
            }
            iter.remove();
        }
        return result;
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public void clear() {
        this.map.clear();
    }

    public Set keySet() {
        return this.map.keySet();
    }

    public boolean equals(Object o) {
        return this.map.equals(o);
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    public String toString() {
        return this.map.toString();
    }
}
