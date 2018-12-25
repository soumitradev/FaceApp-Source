package com.thoughtworks.xstream.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ExplicitTypePermission implements TypePermission {
    final Set names;

    /* renamed from: com.thoughtworks.xstream.security.ExplicitTypePermission$1 */
    class C16951 {
        final /* synthetic */ Class[] val$types;

        C16951(Class[] clsArr) {
            this.val$types = clsArr;
        }

        public String[] getNames() {
            if (this.val$types == null) {
                return null;
            }
            String[] names = new String[this.val$types.length];
            for (int i = 0; i < this.val$types.length; i++) {
                names[i] = this.val$types[i].getName();
            }
            return names;
        }
    }

    public ExplicitTypePermission(Class[] types) {
        this(new C16951(types).getNames());
    }

    public ExplicitTypePermission(String[] names) {
        this.names = names == null ? Collections.EMPTY_SET : new HashSet(Arrays.asList(names));
    }

    public boolean allows(Class type) {
        if (type == null) {
            return false;
        }
        return this.names.contains(type.getName());
    }
}
