package org.catrobat.catroid.io;

import android.util.Log;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.reflection.FieldKey;
import com.thoughtworks.xstream.converters.reflection.FieldKeySorter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.catrobat.catroid.utils.CrashReporter;

public class CatroidFieldKeySorter implements FieldKeySorter {
    private static final String TAG = CatroidFieldKeySorter.class.getSimpleName();

    /* renamed from: org.catrobat.catroid.io.CatroidFieldKeySorter$2 */
    class C18492 implements Comparator {
        C18492() {
        }

        public int compare(Object objectOne, Object objectTwo) {
            FieldKey fieldKeyOne = (FieldKey) objectOne;
            FieldKey fieldKeyTwo = (FieldKey) objectTwo;
            int fieldKeyComparator = fieldKeyOne.getDepth() - fieldKeyTwo.getDepth();
            if (fieldKeyComparator == 0) {
                return CatroidFieldKeySorter.getAliasOrFieldName(fieldKeyOne).compareTo(CatroidFieldKeySorter.getAliasOrFieldName(fieldKeyTwo));
            }
            return fieldKeyComparator;
        }
    }

    public Map sort(Class type, Map keyedByFieldKey) {
        XStreamFieldKeyOrder fieldKeyOrderAnnotation = (XStreamFieldKeyOrder) findAnnotationInClassHierarchy(type, XStreamFieldKeyOrder.class);
        if (fieldKeyOrderAnnotation != null) {
            return sortByList(Arrays.asList(fieldKeyOrderAnnotation.value()), keyedByFieldKey);
        }
        return sortAlphabeticallyByClassHierarchy(keyedByFieldKey);
    }

    private <E extends Annotation> E findAnnotationInClassHierarchy(Class<?> clazz, Class<? extends E> annotation) {
        for (Class<?> currentClass = clazz; currentClass != Object.class; currentClass = currentClass.getSuperclass()) {
            E currentClassAnnotation = currentClass.getAnnotation(annotation);
            if (currentClassAnnotation != null) {
                return currentClassAnnotation;
            }
        }
        return null;
    }

    private Map sortByList(final List<String> fieldOrder, Map keyedByFieldKey) {
        checkMissingSerializableField(fieldOrder, keyedByFieldKey.entrySet());
        Map map = new TreeMap(new Comparator() {
            public int compare(Object objectOne, Object objectTwo) {
                return fieldOrder.indexOf(CatroidFieldKeySorter.getAliasOrFieldName((FieldKey) objectOne)) - fieldOrder.indexOf(CatroidFieldKeySorter.getAliasOrFieldName((FieldKey) objectTwo));
            }
        });
        map.putAll(keyedByFieldKey);
        return map;
    }

    private void checkMissingSerializableField(List<String> fieldOrder, Set<Entry<FieldKey, Field>> fields) {
        for (Entry<FieldKey, Field> fieldEntry : fields) {
            FieldKey fieldKey = (FieldKey) fieldEntry.getKey();
            String fieldKeyName = getAliasOrFieldName(fieldKey);
            if (!fieldOrder.contains(fieldKeyName) && isSerializable((Field) fieldEntry.getValue())) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Missing field '");
                stringBuilder.append(fieldKeyName);
                stringBuilder.append("' in XStreamFieldKeyOrder annotation for class ");
                stringBuilder.append(fieldKey.getDeclaringClass());
                throw new XStreamMissingSerializableFieldException(stringBuilder.toString());
            }
        }
    }

    private boolean isSerializable(Field field) {
        int modifiers = field.getModifiers();
        return (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) ? false : true;
    }

    private Map sortAlphabeticallyByClassHierarchy(Map keyedByFieldKey) {
        Map map = new TreeMap(new C18492());
        map.putAll(keyedByFieldKey);
        return map;
    }

    public static String getAliasOrFieldName(FieldKey fieldKey) {
        String fieldName = fieldKey.getFieldName();
        try {
            XStreamAlias alias = (XStreamAlias) fieldKey.getDeclaringClass().getDeclaredField(fieldName).getAnnotation(XStreamAlias.class);
            if (alias != null) {
                return alias.value();
            }
            return fieldName;
        } catch (Exception exception) {
            Log.e(TAG, Log.getStackTraceString(exception));
            CrashReporter.logException(exception);
            return fieldName;
        }
    }
}
