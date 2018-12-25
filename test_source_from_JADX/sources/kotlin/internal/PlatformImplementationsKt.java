package kotlin.internal;

import com.facebook.internal.NativeProtocol;
import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\b\u0010\b\u001a\u00020\u0005H\u0002\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "getJavaVersion", "kotlin-stdlib"}, k = 2, mv = {1, 1, 10})
/* compiled from: PlatformImplementations.kt */
public final class PlatformImplementationsKt {
    @NotNull
    @JvmField
    public static final PlatformImplementations IMPLEMENTATIONS;

    static {
        Object newInstance;
        PlatformImplementations platformImplementations;
        int version = getJavaVersion();
        if (version >= 65544) {
            try {
                newInstance = Class.forName("kotlin.internal.jdk8.JDK8PlatformImplementations").newInstance();
                if (newInstance == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                }
                platformImplementations = (PlatformImplementations) newInstance;
                IMPLEMENTATIONS = platformImplementations;
            } catch (ClassNotFoundException e) {
                newInstance = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
                if (newInstance == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                }
                platformImplementations = (PlatformImplementations) newInstance;
            } catch (ClassNotFoundException e2) {
            }
        }
        if (version >= NativeProtocol.MESSAGE_GET_LIKE_STATUS_REPLY) {
            try {
                newInstance = Class.forName("kotlin.internal.jdk7.JDK7PlatformImplementations").newInstance();
                if (newInstance == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                }
                platformImplementations = (PlatformImplementations) newInstance;
                IMPLEMENTATIONS = platformImplementations;
            } catch (ClassNotFoundException e3) {
                newInstance = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                if (newInstance == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                }
                platformImplementations = (PlatformImplementations) newInstance;
            } catch (ClassNotFoundException e4) {
            }
        }
        platformImplementations = new PlatformImplementations();
        IMPLEMENTATIONS = platformImplementations;
    }

    private static final int getJavaVersion() {
        String version = System.getProperty("java.specification.version");
        if (version == null) {
            return NativeProtocol.MESSAGE_GET_LIKE_STATUS_REQUEST;
        }
        int firstDot = StringsKt__StringsKt.indexOf$default((CharSequence) version, '.', 0, false, 6, null);
        int parseInt;
        if (firstDot < 0) {
            try {
                parseInt = 65536 * Integer.parseInt(version);
            } catch (NumberFormatException e) {
                parseInt = NativeProtocol.MESSAGE_GET_LIKE_STATUS_REQUEST;
            }
            return parseInt;
        }
        int secondDot = StringsKt__StringsKt.indexOf$default((CharSequence) version, '.', firstDot + 1, false, 4, null);
        if (secondDot < 0) {
            secondDot = version.length();
        }
        if (version == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String firstPart = version.substring(null, firstDot);
        Intrinsics.checkExpressionValueIsNotNull(firstPart, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        String secondPart = firstDot + 1;
        if (version == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        secondPart = version.substring(secondPart, secondDot);
        Intrinsics.checkExpressionValueIsNotNull(secondPart, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        try {
            parseInt = Integer.parseInt(secondPart) + (Integer.parseInt(firstPart) * 65536);
        } catch (NumberFormatException e2) {
            parseInt = NativeProtocol.MESSAGE_GET_LIKE_STATUS_REQUEST;
        }
        return parseInt;
    }

    @SinceKotlin(version = "1.2")
    @PublishedApi
    public static final boolean apiVersionIsAtLeast(int major, int minor, int patch) {
        return KotlinVersion.CURRENT.isAtLeast(major, minor, patch);
    }
}
