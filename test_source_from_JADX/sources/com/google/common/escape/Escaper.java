package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;

@GwtCompatible
@Beta
public abstract class Escaper {
    private final Function<String, String> asFunction = new C09871();

    /* renamed from: com.google.common.escape.Escaper$1 */
    class C09871 implements Function<String, String> {
        C09871() {
        }

        public String apply(String from) {
            return Escaper.this.escape(from);
        }
    }

    public abstract String escape(String str);

    protected Escaper() {
    }

    public final Function<String, String> asFunction() {
        return this.asFunction;
    }
}
