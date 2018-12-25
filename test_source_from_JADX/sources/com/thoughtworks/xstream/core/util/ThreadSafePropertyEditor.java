package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import com.thoughtworks.xstream.core.util.Pool.Factory;
import java.beans.PropertyEditor;

public class ThreadSafePropertyEditor {
    private final Class editorType;
    private final Pool pool;

    /* renamed from: com.thoughtworks.xstream.core.util.ThreadSafePropertyEditor$1 */
    class C20651 implements Factory {
        C20651() {
        }

        public Object newInstance() {
            StringBuilder stringBuilder;
            try {
                return ThreadSafePropertyEditor.this.editorType.newInstance();
            } catch (InstantiationException e) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Could not call default constructor of ");
                stringBuilder.append(ThreadSafePropertyEditor.this.editorType.getName());
                throw new ObjectAccessException(stringBuilder.toString(), e);
            } catch (IllegalAccessException e2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Could not call default constructor of ");
                stringBuilder.append(ThreadSafePropertyEditor.this.editorType.getName());
                throw new ObjectAccessException(stringBuilder.toString(), e2);
            }
        }
    }

    public ThreadSafePropertyEditor(Class type, int initialPoolSize, int maxPoolSize) {
        if (PropertyEditor.class.isAssignableFrom(type)) {
            this.editorType = type;
            this.pool = new Pool(initialPoolSize, maxPoolSize, new C20651());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(type.getName());
        stringBuilder.append(" is not a ");
        stringBuilder.append(PropertyEditor.class.getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public String getAsText(Object object) {
        PropertyEditor editor = fetchFromPool();
        try {
            editor.setValue(object);
            String asText = editor.getAsText();
            return asText;
        } finally {
            this.pool.putInPool(editor);
        }
    }

    public Object setAsText(String str) {
        PropertyEditor editor = fetchFromPool();
        try {
            editor.setAsText(str);
            Object value = editor.getValue();
            return value;
        } finally {
            this.pool.putInPool(editor);
        }
    }

    private PropertyEditor fetchFromPool() {
        return (PropertyEditor) this.pool.fetchFromPool();
    }
}
