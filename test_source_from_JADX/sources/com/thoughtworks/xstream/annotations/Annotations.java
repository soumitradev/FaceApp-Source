package com.thoughtworks.xstream.annotations;

import com.thoughtworks.xstream.XStream;

@Deprecated
public class Annotations {
    private Annotations() {
    }

    @Deprecated
    public static synchronized void configureAliases(XStream xstream, Class<?>... topLevelClasses) {
        synchronized (Annotations.class) {
            xstream.processAnnotations(topLevelClasses);
        }
    }
}
