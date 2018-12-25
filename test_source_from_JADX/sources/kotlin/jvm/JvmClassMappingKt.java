package kotlin.jvm;

import java.lang.annotation.Annotation;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a!\u0010\u0018\u001a\u00020\u0019\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\r*\u0006\u0012\u0002\b\u00030\u001aH\u0007¢\u0006\u0002\u0010\u001b\"'\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"0\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00018GX\u0004¢\u0006\f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"&\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\u0002H\u00028Ç\u0002¢\u0006\u0006\u001a\u0004\b\n\u0010\u000e\";\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018Ç\u0002X\u0004¢\u0006\f\u0012\u0004\b\u000f\u0010\t\u001a\u0004\b\u0010\u0010\u000b\"+\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000b\"-\u0010\u0013\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000b\"+\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00078G¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "java", "Ljava/lang/Class;", "java$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "javaClass$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-runtime"}, k = 2, mv = {1, 1, 10})
@JvmName(name = "JvmClassMappingKt")
/* compiled from: JvmClassMapping.kt */
public final class JvmClassMappingKt {
    public static /* synthetic */ void java$annotations(KClass kClass) {
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'java' property to get Java class corresponding to this Kotlin class or cast this instance to Any if you really want to get the runtime Java class of this implementation of KClass.", replaceWith = @ReplaceWith(expression = "(this as Any).javaClass", imports = {}))
    public static /* synthetic */ void javaClass$annotations(KClass kClass) {
    }

    @NotNull
    @JvmName(name = "getJavaClass")
    public static final <T> Class<T> getJavaClass(@NotNull KClass<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Class<T> jClass = ((ClassBasedDeclarationContainer) $receiver).getJClass();
        if (jClass != null) {
            return jClass;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @org.jetbrains.annotations.Nullable
    public static final <T> java.lang.Class<T> getJavaPrimitiveType(@org.jetbrains.annotations.NotNull kotlin.reflect.KClass<T> r3) {
        /*
        r0 = "$receiver";
        kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r3, r0);
        r0 = r3;
        r0 = (kotlin.jvm.internal.ClassBasedDeclarationContainer) r0;
        r0 = r0.getJClass();
        r1 = r0.isPrimitive();
        if (r1 == 0) goto L_0x001d;
    L_0x0012:
        if (r0 != 0) goto L_0x001c;
    L_0x0014:
        r1 = new kotlin.TypeCastException;
        r2 = "null cannot be cast to non-null type java.lang.Class<T>";
        r1.<init>(r2);
        throw r1;
    L_0x001c:
        return r0;
    L_0x001d:
        r1 = r0.getName();
        if (r1 != 0) goto L_0x0025;
    L_0x0023:
        goto L_0x0091;
    L_0x0025:
        r2 = r1.hashCode();
        switch(r2) {
            case -2056817302: goto L_0x0086;
            case -527879800: goto L_0x007b;
            case -515992664: goto L_0x0070;
            case 155276373: goto L_0x0065;
            case 344809556: goto L_0x005a;
            case 398507100: goto L_0x004f;
            case 398795216: goto L_0x0044;
            case 399092968: goto L_0x0039;
            case 761287205: goto L_0x002e;
            default: goto L_0x002c;
        };
    L_0x002c:
        goto L_0x0091;
    L_0x002e:
        r2 = "java.lang.Double";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0036:
        r1 = java.lang.Double.TYPE;
        goto L_0x0092;
    L_0x0039:
        r2 = "java.lang.Void";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0041:
        r1 = java.lang.Void.TYPE;
        goto L_0x0092;
    L_0x0044:
        r2 = "java.lang.Long";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x004c:
        r1 = java.lang.Long.TYPE;
        goto L_0x0092;
    L_0x004f:
        r2 = "java.lang.Byte";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0057:
        r1 = java.lang.Byte.TYPE;
        goto L_0x0092;
    L_0x005a:
        r2 = "java.lang.Boolean";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0062:
        r1 = java.lang.Boolean.TYPE;
        goto L_0x0092;
    L_0x0065:
        r2 = "java.lang.Character";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x006d:
        r1 = java.lang.Character.TYPE;
        goto L_0x0092;
    L_0x0070:
        r2 = "java.lang.Short";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0078:
        r1 = java.lang.Short.TYPE;
        goto L_0x0092;
    L_0x007b:
        r2 = "java.lang.Float";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0083:
        r1 = java.lang.Float.TYPE;
        goto L_0x0092;
    L_0x0086:
        r2 = "java.lang.Integer";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x008e:
        r1 = java.lang.Integer.TYPE;
        goto L_0x0092;
    L_0x0091:
        r1 = 0;
    L_0x0092:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.jvm.JvmClassMappingKt.getJavaPrimitiveType(kotlin.reflect.KClass):java.lang.Class<T>");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @org.jetbrains.annotations.NotNull
    public static final <T> java.lang.Class<T> getJavaObjectType(@org.jetbrains.annotations.NotNull kotlin.reflect.KClass<T> r3) {
        /*
        r0 = "$receiver";
        kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r3, r0);
        r0 = r3;
        r0 = (kotlin.jvm.internal.ClassBasedDeclarationContainer) r0;
        r0 = r0.getJClass();
        r1 = r0.isPrimitive();
        if (r1 != 0) goto L_0x001d;
    L_0x0012:
        if (r0 != 0) goto L_0x001c;
    L_0x0014:
        r1 = new kotlin.TypeCastException;
        r2 = "null cannot be cast to non-null type java.lang.Class<T>";
        r1.<init>(r2);
        throw r1;
    L_0x001c:
        return r0;
    L_0x001d:
        r1 = r0.getName();
        if (r1 != 0) goto L_0x0025;
    L_0x0023:
        goto L_0x0091;
    L_0x0025:
        r2 = r1.hashCode();
        switch(r2) {
            case -1325958191: goto L_0x0086;
            case 104431: goto L_0x007b;
            case 3039496: goto L_0x0070;
            case 3052374: goto L_0x0065;
            case 3327612: goto L_0x005a;
            case 3625364: goto L_0x004f;
            case 64711720: goto L_0x0044;
            case 97526364: goto L_0x0039;
            case 109413500: goto L_0x002e;
            default: goto L_0x002c;
        };
    L_0x002c:
        goto L_0x0091;
    L_0x002e:
        r2 = "short";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0036:
        r1 = java.lang.Short.class;
        goto L_0x0093;
    L_0x0039:
        r2 = "float";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0041:
        r1 = java.lang.Float.class;
        goto L_0x0093;
    L_0x0044:
        r2 = "boolean";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x004c:
        r1 = java.lang.Boolean.class;
        goto L_0x0093;
    L_0x004f:
        r2 = "void";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0057:
        r1 = java.lang.Void.class;
        goto L_0x0093;
    L_0x005a:
        r2 = "long";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0062:
        r1 = java.lang.Long.class;
        goto L_0x0093;
    L_0x0065:
        r2 = "char";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x006d:
        r1 = java.lang.Character.class;
        goto L_0x0093;
    L_0x0070:
        r2 = "byte";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0078:
        r1 = java.lang.Byte.class;
        goto L_0x0093;
    L_0x007b:
        r2 = "int";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x0083:
        r1 = java.lang.Integer.class;
        goto L_0x0093;
    L_0x0086:
        r2 = "double";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0091;
    L_0x008e:
        r1 = java.lang.Double.class;
        goto L_0x0093;
        r1 = r0;
    L_0x0093:
        if (r1 != 0) goto L_0x009d;
    L_0x0095:
        r1 = new kotlin.TypeCastException;
        r2 = "null cannot be cast to non-null type java.lang.Class<T>";
        r1.<init>(r2);
        throw r1;
    L_0x009d:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.jvm.JvmClassMappingKt.getJavaObjectType(kotlin.reflect.KClass):java.lang.Class<T>");
    }

    @NotNull
    @JvmName(name = "getKotlinClass")
    public static final <T> KClass<T> getKotlinClass(@NotNull Class<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return Reflection.getOrCreateKotlinClass($receiver);
    }

    @NotNull
    public static final <T> Class<T> getJavaClass(@NotNull T $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Class<T> cls = $receiver.getClass();
        if (cls != null) {
            return cls;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
    }

    @NotNull
    @JvmName(name = "getRuntimeClassOfKClassInstance")
    public static final <T> Class<KClass<T>> getRuntimeClassOfKClassInstance(@NotNull KClass<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Class<KClass<T>> cls = $receiver.getClass();
        if (cls != null) {
            return cls;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<kotlin.reflect.KClass<T>>");
    }

    private static final <T> boolean isArrayOf(@NotNull Object[] $receiver) {
        Intrinsics.reifiedOperationMarker(4, "T");
        return Object.class.isAssignableFrom($receiver.getClass().getComponentType());
    }

    @NotNull
    public static final <T extends Annotation> KClass<? extends T> getAnnotationClass(@NotNull T $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Class annotationType = $receiver.annotationType();
        Intrinsics.checkExpressionValueIsNotNull(annotationType, "(this as java.lang.annot…otation).annotationType()");
        KClass<? extends T> kotlinClass = getKotlinClass(annotationType);
        if (kotlinClass != null) {
            return kotlinClass;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.KClass<out T>");
    }
}
