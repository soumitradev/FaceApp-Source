package kotlin.text;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import javax.jmdns.impl.constants.DNSRecordClass;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\\\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\n\n\u0002\b\u0005\u001a4\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0005H\b¢\u0006\u0004\b\u0006\u0010\u0007\u001a\r\u0010\b\u001a\u00020\t*\u00020\u0003H\b\u001a\u0015\u0010\b\u001a\u00020\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\b\u001a\u000e\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u0003H\u0007\u001a\u0016\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\r\u0010\r\u001a\u00020\u000e*\u00020\u0003H\b\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\b\u001a\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007\u001a\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\r\u0010\u0012\u001a\u00020\u0013*\u00020\u0003H\b\u001a\r\u0010\u0014\u001a\u00020\u0015*\u00020\u0003H\b\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\b\u001a\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0015*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0017\u001a\u001b\u0010\u0016\u001a\u0004\u0018\u00010\u0015*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0007¢\u0006\u0002\u0010\u0018\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u0003H\b\u001a\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u001a*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u001c\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u0003H\b\u001a\u0013\u0010\u001f\u001a\u0004\u0018\u00010\u001e*\u00020\u0003H\u0007¢\u0006\u0002\u0010 \u001a\r\u0010!\u001a\u00020\u0010*\u00020\u0003H\b\u001a\u0015\u0010!\u001a\u00020\u0010*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\b\u001a\u0013\u0010\"\u001a\u0004\u0018\u00010\u0010*\u00020\u0003H\u0007¢\u0006\u0002\u0010#\u001a\u001b\u0010\"\u001a\u0004\u0018\u00010\u0010*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0007¢\u0006\u0002\u0010$\u001a\r\u0010%\u001a\u00020&*\u00020\u0003H\b\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\b\u001a\u0013\u0010'\u001a\u0004\u0018\u00010&*\u00020\u0003H\u0007¢\u0006\u0002\u0010(\u001a\u001b\u0010'\u001a\u0004\u0018\u00010&*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0007¢\u0006\u0002\u0010)\u001a\r\u0010*\u001a\u00020+*\u00020\u0003H\b\u001a\u0015\u0010*\u001a\u00020+*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\b\u001a\u0013\u0010,\u001a\u0004\u0018\u00010+*\u00020\u0003H\u0007¢\u0006\u0002\u0010-\u001a\u001b\u0010,\u001a\u0004\u0018\u00010+*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0007¢\u0006\u0002\u0010.\u001a\u0015\u0010/\u001a\u00020\u0003*\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\b\u001a\u0015\u0010/\u001a\u00020\u0003*\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\b\u001a\u0015\u0010/\u001a\u00020\u0003*\u00020&2\u0006\u0010\u000f\u001a\u00020\u0010H\b\u001a\u0015\u0010/\u001a\u00020\u0003*\u00020+2\u0006\u0010\u000f\u001a\u00020\u0010H\b¨\u00060"}, d2 = {"screenFloatValue", "T", "str", "", "parse", "Lkotlin/Function1;", "screenFloatValue$StringsKt__StringNumberConversionsKt", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "toBigDecimal", "Ljava/math/BigDecimal;", "mathContext", "Ljava/math/MathContext;", "toBigDecimalOrNull", "toBigInteger", "Ljava/math/BigInteger;", "radix", "", "toBigIntegerOrNull", "toBoolean", "", "toByte", "", "toByteOrNull", "(Ljava/lang/String;)Ljava/lang/Byte;", "(Ljava/lang/String;I)Ljava/lang/Byte;", "toDouble", "", "toDoubleOrNull", "(Ljava/lang/String;)Ljava/lang/Double;", "toFloat", "", "toFloatOrNull", "(Ljava/lang/String;)Ljava/lang/Float;", "toInt", "toIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "(Ljava/lang/String;I)Ljava/lang/Integer;", "toLong", "", "toLongOrNull", "(Ljava/lang/String;)Ljava/lang/Long;", "(Ljava/lang/String;I)Ljava/lang/Long;", "toShort", "", "toShortOrNull", "(Ljava/lang/String;)Ljava/lang/Short;", "(Ljava/lang/String;I)Ljava/lang/Short;", "toString", "kotlin-stdlib"}, k = 5, mv = {1, 1, 10}, xi = 1, xs = "kotlin/text/StringsKt")
/* compiled from: StringNumberConversions.kt */
class StringsKt__StringNumberConversionsKt extends StringsKt__StringBuilderKt {
    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final String toString(byte $receiver, int radix) {
        String num = Integer.toString($receiver, CharsKt__CharJVMKt.checkRadix(CharsKt__CharJVMKt.checkRadix(radix)));
        Intrinsics.checkExpressionValueIsNotNull(num, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        return num;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final String toString(short $receiver, int radix) {
        String num = Integer.toString($receiver, CharsKt__CharJVMKt.checkRadix(CharsKt__CharJVMKt.checkRadix(radix)));
        Intrinsics.checkExpressionValueIsNotNull(num, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        return num;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final String toString(int $receiver, int radix) {
        String num = Integer.toString($receiver, CharsKt__CharJVMKt.checkRadix(radix));
        Intrinsics.checkExpressionValueIsNotNull(num, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        return num;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final String toString(long $receiver, int radix) {
        String l = Long.toString($receiver, CharsKt__CharJVMKt.checkRadix(radix));
        Intrinsics.checkExpressionValueIsNotNull(l, "java.lang.Long.toString(this, checkRadix(radix))");
        return l;
    }

    @InlineOnly
    private static final boolean toBoolean(@NotNull String $receiver) {
        return Boolean.parseBoolean($receiver);
    }

    @InlineOnly
    private static final byte toByte(@NotNull String $receiver) {
        return Byte.parseByte($receiver);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final byte toByte(@NotNull String $receiver, int radix) {
        return Byte.parseByte($receiver, CharsKt__CharJVMKt.checkRadix(radix));
    }

    @InlineOnly
    private static final short toShort(@NotNull String $receiver) {
        return Short.parseShort($receiver);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final short toShort(@NotNull String $receiver, int radix) {
        return Short.parseShort($receiver, CharsKt__CharJVMKt.checkRadix(radix));
    }

    @InlineOnly
    private static final int toInt(@NotNull String $receiver) {
        return Integer.parseInt($receiver);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final int toInt(@NotNull String $receiver, int radix) {
        return Integer.parseInt($receiver, CharsKt__CharJVMKt.checkRadix(radix));
    }

    @InlineOnly
    private static final long toLong(@NotNull String $receiver) {
        return Long.parseLong($receiver);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final long toLong(@NotNull String $receiver, int radix) {
        return Long.parseLong($receiver, CharsKt__CharJVMKt.checkRadix(radix));
    }

    @InlineOnly
    private static final float toFloat(@NotNull String $receiver) {
        return Float.parseFloat($receiver);
    }

    @InlineOnly
    private static final double toDouble(@NotNull String $receiver) {
        return Double.parseDouble($receiver);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Byte toByteOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return toByteOrNull($receiver, 10);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Byte toByteOrNull(@NotNull String $receiver, int radix) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int intR = toIntOrNull($receiver, radix);
        if (intR == 0) {
            return null;
        }
        intR = intR.intValue();
        if (intR >= -128) {
            if (intR <= MetaEvent.SEQUENCER_SPECIFIC) {
                return Byte.valueOf((byte) intR);
            }
        }
        return null;
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Short toShortOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return toShortOrNull($receiver, 10);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Short toShortOrNull(@NotNull String $receiver, int radix) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int intR = toIntOrNull($receiver, radix);
        if (intR == 0) {
            return null;
        }
        intR = intR.intValue();
        if (intR >= -32768) {
            if (intR <= DNSRecordClass.CLASS_MASK) {
                return Short.valueOf((short) intR);
            }
        }
        return null;
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Integer toIntOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return toIntOrNull($receiver, 10);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Integer toIntOrNull(@NotNull String $receiver, int radix) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharsKt__CharJVMKt.checkRadix(radix);
        int length = $receiver.length();
        if (length == 0) {
            return null;
        }
        int limit = 0;
        boolean isNegative = false;
        int start = 0;
        char firstChar = $receiver.charAt(0);
        if (firstChar >= '0') {
            start = 0;
            isNegative = false;
            limit = -2147483647;
        } else if (length == 1) {
            return null;
        } else {
            start = 1;
            if (firstChar == '-') {
                isNegative = true;
                limit = Integer.MIN_VALUE;
            } else if (firstChar != '+') {
                return null;
            } else {
                isNegative = false;
                limit = -2147483647;
            }
        }
        int limitBeforeMul = limit / radix;
        int result = 0;
        int i = length - 1;
        if (start <= i) {
            int result2 = 0;
            result = start;
            while (true) {
                int digit = CharsKt__CharJVMKt.digitOf($receiver.charAt(result), radix);
                if (digit >= 0 && result2 >= limitBeforeMul) {
                    result2 *= radix;
                    if (result2 >= limit + digit) {
                        result2 -= digit;
                        if (result == i) {
                            break;
                        }
                        result++;
                    } else {
                        return null;
                    }
                }
                return null;
            }
            result = result2;
        }
        return isNegative ? Integer.valueOf(result) : Integer.valueOf(-result);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Long toLongOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return toLongOrNull($receiver, 10);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Long toLongOrNull(@NotNull String $receiver, int radix) {
        String str = $receiver;
        int i = radix;
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        CharsKt__CharJVMKt.checkRadix(radix);
        int length = $receiver.length();
        Long l = null;
        if (length == 0) {
            return null;
        }
        long limit;
        boolean isNegative = false;
        int start = 0;
        char firstChar = str.charAt(0);
        if (firstChar >= '0') {
            start = 0;
            isNegative = false;
            limit = -9223372036854775807L;
        } else if (length == 1) {
            return null;
        } else {
            start = 1;
            if (firstChar == '-') {
                isNegative = true;
                limit = Long.MIN_VALUE;
            } else if (firstChar != '+') {
                return null;
            } else {
                isNegative = false;
                limit = -9223372036854775807L;
            }
        }
        long limitBeforeMul = limit / ((long) i);
        long result = 0;
        int i2 = length - 1;
        if (start <= i2) {
            long result2;
            long result3 = 0;
            int i3 = start;
            while (true) {
                int digit = CharsKt__CharJVMKt.digitOf(str.charAt(i3), i);
                if (digit >= 0 && result3 >= limitBeforeMul) {
                    long limit2 = limit;
                    result3 *= (long) i;
                    if (result3 >= limit2 + ((long) digit)) {
                        l = null;
                        result2 = result3 - ((long) digit);
                        if (i3 == i2) {
                            break;
                        }
                        i3++;
                        result3 = result2;
                        limit = limit2;
                    } else {
                        return null;
                    }
                }
                return l;
            }
            result = result2;
        }
        return isNegative ? Long.valueOf(result) : Long.valueOf(-result);
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Float toFloatOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int $i$f$screenFloatValue$StringsKt__StringNumberConversionsKt = 0;
        Float f = null;
        try {
            if (ScreenFloatValueRegEx.value.matches($receiver)) {
                f = Float.valueOf(Float.parseFloat($receiver));
            }
        } catch (NumberFormatException e) {
        }
        return f;
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final Double toDoubleOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int $i$f$screenFloatValue$StringsKt__StringNumberConversionsKt = 0;
        Double d = null;
        try {
            if (ScreenFloatValueRegEx.value.matches($receiver)) {
                d = Double.valueOf(Double.parseDouble($receiver));
            }
        } catch (NumberFormatException e) {
        }
        return d;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(@NotNull String $receiver) {
        return new BigInteger($receiver);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(@NotNull String $receiver, int radix) {
        return new BigInteger($receiver, CharsKt__CharJVMKt.checkRadix(radix));
    }

    @SinceKotlin(version = "1.2")
    @Nullable
    public static final BigInteger toBigIntegerOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return toBigIntegerOrNull($receiver, 10);
    }

    @SinceKotlin(version = "1.2")
    @Nullable
    public static final BigInteger toBigIntegerOrNull(@NotNull String $receiver, int radix) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharsKt__CharJVMKt.checkRadix(radix);
        int length = $receiver.length();
        int start = 0;
        switch (length) {
            case 0:
                return null;
            case 1:
                if (CharsKt__CharJVMKt.digitOf($receiver.charAt(0), radix) < 0) {
                    return null;
                }
                break;
            default:
                if ($receiver.charAt(0) == '-') {
                    start = 1;
                }
                for (int index = start; index < length; index++) {
                    if (CharsKt__CharJVMKt.digitOf($receiver.charAt(index), radix) < 0) {
                        return null;
                    }
                }
                break;
        }
        return new BigInteger($receiver, CharsKt__CharJVMKt.checkRadix(radix));
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(@NotNull String $receiver) {
        return new BigDecimal($receiver);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(@NotNull String $receiver, MathContext mathContext) {
        return new BigDecimal($receiver, mathContext);
    }

    @SinceKotlin(version = "1.2")
    @Nullable
    public static final BigDecimal toBigDecimalOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int $i$f$screenFloatValue$StringsKt__StringNumberConversionsKt = 0;
        BigDecimal bigDecimal = null;
        try {
            if (ScreenFloatValueRegEx.value.matches($receiver)) {
                bigDecimal = new BigDecimal($receiver);
            }
        } catch (NumberFormatException e) {
        }
        return bigDecimal;
    }

    @SinceKotlin(version = "1.2")
    @Nullable
    public static final BigDecimal toBigDecimalOrNull(@NotNull String $receiver, @NotNull MathContext mathContext) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(mathContext, "mathContext");
        int $i$f$screenFloatValue$StringsKt__StringNumberConversionsKt = 0;
        BigDecimal bigDecimal = null;
        try {
            if (ScreenFloatValueRegEx.value.matches($receiver)) {
                bigDecimal = new BigDecimal($receiver, mathContext);
            }
        } catch (NumberFormatException e) {
        }
        return bigDecimal;
    }

    private static final <T> T screenFloatValue$StringsKt__StringNumberConversionsKt(String str, Function1<? super String, ? extends T> parse) {
        T t = null;
        try {
            if (ScreenFloatValueRegEx.value.matches(str)) {
                t = parse.invoke(str);
            }
        } catch (NumberFormatException e) {
        }
        return t;
    }
}
