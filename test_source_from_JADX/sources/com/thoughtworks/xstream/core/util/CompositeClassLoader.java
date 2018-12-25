package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.core.JVM;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CompositeClassLoader extends ClassLoader {
    private final List classLoaders = new ArrayList();
    private final ReferenceQueue queue = new ReferenceQueue();

    static {
        if (JVM.is17()) {
            try {
                Method m = ClassLoader.class.getDeclaredMethod("registerAsParallelCapable", (Class[]) null);
                if (!m.isAccessible()) {
                    m.setAccessible(true);
                }
                m.invoke(null, (Object[]) null);
            } catch (Exception e) {
            }
        }
    }

    public CompositeClassLoader() {
        addInternal(Object.class.getClassLoader());
        addInternal(getClass().getClassLoader());
    }

    public synchronized void add(ClassLoader classLoader) {
        cleanup();
        if (classLoader != null) {
            addInternal(classLoader);
        }
    }

    private void addInternal(ClassLoader classLoader) {
        WeakReference refClassLoader = null;
        Iterator iterator = this.classLoaders.iterator();
        while (iterator.hasNext()) {
            WeakReference ref = (WeakReference) iterator.next();
            ClassLoader cl = (ClassLoader) ref.get();
            if (cl == null) {
                iterator.remove();
            } else if (cl == classLoader) {
                iterator.remove();
                refClassLoader = ref;
            }
        }
        this.classLoaders.add(0, refClassLoader != null ? refClassLoader : new WeakReference(classLoader, this.queue));
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        List<ClassLoader> copy = new ArrayList(this.classLoaders.size()) {
            public boolean addAll(Collection c) {
                boolean result = false;
                for (Object add : c) {
                    result |= add(add);
                }
                return result;
            }

            public boolean add(Object ref) {
                Object classLoader = ((WeakReference) ref).get();
                if (classLoader != null) {
                    return super.add(classLoader);
                }
                return false;
            }
        };
        synchronized (this) {
            cleanup();
            copy.addAll(this.classLoaders);
        }
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        for (ClassLoader classLoader : copy) {
            if (classLoader == contextClassLoader) {
                contextClassLoader = null;
            }
            try {
                return classLoader.loadClass(name);
            } catch (ClassNotFoundException e) {
            }
        }
        if (contextClassLoader != null) {
            return contextClassLoader.loadClass(name);
        }
        throw new ClassNotFoundException(name);
    }

    private void cleanup() {
        while (true) {
            WeakReference weakReference = (WeakReference) this.queue.poll();
            WeakReference ref = weakReference;
            if (weakReference != null) {
                this.classLoaders.remove(ref);
            } else {
                return;
            }
        }
    }
}
