package kotlin.math;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u000f\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b7\u001a\u0011\u0010!\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010!\u001a\u00020\u00142\u0006\u0010#\u001a\u00020\u0014H\b\u001a\u0011\u0010!\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0017H\b\u001a\u0011\u0010$\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010$\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0010\u0010%\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\u0007\u001a\u0011\u0010%\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010&\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010&\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0010\u0010'\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\u0007\u001a\u0011\u0010'\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010(\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010(\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0019\u0010)\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0019\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0010\u0010+\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\u0007\u001a\u0011\u0010+\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010,\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010,\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010-\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010-\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010.\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010.\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010/\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010/\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u00100\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u00100\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u00101\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u00101\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0019\u00102\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u0001H\b\u001a\u0019\u00102\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u0011H\b\u001a\u0011\u00103\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u00103\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u00104\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u00104\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0018\u00105\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u00012\u0006\u00106\u001a\u00020\u0001H\u0007\u001a\u0018\u00105\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u00112\u0006\u00106\u001a\u00020\u0011H\u0007\u001a\u0011\u00107\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u00107\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0010\u00108\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\u0007\u001a\u0010\u00108\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\u0007\u001a\u0019\u00109\u001a\u00020\u00012\u0006\u0010:\u001a\u00020\u00012\u0006\u0010;\u001a\u00020\u0001H\b\u001a\u0019\u00109\u001a\u00020\u00112\u0006\u0010:\u001a\u00020\u00112\u0006\u0010;\u001a\u00020\u0011H\b\u001a\u0019\u00109\u001a\u00020\u00142\u0006\u0010:\u001a\u00020\u00142\u0006\u0010;\u001a\u00020\u0014H\b\u001a\u0019\u00109\u001a\u00020\u00172\u0006\u0010:\u001a\u00020\u00172\u0006\u0010;\u001a\u00020\u0017H\b\u001a\u0019\u0010<\u001a\u00020\u00012\u0006\u0010:\u001a\u00020\u00012\u0006\u0010;\u001a\u00020\u0001H\b\u001a\u0019\u0010<\u001a\u00020\u00112\u0006\u0010:\u001a\u00020\u00112\u0006\u0010;\u001a\u00020\u0011H\b\u001a\u0019\u0010<\u001a\u00020\u00142\u0006\u0010:\u001a\u00020\u00142\u0006\u0010;\u001a\u00020\u0014H\b\u001a\u0019\u0010<\u001a\u00020\u00172\u0006\u0010:\u001a\u00020\u00172\u0006\u0010;\u001a\u00020\u0017H\b\u001a\u0011\u0010=\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010=\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010\u001a\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010\u001a\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010>\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010>\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010?\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010?\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010@\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010@\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010A\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010A\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0011\u0010B\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0011\u0010B\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0010\u0010C\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\u0007\u001a\u0010\u0010C\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\u0007\u001a\u0015\u0010D\u001a\u00020\u0001*\u00020\u00012\u0006\u0010E\u001a\u00020\u0001H\b\u001a\u0015\u0010D\u001a\u00020\u0011*\u00020\u00112\u0006\u0010E\u001a\u00020\u0011H\b\u001a\r\u0010F\u001a\u00020\u0001*\u00020\u0001H\b\u001a\r\u0010F\u001a\u00020\u0011*\u00020\u0011H\b\u001a\u0015\u0010G\u001a\u00020\u0001*\u00020\u00012\u0006\u0010H\u001a\u00020\u0001H\b\u001a\u0015\u0010G\u001a\u00020\u0011*\u00020\u00112\u0006\u0010H\u001a\u00020\u0011H\b\u001a\r\u0010I\u001a\u00020\u0001*\u00020\u0001H\b\u001a\r\u0010I\u001a\u00020\u0011*\u00020\u0011H\b\u001a\u0015\u0010J\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001H\b\u001a\u0015\u0010J\u001a\u00020\u0001*\u00020\u00012\u0006\u0010#\u001a\u00020\u0014H\b\u001a\u0015\u0010J\u001a\u00020\u0011*\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\b\u001a\u0015\u0010J\u001a\u00020\u0011*\u00020\u00112\u0006\u0010#\u001a\u00020\u0014H\b\u001a\f\u0010K\u001a\u00020\u0014*\u00020\u0001H\u0007\u001a\f\u0010K\u001a\u00020\u0014*\u00020\u0011H\u0007\u001a\f\u0010L\u001a\u00020\u0017*\u00020\u0001H\u0007\u001a\f\u0010L\u001a\u00020\u0017*\u00020\u0011H\u0007\u001a\u0015\u0010M\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u0001H\b\u001a\u0015\u0010M\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u0014H\b\u001a\u0015\u0010M\u001a\u00020\u0011*\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0011H\b\u001a\u0015\u0010M\u001a\u00020\u0011*\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0014H\b\"\u0016\u0010\u0000\u001a\u00020\u00018\u0006XT¢\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u0016\u0010\u0005\u001a\u00020\u00018\u0006XT¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0003\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u001f\u0010\f\u001a\u00020\u0001*\u00020\u00018Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u001f\u0010\f\u001a\u00020\u0011*\u00020\u00118Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\r\u0010\u0012\u001a\u0004\b\u000f\u0010\u0013\"\u001f\u0010\f\u001a\u00020\u0014*\u00020\u00148Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\r\u0010\u0015\u001a\u0004\b\u000f\u0010\u0016\"\u001f\u0010\f\u001a\u00020\u0017*\u00020\u00178Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\r\u0010\u0018\u001a\u0004\b\u000f\u0010\u0019\"\u001f\u0010\u001a\u001a\u00020\u0001*\u00020\u00018Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u001b\u0010\u000e\u001a\u0004\b\u001c\u0010\u0010\"\u001f\u0010\u001a\u001a\u00020\u0011*\u00020\u00118Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u001b\u0010\u0012\u001a\u0004\b\u001c\u0010\u0013\"\u001e\u0010\u001a\u001a\u00020\u0014*\u00020\u00148FX\u0004¢\u0006\f\u0012\u0004\b\u001b\u0010\u0015\u001a\u0004\b\u001c\u0010\u0016\"\u001e\u0010\u001a\u001a\u00020\u0014*\u00020\u00178FX\u0004¢\u0006\f\u0012\u0004\b\u001b\u0010\u0018\u001a\u0004\b\u001c\u0010\u001d\"\u001f\u0010\u001e\u001a\u00020\u0001*\u00020\u00018Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u001f\u0010\u000e\u001a\u0004\b \u0010\u0010\"\u001f\u0010\u001e\u001a\u00020\u0011*\u00020\u00118Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u001f\u0010\u0012\u001a\u0004\b \u0010\u0013¨\u0006N"}, d2 = {"E", "", "E$annotations", "()V", "LN2", "PI", "PI$annotations", "epsilon", "taylor_2_bound", "taylor_n_bound", "upper_taylor_2_bound", "upper_taylor_n_bound", "absoluteValue", "absoluteValue$annotations", "(D)V", "getAbsoluteValue", "(D)D", "", "(F)V", "(F)F", "", "(I)V", "(I)I", "", "(J)V", "(J)J", "sign", "sign$annotations", "getSign", "(J)I", "ulp", "ulp$annotations", "getUlp", "abs", "x", "n", "acos", "acosh", "asin", "asinh", "atan", "atan2", "y", "atanh", "ceil", "cos", "cosh", "exp", "expm1", "floor", "hypot", "ln", "ln1p", "log", "base", "log10", "log2", "max", "a", "b", "min", "round", "sin", "sinh", "sqrt", "tan", "tanh", "truncate", "IEEErem", "divisor", "nextDown", "nextTowards", "to", "nextUp", "pow", "roundToInt", "roundToLong", "withSign", "kotlin-stdlib"}, k = 2, mv = {1, 1, 10})
@JvmName(name = "MathKt")
/* compiled from: MathJVM.kt */
public final class MathKt {
    /* renamed from: E */
    public static final double f9E = 2.718281828459045d;
    private static final double LN2 = Math.log(2.0d);
    public static final double PI = 3.141592653589793d;
    private static final double epsilon = Math.ulp(1.0d);
    private static final double taylor_2_bound = Math.sqrt(epsilon);
    private static final double taylor_n_bound = Math.sqrt(taylor_2_bound);
    private static final double upper_taylor_2_bound;
    private static final double upper_taylor_n_bound;

    @SinceKotlin(version = "1.2")
    public static /* synthetic */ void E$annotations() {
    }

    @SinceKotlin(version = "1.2")
    public static /* synthetic */ void PI$annotations() {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(double d) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(float f) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(int i) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(long j) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void sign$annotations(double d) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void sign$annotations(float f) {
    }

    @SinceKotlin(version = "1.2")
    public static /* synthetic */ void sign$annotations(int i) {
    }

    @SinceKotlin(version = "1.2")
    public static /* synthetic */ void sign$annotations(long j) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void ulp$annotations(double d) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void ulp$annotations(float f) {
    }

    static {
        double d = (double) 1;
        upper_taylor_2_bound = d / taylor_2_bound;
        upper_taylor_n_bound = d / taylor_n_bound;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double sin(double x) {
        return Math.sin(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double cos(double x) {
        return Math.cos(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double tan(double x) {
        return Math.tan(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double asin(double x) {
        return Math.asin(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double acos(double x) {
        return Math.acos(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double atan(double x) {
        return Math.atan(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double atan2(double y, double x) {
        return Math.atan2(y, x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double sinh(double x) {
        return Math.sinh(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double cosh(double x) {
        return Math.cosh(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double tanh(double x) {
        return Math.tanh(x);
    }

    @SinceKotlin(version = "1.2")
    public static final double asinh(double x) {
        double log;
        if (x >= taylor_n_bound) {
            if (x <= upper_taylor_n_bound) {
                log = Math.log(Math.sqrt((x * x) + ((double) 1)) + x);
            } else if (x > upper_taylor_2_bound) {
                log = Math.log(x) + LN2;
            } else {
                double d = (double) 2;
                log = Math.log((x * d) + (((double) 1) / (d * x)));
            }
        } else if (x <= (-taylor_n_bound)) {
            log = -asinh(-x);
        } else {
            log = x;
            if (Math.abs(x) >= taylor_2_bound) {
                log -= ((x * x) * x) / ((double) 6);
            }
        }
        return log;
    }

    @SinceKotlin(version = "1.2")
    public static final double acosh(double x) {
        double d = (double) 1;
        if (x < d) {
            d = DoubleCompanionObject.INSTANCE.getNaN();
        } else if (x > upper_taylor_2_bound) {
            d = Math.log(x) + LN2;
        } else if (x - d >= taylor_n_bound) {
            d = Math.log(Math.sqrt((x * x) - d) + x);
        } else {
            d = Math.sqrt(x - d);
            double result = d;
            if (d >= taylor_2_bound) {
                result -= ((d * d) * d) / ((double) 12);
            }
            d = Math.sqrt(2.0d) * result;
        }
        return d;
    }

    @SinceKotlin(version = "1.2")
    public static final double atanh(double x) {
        double result;
        if (Math.abs(x) < taylor_n_bound) {
            result = x;
            if (Math.abs(x) > taylor_2_bound) {
                result += ((x * x) * x) / ((double) 3);
            }
            return result;
        }
        result = (double) 1;
        return Math.log((result + x) / (result - x)) / ((double) 2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double hypot(double x, double y) {
        return Math.hypot(x, y);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double sqrt(double x) {
        return Math.sqrt(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double exp(double x) {
        return Math.exp(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double expm1(double x) {
        return Math.expm1(x);
    }

    @SinceKotlin(version = "1.2")
    public static final double log(double x, double base) {
        if (base > 0.0d) {
            if (base != 1.0d) {
                return Math.log(x) / Math.log(base);
            }
        }
        return DoubleCompanionObject.INSTANCE.getNaN();
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double ln(double x) {
        return Math.log(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double log10(double x) {
        return Math.log10(x);
    }

    @SinceKotlin(version = "1.2")
    public static final double log2(double x) {
        return Math.log(x) / LN2;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double ln1p(double x) {
        return Math.log1p(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double ceil(double x) {
        return Math.ceil(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double floor(double x) {
        return Math.floor(x);
    }

    @SinceKotlin(version = "1.2")
    public static final double truncate(double x) {
        if (!Double.isNaN(x)) {
            if (!Double.isInfinite(x)) {
                if (x > ((double) 0)) {
                    return Math.floor(x);
                }
                return Math.ceil(x);
            }
        }
        return x;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double round(double x) {
        return Math.rint(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double abs(double x) {
        return Math.abs(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double sign(double x) {
        return Math.signum(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double min(double a, double b) {
        return Math.min(a, b);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double max(double a, double b) {
        return Math.max(a, b);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double pow(double $receiver, double x) {
        return Math.pow($receiver, x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double pow(double $receiver, int n) {
        return Math.pow($receiver, (double) n);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double IEEErem(double $receiver, double divisor) {
        return Math.IEEEremainder($receiver, divisor);
    }

    private static final double getAbsoluteValue(double $receiver) {
        return Math.abs($receiver);
    }

    private static final double getSign(double $receiver) {
        return Math.signum($receiver);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double withSign(double $receiver, double sign) {
        return Math.copySign($receiver, sign);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double withSign(double $receiver, int sign) {
        return Math.copySign($receiver, (double) sign);
    }

    private static final double getUlp(double $receiver) {
        return Math.ulp($receiver);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double nextUp(double $receiver) {
        return Math.nextUp($receiver);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double nextDown(double $receiver) {
        return Math.nextAfter($receiver, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double nextTowards(double $receiver, double to) {
        return Math.nextAfter($receiver, to);
    }

    @SinceKotlin(version = "1.2")
    public static final int roundToInt(double $receiver) {
        if (Double.isNaN($receiver)) {
            throw new IllegalArgumentException("Cannot round NaN value.");
        }
        int i = Integer.MAX_VALUE;
        if ($receiver <= ((double) 2147483647)) {
            if ($receiver < ((double) Integer.MIN_VALUE)) {
                i = Integer.MIN_VALUE;
            } else {
                i = (int) Math.round($receiver);
            }
        }
        return i;
    }

    @SinceKotlin(version = "1.2")
    public static final long roundToLong(double $receiver) {
        if (!Double.isNaN($receiver)) {
            return Math.round($receiver);
        }
        throw new IllegalArgumentException("Cannot round NaN value.");
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float sin(float x) {
        return (float) Math.sin((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float cos(float x) {
        return (float) Math.cos((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float tan(float x) {
        return (float) Math.tan((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float asin(float x) {
        return (float) Math.asin((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float acos(float x) {
        return (float) Math.acos((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float atan(float x) {
        return (float) Math.atan((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float atan2(float y, float x) {
        return (float) Math.atan2((double) y, (double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float sinh(float x) {
        return (float) Math.sinh((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float cosh(float x) {
        return (float) Math.cosh((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float tanh(float x) {
        return (float) Math.tanh((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float asinh(float x) {
        return (float) asinh((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float acosh(float x) {
        return (float) acosh((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float atanh(float x) {
        return (float) atanh((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float hypot(float x, float y) {
        return (float) Math.hypot((double) x, (double) y);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float sqrt(float x) {
        return (float) Math.sqrt((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float exp(float x) {
        return (float) Math.exp((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float expm1(float x) {
        return (float) Math.expm1((double) x);
    }

    @SinceKotlin(version = "1.2")
    public static final float log(float x, float base) {
        if (base > 0.0f) {
            if (base != 1.0f) {
                return (float) (Math.log((double) x) / Math.log((double) base));
            }
        }
        return FloatCompanionObject.INSTANCE.getNaN();
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float ln(float x) {
        return (float) Math.log((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float log10(float x) {
        return (float) Math.log10((double) x);
    }

    @SinceKotlin(version = "1.2")
    public static final float log2(float x) {
        return (float) (Math.log((double) x) / LN2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float ln1p(float x) {
        return (float) Math.log1p((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float ceil(float x) {
        return (float) Math.ceil((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float floor(float x) {
        return (float) Math.floor((double) x);
    }

    @SinceKotlin(version = "1.2")
    public static final float truncate(float x) {
        if (!Float.isNaN(x)) {
            if (!Float.isInfinite(x)) {
                if (x > ((float) 0)) {
                    return (float) Math.floor((double) x);
                }
                return (float) Math.ceil((double) x);
            }
        }
        return x;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float round(float x) {
        return (float) Math.rint((double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float abs(float x) {
        return Math.abs(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float sign(float x) {
        return Math.signum(x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float min(float a, float b) {
        return Math.min(a, b);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float max(float a, float b) {
        return Math.max(a, b);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float pow(float $receiver, float x) {
        return (float) Math.pow((double) $receiver, (double) x);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float pow(float $receiver, int n) {
        return (float) Math.pow((double) $receiver, (double) n);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float IEEErem(float $receiver, float divisor) {
        return (float) Math.IEEEremainder((double) $receiver, (double) divisor);
    }

    private static final float getAbsoluteValue(float $receiver) {
        return Math.abs($receiver);
    }

    private static final float getSign(float $receiver) {
        return Math.signum($receiver);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float withSign(float $receiver, float sign) {
        return Math.copySign($receiver, sign);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float withSign(float $receiver, int sign) {
        return Math.copySign($receiver, (float) sign);
    }

    private static final float getUlp(float $receiver) {
        return Math.ulp($receiver);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float nextUp(float $receiver) {
        return Math.nextUp($receiver);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float nextDown(float $receiver) {
        return Math.nextAfter($receiver, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float nextTowards(float $receiver, float to) {
        return Math.nextAfter($receiver, (double) to);
    }

    @SinceKotlin(version = "1.2")
    public static final int roundToInt(float $receiver) {
        if (!Float.isNaN($receiver)) {
            return Math.round($receiver);
        }
        throw new IllegalArgumentException("Cannot round NaN value.");
    }

    @SinceKotlin(version = "1.2")
    public static final long roundToLong(float $receiver) {
        return roundToLong((double) $receiver);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final int abs(int n) {
        return Math.abs(n);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final int min(int a, int b) {
        return Math.min(a, b);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final int max(int a, int b) {
        return Math.max(a, b);
    }

    private static final int getAbsoluteValue(int $receiver) {
        return Math.abs($receiver);
    }

    public static final int getSign(int $receiver) {
        int i;
        if ($receiver < 0) {
            i = -1;
        } else if ($receiver > 0) {
            i = 1;
        } else {
            i = 0;
        }
        return i;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final long abs(long n) {
        return Math.abs(n);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final long min(long a, long b) {
        return Math.min(a, b);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final long max(long a, long b) {
        return Math.max(a, b);
    }

    private static final long getAbsoluteValue(long $receiver) {
        return Math.abs($receiver);
    }

    public static final int getSign(long $receiver) {
        int i;
        if ($receiver < 0) {
            i = -1;
        } else if ($receiver > 0) {
            i = 1;
        } else {
            i = 0;
        }
        return i;
    }
}
