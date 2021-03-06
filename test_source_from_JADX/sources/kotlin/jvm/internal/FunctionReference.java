package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

public class FunctionReference extends CallableReference implements FunctionBase, KFunction {
    private final int arity;

    public FunctionReference(int arity) {
        this.arity = arity;
    }

    @SinceKotlin(version = "1.1")
    public FunctionReference(int arity, Object receiver) {
        super(receiver);
        this.arity = arity;
    }

    public int getArity() {
        return this.arity;
    }

    @SinceKotlin(version = "1.1")
    protected KFunction getReflected() {
        return (KFunction) super.getReflected();
    }

    @SinceKotlin(version = "1.1")
    protected KCallable computeReflected() {
        return Reflection.function(this);
    }

    @SinceKotlin(version = "1.1")
    public boolean isInline() {
        return getReflected().isInline();
    }

    @SinceKotlin(version = "1.1")
    public boolean isExternal() {
        return getReflected().isExternal();
    }

    @SinceKotlin(version = "1.1")
    public boolean isOperator() {
        return getReflected().isOperator();
    }

    @SinceKotlin(version = "1.1")
    public boolean isInfix() {
        return getReflected().isInfix();
    }

    @SinceKotlin(version = "1.1")
    public boolean isSuspend() {
        return getReflected().isSuspend();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r6) {
        /*
        r5 = this;
        r0 = 1;
        if (r6 != r5) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = r6 instanceof kotlin.jvm.internal.FunctionReference;
        r2 = 0;
        if (r1 == 0) goto L_0x0054;
    L_0x0009:
        r1 = r6;
        r1 = (kotlin.jvm.internal.FunctionReference) r1;
        r3 = r5.getOwner();
        if (r3 != 0) goto L_0x0019;
    L_0x0012:
        r3 = r1.getOwner();
        if (r3 != 0) goto L_0x0052;
    L_0x0018:
        goto L_0x0027;
    L_0x0019:
        r3 = r5.getOwner();
        r4 = r1.getOwner();
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0052;
    L_0x0027:
        r3 = r5.getName();
        r4 = r1.getName();
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0052;
    L_0x0035:
        r3 = r5.getSignature();
        r4 = r1.getSignature();
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0052;
    L_0x0043:
        r3 = r5.getBoundReceiver();
        r4 = r1.getBoundReceiver();
        r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4);
        if (r3 == 0) goto L_0x0052;
    L_0x0051:
        goto L_0x0053;
    L_0x0052:
        r0 = 0;
    L_0x0053:
        return r0;
    L_0x0054:
        r0 = r6 instanceof kotlin.reflect.KFunction;
        if (r0 == 0) goto L_0x0061;
    L_0x0058:
        r0 = r5.compute();
        r0 = r6.equals(r0);
        return r0;
    L_0x0061:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.jvm.internal.FunctionReference.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return (((getOwner() == null ? 0 : getOwner().hashCode() * 31) + getName().hashCode()) * 31) + getSignature().hashCode();
    }

    public String toString() {
        FunctionReference reflected = compute();
        if (reflected != this) {
            return reflected.toString();
        }
        String str;
        if ("<init>".equals(getName())) {
            str = "constructor (Kotlin reflection is not available)";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("function ");
            stringBuilder.append(getName());
            stringBuilder.append(" (Kotlin reflection is not available)");
            str = stringBuilder.toString();
        }
        return str;
    }
}
