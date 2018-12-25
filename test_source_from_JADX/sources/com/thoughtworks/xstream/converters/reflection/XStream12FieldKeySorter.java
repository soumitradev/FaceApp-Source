package com.thoughtworks.xstream.converters.reflection;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class XStream12FieldKeySorter implements FieldKeySorter {

    /* renamed from: com.thoughtworks.xstream.converters.reflection.XStream12FieldKeySorter$1 */
    class C16791 implements Comparator {
        C16791() {
        }

        public int compare(Object o1, Object o2) {
            FieldKey fieldKey1 = (FieldKey) o1;
            FieldKey fieldKey2 = (FieldKey) o2;
            int i = fieldKey2.getDepth() - fieldKey1.getDepth();
            if (i == 0) {
                return fieldKey1.getOrder() - fieldKey2.getOrder();
            }
            return i;
        }
    }

    public Map sort(Class type, Map keyedByFieldKey) {
        Map map = new TreeMap(new C16791());
        map.putAll(keyedByFieldKey);
        keyedByFieldKey.clear();
        keyedByFieldKey.putAll(map);
        return keyedByFieldKey;
    }
}
