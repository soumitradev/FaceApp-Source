package kotlin.text;

import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.NativeProtocol;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.Grouping;
import kotlin.collections.IndexedValue;
import kotlin.collections.IndexingIterable;
import kotlin.collections.SlidingWindowKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.sequences.Sequence;
import org.catrobat.catroid.common.BrickValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000Ú\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u001f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a!\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a\u0010\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b*\u00020\u0002\u001a\u0010\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n*\u00020\u0002\u001aE\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\b\u001a3\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00050\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\b\u001aM\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\b\u001aN\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u00020\u00050\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\b¢\u0006\u0002\u0010\u0018\u001ah\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\b¢\u0006\u0002\u0010\u0019\u001a`\u0010\u001a\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\b¢\u0006\u0002\u0010\u0018\u001a\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001fH\u0007\u001a4\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H 0\u0004H\u0007\u001a\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001d0\n*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001fH\u0007\u001a4\u0010!\u001a\b\u0012\u0004\u0012\u0002H 0\n\"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H 0\u0004H\u0007\u001a\r\u0010\"\u001a\u00020\u001f*\u00020\u0002H\b\u001a!\u0010\"\u001a\u00020\u001f*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a\u0012\u0010#\u001a\u00020\u0002*\u00020\u00022\u0006\u0010$\u001a\u00020\u001f\u001a\u0012\u0010#\u001a\u00020\u001d*\u00020\u001d2\u0006\u0010$\u001a\u00020\u001f\u001a\u0012\u0010%\u001a\u00020\u0002*\u00020\u00022\u0006\u0010$\u001a\u00020\u001f\u001a\u0012\u0010%\u001a\u00020\u001d*\u00020\u001d2\u0006\u0010$\u001a\u00020\u001f\u001a!\u0010&\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a!\u0010&\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a!\u0010'\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a!\u0010'\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a\u0015\u0010(\u001a\u00020\u0005*\u00020\u00022\u0006\u0010)\u001a\u00020\u001fH\b\u001a)\u0010*\u001a\u00020\u0005*\u00020\u00022\u0006\u0010)\u001a\u00020\u001f2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00050\u0004H\b\u001a\u001c\u0010,\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010)\u001a\u00020\u001fH\b¢\u0006\u0002\u0010-\u001a!\u0010.\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a!\u0010.\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a6\u0010/\u001a\u00020\u0002*\u00020\u00022'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000100H\b\u001a6\u0010/\u001a\u00020\u001d*\u00020\u001d2'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000100H\b\u001aQ\u00103\u001a\u0002H4\"\f\b\u0000\u00104*\u000605j\u0002`6*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000100H\b¢\u0006\u0002\u00107\u001a!\u00108\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a!\u00108\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a<\u00109\u001a\u0002H4\"\f\b\u0000\u00104*\u000605j\u0002`6*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b¢\u0006\u0002\u0010:\u001a<\u0010;\u001a\u0002H4\"\f\b\u0000\u00104*\u000605j\u0002`6*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b¢\u0006\u0002\u0010:\u001a(\u0010<\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b¢\u0006\u0002\u0010=\u001a(\u0010>\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b¢\u0006\u0002\u0010=\u001a\n\u0010?\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010?\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a\u0011\u0010@\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010A\u001a(\u0010@\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b¢\u0006\u0002\u0010=\u001a3\u0010B\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u00022\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H 0\b0\u0004H\b\u001aL\u0010C\u001a\u0002H4\"\u0004\b\u0000\u0010 \"\u0010\b\u0001\u00104*\n\u0012\u0006\b\u0000\u0012\u0002H 0D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H 0\b0\u0004H\b¢\u0006\u0002\u0010E\u001aI\u0010F\u001a\u0002H \"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010G\u001a\u0002H 2'\u0010H\u001a#\u0012\u0013\u0012\u0011H ¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 00H\b¢\u0006\u0002\u0010J\u001a^\u0010K\u001a\u0002H \"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010G\u001a\u0002H 2<\u0010H\u001a8\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0013\u0012\u0011H ¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 0LH\b¢\u0006\u0002\u0010M\u001aI\u0010N\u001a\u0002H \"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010G\u001a\u0002H 2'\u0010H\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H ¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u0002H 00H\b¢\u0006\u0002\u0010J\u001a^\u0010O\u001a\u0002H \"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010G\u001a\u0002H 2<\u0010H\u001a8\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H ¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u0002H 0LH\b¢\u0006\u0002\u0010M\u001a!\u0010P\u001a\u00020Q*\u00020\u00022\u0012\u0010R\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020Q0\u0004H\b\u001a6\u0010S\u001a\u00020Q*\u00020\u00022'\u0010R\u001a#\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020Q00H\b\u001a)\u0010T\u001a\u00020\u0005*\u00020\u00022\u0006\u0010)\u001a\u00020\u001f2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00050\u0004H\b\u001a\u0019\u0010U\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010)\u001a\u00020\u001f¢\u0006\u0002\u0010-\u001a9\u0010V\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u001c0\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\b\u001aS\u0010V\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u001c0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\b\u001aR\u0010W\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u001c\b\u0001\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050X0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\b¢\u0006\u0002\u0010\u0018\u001al\u0010W\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u001c\b\u0002\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0X0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\b¢\u0006\u0002\u0010\u0019\u001a5\u0010Y\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0Z\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0014\b\u0004\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\b\u001a!\u0010[\u001a\u00020\u001f*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a!\u0010\\\u001a\u00020\u001f*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a\n\u0010]\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010]\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a\u0011\u0010^\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010A\u001a(\u0010^\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b¢\u0006\u0002\u0010=\u001a-\u0010_\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u00022\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 0\u0004H\b\u001aB\u0010`\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u00022'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 00H\b\u001aH\u0010a\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\b\b\u0000\u0010 *\u00020b*\u00020\u00022)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H 00H\b\u001aa\u0010c\u001a\u0002H4\"\b\b\u0000\u0010 *\u00020b\"\u0010\b\u0001\u00104*\n\u0012\u0006\b\u0000\u0012\u0002H 0D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H 00H\b¢\u0006\u0002\u0010d\u001a[\u0010e\u001a\u0002H4\"\u0004\b\u0000\u0010 \"\u0010\b\u0001\u00104*\n\u0012\u0006\b\u0000\u0012\u0002H 0D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 00H\b¢\u0006\u0002\u0010d\u001a3\u0010f\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\b\b\u0000\u0010 *\u00020b*\u00020\u00022\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H 0\u0004H\b\u001aL\u0010g\u001a\u0002H4\"\b\b\u0000\u0010 *\u00020b\"\u0010\b\u0001\u00104*\n\u0012\u0006\b\u0000\u0012\u0002H 0D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H 0\u0004H\b¢\u0006\u0002\u0010E\u001aF\u0010h\u001a\u0002H4\"\u0004\b\u0000\u0010 \"\u0010\b\u0001\u00104*\n\u0012\u0006\b\u0000\u0012\u0002H 0D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 0\u0004H\b¢\u0006\u0002\u0010E\u001a\u0011\u0010i\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010A\u001a8\u0010j\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010 *\b\u0012\u0004\u0012\u0002H 0k*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 0\u0004H\b¢\u0006\u0002\u0010=\u001a-\u0010m\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010n\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050oj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`p¢\u0006\u0002\u0010q\u001a\u0011\u0010r\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010A\u001a8\u0010s\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010 *\b\u0012\u0004\u0012\u0002H 0k*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 0\u0004H\b¢\u0006\u0002\u0010=\u001a-\u0010t\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010n\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050oj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`p¢\u0006\u0002\u0010q\u001a\n\u0010u\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010u\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a0\u0010v\u001a\u0002Hw\"\b\b\u0000\u0010w*\u00020\u0002*\u0002Hw2\u0012\u0010R\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020Q0\u0004H\b¢\u0006\u0002\u0010x\u001a-\u0010y\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a-\u0010y\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d0\u0010*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a6\u0010z\u001a\u00020\u0005*\u00020\u00022'\u0010H\u001a#\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000500H\b\u001aK\u0010{\u001a\u00020\u0005*\u00020\u00022<\u0010H\u001a8\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050LH\b\u001a6\u0010|\u001a\u00020\u0005*\u00020\u00022'\u0010H\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u000500H\b\u001aK\u0010}\u001a\u00020\u0005*\u00020\u00022<\u0010H\u001a8\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u00050LH\b\u001a\n\u0010~\u001a\u00020\u0002*\u00020\u0002\u001a\r\u0010~\u001a\u00020\u001d*\u00020\u001dH\b\u001a\n\u0010\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a\u0012\u0010\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010A\u001a)\u0010\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b¢\u0006\u0002\u0010=\u001a\u001a\u0010\u0001\u001a\u00020\u0002*\u00020\u00022\r\u0010\u0001\u001a\b\u0012\u0004\u0012\u00020\u001f0\b\u001a\u0015\u0010\u0001\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0001\u001a\u00030\u0001\u001a\u001d\u0010\u0001\u001a\u00020\u001d*\u00020\u001d2\r\u0010\u0001\u001a\b\u0012\u0004\u0012\u00020\u001f0\bH\b\u001a\u0015\u0010\u0001\u001a\u00020\u001d*\u00020\u001d2\b\u0010\u0001\u001a\u00030\u0001\u001a\"\u0010\u0001\u001a\u00020\u001f*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u001f0\u0004H\b\u001a$\u0010\u0001\u001a\u00030\u0001*\u00020\u00022\u0013\u0010l\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0012\u0005\u0012\u00030\u00010\u0004H\b\u001a\u0013\u0010\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010$\u001a\u00020\u001f\u001a\u0013\u0010\u0001\u001a\u00020\u001d*\u00020\u001d2\u0006\u0010$\u001a\u00020\u001f\u001a\u0013\u0010\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010$\u001a\u00020\u001f\u001a\u0013\u0010\u0001\u001a\u00020\u001d*\u00020\u001d2\u0006\u0010$\u001a\u00020\u001f\u001a\"\u0010\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a\"\u0010\u0001\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a\"\u0010\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a\"\u0010\u0001\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\b\u001a+\u0010\u0001\u001a\u0002H4\"\u0010\b\u0000\u00104*\n\u0012\u0006\b\u0000\u0012\u00020\u00050D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H4¢\u0006\u0003\u0010\u0001\u001a\u001d\u0010\u0001\u001a\u0014\u0012\u0004\u0012\u00020\u00050\u0001j\t\u0012\u0004\u0012\u00020\u0005`\u0001*\u00020\u0002\u001a\u0011\u0010\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001c*\u00020\u0002\u001a\u0011\u0010\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050X*\u00020\u0002\u001a\u0012\u0010\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050\u0001*\u00020\u0002\u001a\u0012\u0010\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050\u0001*\u00020\u0002\u001a1\u0010\u0001\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\t\b\u0002\u0010\u0001\u001a\u00020\u001f2\t\b\u0002\u0010\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u0001\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\t\b\u0002\u0010\u0001\u001a\u00020\u001f2\t\b\u0002\u0010\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H 0\u0004H\u0007\u001a1\u0010\u0001\u001a\b\u0012\u0004\u0012\u00020\u001d0\n*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\t\b\u0002\u0010\u0001\u001a\u00020\u001f2\t\b\u0002\u0010\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u0001\u001a\b\u0012\u0004\u0012\u0002H 0\n\"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\t\b\u0002\u0010\u0001\u001a\u00020\u001f2\t\b\u0002\u0010\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H 0\u0004H\u0007\u001a\u0018\u0010\u0001\u001a\u000f\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020\u00050\u00010\b*\u00020\u0002\u001a)\u0010\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001c*\u00020\u00022\u0007\u0010\u0001\u001a\u00020\u0002H\u0004\u001a]\u0010\u0001\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u001c\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0007\u0010\u0001\u001a\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b1\u0012\t\b2\u0012\u0005\b\b(\u0001\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b1\u0012\t\b2\u0012\u0005\b\b(\u0001\u0012\u0004\u0012\u0002H\u000e00H\b\u001a\u001f\u0010 \u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001c*\u00020\u0002H\u0007\u001aT\u0010 \u0001\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b1\u0012\t\b2\u0012\u0005\b\b(\u0001\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b1\u0012\t\b2\u0012\u0005\b\b(\u0001\u0012\u0004\u0012\u0002H 00H\b¨\u0006¡\u0001"}, d2 = {"all", "", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "associate", "", "K", "V", "transform", "Lkotlin/Pair;", "associateBy", "keySelector", "valueTransform", "associateByTo", "M", "", "destination", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "associateTo", "chunked", "", "", "size", "", "R", "chunkedSequence", "count", "drop", "n", "dropLast", "dropLastWhile", "dropWhile", "elementAt", "index", "elementAtOrElse", "defaultValue", "elementAtOrNull", "(Ljava/lang/CharSequence;I)Ljava/lang/Character;", "filter", "filterIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "filterIndexedTo", "C", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function2;)Ljava/lang/Appendable;", "filterNot", "filterNotTo", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Appendable;", "filterTo", "find", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Character;", "findLast", "first", "firstOrNull", "(Ljava/lang/CharSequence;)Ljava/lang/Character;", "flatMap", "flatMapTo", "", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "fold", "initial", "operation", "acc", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "foldIndexed", "Lkotlin/Function3;", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "foldRight", "foldRightIndexed", "forEach", "", "action", "forEachIndexed", "getOrElse", "getOrNull", "groupBy", "groupByTo", "", "groupingBy", "Lkotlin/collections/Grouping;", "indexOfFirst", "indexOfLast", "last", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "", "mapIndexedNotNullTo", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;", "mapIndexedTo", "mapNotNull", "mapNotNullTo", "mapTo", "max", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/CharSequence;Ljava/util/Comparator;)Ljava/lang/Character;", "min", "minBy", "minWith", "none", "onEach", "S", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/CharSequence;", "partition", "reduce", "reduceIndexed", "reduceRight", "reduceRightIndexed", "reversed", "single", "singleOrNull", "slice", "indices", "Lkotlin/ranges/IntRange;", "sumBy", "sumByDouble", "", "take", "takeLast", "takeLastWhile", "takeWhile", "toCollection", "(Ljava/lang/CharSequence;Ljava/util/Collection;)Ljava/util/Collection;", "toHashSet", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "toList", "toMutableList", "toSet", "", "toSortedSet", "Ljava/util/SortedSet;", "windowed", "step", "partialWindows", "windowedSequence", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "zipWithNext", "kotlin-stdlib"}, k = 5, mv = {1, 1, 10}, xi = 1, xs = "kotlin/text/StringsKt")
/* compiled from: _Strings.kt */
class StringsKt___StringsKt extends StringsKt__StringsKt {
    @InlineOnly
    private static final char elementAt(@NotNull CharSequence $receiver, int index) {
        return $receiver.charAt(index);
    }

    @InlineOnly
    private static final char elementAtOrElse(@NotNull CharSequence $receiver, int index, Function1<? super Integer, Character> defaultValue) {
        return (index < 0 || index > StringsKt__StringsKt.getLastIndex($receiver)) ? ((Character) defaultValue.invoke(Integer.valueOf(index))).charValue() : $receiver.charAt(index);
    }

    @InlineOnly
    private static final Character elementAtOrNull(@NotNull CharSequence $receiver, int index) {
        return getOrNull($receiver, index);
    }

    @InlineOnly
    private static final Character find(@NotNull CharSequence $receiver, Function1<? super Character, Boolean> predicate) {
        CharSequence $receiver$iv = $receiver;
        for (int i = 0; i < $receiver$iv.length(); i++) {
            char element$iv = $receiver$iv.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(element$iv))).booleanValue()) {
                return Character.valueOf(element$iv);
            }
        }
        return null;
    }

    @InlineOnly
    private static final Character findLast(@NotNull CharSequence $receiver, Function1<? super Character, Boolean> predicate) {
        CharSequence $receiver$iv = $receiver;
        for (int index$iv = $receiver$iv.length() - 1; index$iv >= 0; index$iv--) {
            char element$iv = $receiver$iv.charAt(index$iv);
            if (((Boolean) predicate.invoke(Character.valueOf(element$iv))).booleanValue()) {
                return Character.valueOf(element$iv);
            }
        }
        return null;
    }

    public static final char first(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if (($receiver.length() == 0 ? 1 : null) == null) {
            return $receiver.charAt(0);
        }
        throw new NoSuchElementException("Char sequence is empty.");
    }

    public static final char first(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < $receiver.length(); i++) {
            char element = $receiver.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(element))).booleanValue()) {
                return element;
            }
        }
        throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
    }

    @Nullable
    public static final Character firstOrNull(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return ($receiver.length() == 0 ? 1 : null) != null ? null : Character.valueOf($receiver.charAt(0));
    }

    @Nullable
    public static final Character firstOrNull(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < $receiver.length(); i++) {
            char element = $receiver.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(element))).booleanValue()) {
                return Character.valueOf(element);
            }
        }
        return null;
    }

    @InlineOnly
    private static final char getOrElse(@NotNull CharSequence $receiver, int index, Function1<? super Integer, Character> defaultValue) {
        return (index < 0 || index > StringsKt__StringsKt.getLastIndex($receiver)) ? ((Character) defaultValue.invoke(Integer.valueOf(index))).charValue() : $receiver.charAt(index);
    }

    @Nullable
    public static final Character getOrNull(@NotNull CharSequence $receiver, int index) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (index < 0 || index > StringsKt__StringsKt.getLastIndex($receiver)) ? null : Character.valueOf($receiver.charAt(index));
    }

    public static final int indexOfFirst(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = $receiver.length();
        for (int index = 0; index < length; index++) {
            if (((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(index)))).booleanValue()) {
                return index;
            }
        }
        return -1;
    }

    public static final int indexOfLast(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int index = $receiver.length() - 1; index >= 0; index--) {
            if (((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(index)))).booleanValue()) {
                return index;
            }
        }
        return -1;
    }

    public static final char last(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if (($receiver.length() == 0 ? 1 : null) == null) {
            return $receiver.charAt(StringsKt__StringsKt.getLastIndex($receiver));
        }
        throw new NoSuchElementException("Char sequence is empty.");
    }

    public static final char last(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int index = $receiver.length() - 1; index >= 0; index--) {
            char element = $receiver.charAt(index);
            if (((Boolean) predicate.invoke(Character.valueOf(element))).booleanValue()) {
                return element;
            }
        }
        throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
    }

    @Nullable
    public static final Character lastOrNull(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return ($receiver.length() == 0 ? 1 : null) != null ? null : Character.valueOf($receiver.charAt($receiver.length() - 1));
    }

    @Nullable
    public static final Character lastOrNull(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int index = $receiver.length() - 1; index >= 0; index--) {
            char element = $receiver.charAt(index);
            if (((Boolean) predicate.invoke(Character.valueOf(element))).booleanValue()) {
                return Character.valueOf(element);
            }
        }
        return null;
    }

    public static final char single(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        switch ($receiver.length()) {
            case 0:
                throw new NoSuchElementException("Char sequence is empty.");
            case 1:
                return $receiver.charAt(0);
            default:
                throw new IllegalArgumentException("Char sequence has more than one element.");
        }
    }

    public static final char single(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Character single = null;
        boolean found = false;
        for (int i = 0; i < $receiver.length(); i++) {
            char element = $receiver.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(element))).booleanValue()) {
                if (found) {
                    throw new IllegalArgumentException("Char sequence contains more than one matching element.");
                }
                single = Character.valueOf(element);
                found = true;
            }
        }
        if (!found) {
            throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
        } else if (single != null) {
            return single.charValue();
        } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Char");
        }
    }

    @Nullable
    public static final Character singleOrNull(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.length() == 1 ? Character.valueOf($receiver.charAt(0)) : null;
    }

    @Nullable
    public static final Character singleOrNull(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Character single = null;
        boolean found = false;
        for (int i = 0; i < $receiver.length(); i++) {
            char element = $receiver.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(element))).booleanValue()) {
                if (found) {
                    return null;
                }
                single = Character.valueOf(element);
                found = true;
            }
        }
        if (found) {
            return single;
        }
        return null;
    }

    @NotNull
    public static final CharSequence drop(@NotNull CharSequence $receiver, int n) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ((n >= 0 ? 1 : null) != null) {
            return $receiver.subSequence(RangesKt___RangesKt.coerceAtMost(n, $receiver.length()), $receiver.length());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested character count ");
        stringBuilder.append(n);
        stringBuilder.append(" is less than zero.");
        throw new IllegalArgumentException(stringBuilder.toString().toString());
    }

    @NotNull
    public static final String drop(@NotNull String $receiver, int n) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ((n >= 0 ? 1 : null) == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested character count ");
            stringBuilder.append(n);
            stringBuilder.append(" is less than zero.");
            throw new IllegalArgumentException(stringBuilder.toString().toString());
        }
        String substring = $receiver.substring(RangesKt___RangesKt.coerceAtMost(n, $receiver.length()));
        Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
        return substring;
    }

    @NotNull
    public static final CharSequence dropLast(@NotNull CharSequence $receiver, int n) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ((n >= 0 ? 1 : null) != null) {
            return take($receiver, RangesKt___RangesKt.coerceAtLeast($receiver.length() - n, 0));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested character count ");
        stringBuilder.append(n);
        stringBuilder.append(" is less than zero.");
        throw new IllegalArgumentException(stringBuilder.toString().toString());
    }

    @NotNull
    public static final String dropLast(@NotNull String $receiver, int n) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ((n >= 0 ? 1 : null) != null) {
            return take($receiver, RangesKt___RangesKt.coerceAtLeast($receiver.length() - n, 0));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested character count ");
        stringBuilder.append(n);
        stringBuilder.append(" is less than zero.");
        throw new IllegalArgumentException(stringBuilder.toString().toString());
    }

    @NotNull
    public static final CharSequence dropLastWhile(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int index = StringsKt__StringsKt.getLastIndex($receiver); index >= 0; index--) {
            if (!((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(index)))).booleanValue()) {
                return $receiver.subSequence(0, index + 1);
            }
        }
        return "";
    }

    @NotNull
    public static final String dropLastWhile(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int index = StringsKt__StringsKt.getLastIndex($receiver);
        while (index >= 0) {
            if (((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(index)))).booleanValue()) {
                index--;
            } else {
                String substring = $receiver.substring(0, index + 1);
                Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                return substring;
            }
        }
        return "";
    }

    @NotNull
    public static final CharSequence dropWhile(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = $receiver.length();
        for (int index = 0; index < length; index++) {
            if (!((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(index)))).booleanValue()) {
                return $receiver.subSequence(index, $receiver.length());
            }
        }
        return "";
    }

    @NotNull
    public static final String dropWhile(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = $receiver.length();
        int index = 0;
        while (index < length) {
            if (((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(index)))).booleanValue()) {
                index++;
            } else {
                String substring = $receiver.substring(index);
                Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
                return substring;
            }
        }
        return "";
    }

    @NotNull
    public static final CharSequence filter(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Appendable destination$iv = new StringBuilder();
        CharSequence $receiver$iv = $receiver;
        int length = $receiver$iv.length();
        for (int index$iv = 0; index$iv < length; index$iv++) {
            char element$iv = $receiver$iv.charAt(index$iv);
            if (((Boolean) predicate.invoke(Character.valueOf(element$iv))).booleanValue()) {
                destination$iv.append(element$iv);
            }
        }
        return (CharSequence) destination$iv;
    }

    @NotNull
    public static final String filter(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence $receiver$iv = $receiver;
        Appendable destination$iv = new StringBuilder();
        int length = $receiver$iv.length();
        for (int index$iv = 0; index$iv < length; index$iv++) {
            char element$iv = $receiver$iv.charAt(index$iv);
            if (((Boolean) predicate.invoke(Character.valueOf(element$iv))).booleanValue()) {
                destination$iv.append(element$iv);
            }
        }
        String stringBuilder = ((StringBuilder) destination$iv).toString();
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "filterTo(StringBuilder(), predicate).toString()");
        return stringBuilder;
    }

    @NotNull
    public static final CharSequence filterIndexed(@NotNull CharSequence $receiver, @NotNull Function2<? super Integer, ? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Appendable destination$iv = new StringBuilder();
        CharSequence $receiver$iv$iv = $receiver;
        int i = 0;
        int $i$f$forEachIndexed = 0;
        int index$iv = 0;
        while (i < $receiver$iv$iv.length()) {
            int index$iv$iv = index$iv + 1;
            char element$iv = $receiver$iv$iv.charAt(i);
            if (((Boolean) predicate.invoke(Integer.valueOf(index$iv), Character.valueOf(element$iv))).booleanValue()) {
                destination$iv.append(element$iv);
            }
            i++;
            index$iv = index$iv$iv;
        }
        return (CharSequence) destination$iv;
    }

    @NotNull
    public static final String filterIndexed(@NotNull String $receiver, @NotNull Function2<? super Integer, ? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Appendable destination$iv = new StringBuilder();
        CharSequence $receiver$iv$iv = $receiver;
        int i = 0;
        int $i$f$forEachIndexed = 0;
        int index$iv = 0;
        while (i < $receiver$iv$iv.length()) {
            int index$iv$iv = index$iv + 1;
            char element$iv = $receiver$iv$iv.charAt(i);
            if (((Boolean) predicate.invoke(Integer.valueOf(index$iv), Character.valueOf(element$iv))).booleanValue()) {
                destination$iv.append(element$iv);
            }
            i++;
            index$iv = index$iv$iv;
        }
        String stringBuilder = ((StringBuilder) destination$iv).toString();
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "filterIndexedTo(StringBu…(), predicate).toString()");
        return stringBuilder;
    }

    @NotNull
    public static final <C extends Appendable> C filterIndexedTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function2<? super Integer, ? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence $receiver$iv = $receiver;
        int index = 0;
        int i = 0;
        while (i < $receiver$iv.length()) {
            int index$iv = index + 1;
            char element = $receiver$iv.charAt(i);
            if (((Boolean) predicate.invoke(Integer.valueOf(index), Character.valueOf(element))).booleanValue()) {
                destination.append(element);
            }
            i++;
            index = index$iv;
        }
        return destination;
    }

    @NotNull
    public static final CharSequence filterNot(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Appendable destination$iv = new StringBuilder();
        CharSequence $receiver$iv = $receiver;
        for (int i = 0; i < $receiver$iv.length(); i++) {
            char element$iv = $receiver$iv.charAt(i);
            if (!((Boolean) predicate.invoke(Character.valueOf(element$iv))).booleanValue()) {
                destination$iv.append(element$iv);
            }
        }
        return (CharSequence) destination$iv;
    }

    @NotNull
    public static final String filterNot(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence $receiver$iv = $receiver;
        Appendable destination$iv = new StringBuilder();
        for (int i = 0; i < $receiver$iv.length(); i++) {
            char element$iv = $receiver$iv.charAt(i);
            if (!((Boolean) predicate.invoke(Character.valueOf(element$iv))).booleanValue()) {
                destination$iv.append(element$iv);
            }
        }
        String stringBuilder = ((StringBuilder) destination$iv).toString();
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "filterNotTo(StringBuilder(), predicate).toString()");
        return stringBuilder;
    }

    @NotNull
    public static final <C extends Appendable> C filterNotTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < $receiver.length(); i++) {
            char element = $receiver.charAt(i);
            if (!((Boolean) predicate.invoke(Character.valueOf(element))).booleanValue()) {
                destination.append(element);
            }
        }
        return destination;
    }

    @NotNull
    public static final <C extends Appendable> C filterTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = $receiver.length();
        for (int index = 0; index < length; index++) {
            char element = $receiver.charAt(index);
            if (((Boolean) predicate.invoke(Character.valueOf(element))).booleanValue()) {
                destination.append(element);
            }
        }
        return destination;
    }

    @NotNull
    public static final CharSequence slice(@NotNull CharSequence $receiver, @NotNull IntRange indices) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        if (indices.isEmpty()) {
            return "";
        }
        return StringsKt__StringsKt.subSequence($receiver, indices);
    }

    @NotNull
    public static final String slice(@NotNull String $receiver, @NotNull IntRange indices) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        if (indices.isEmpty()) {
            return "";
        }
        return StringsKt__StringsKt.substring($receiver, indices);
    }

    @NotNull
    public static final CharSequence slice(@NotNull CharSequence $receiver, @NotNull Iterable<Integer> indices) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        int size = CollectionsKt__IterablesKt.collectionSizeOrDefault(indices, 10);
        if (size == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder(size);
        for (Number i : indices) {
            result.append($receiver.charAt(i.intValue()));
        }
        return result;
    }

    @InlineOnly
    private static final String slice(@NotNull String $receiver, Iterable<Integer> indices) {
        if ($receiver != null) {
            return slice((CharSequence) $receiver, (Iterable) indices).toString();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
    }

    @NotNull
    public static final CharSequence take(@NotNull CharSequence $receiver, int n) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ((n >= 0 ? 1 : null) != null) {
            return $receiver.subSequence(0, RangesKt___RangesKt.coerceAtMost(n, $receiver.length()));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested character count ");
        stringBuilder.append(n);
        stringBuilder.append(" is less than zero.");
        throw new IllegalArgumentException(stringBuilder.toString().toString());
    }

    @NotNull
    public static final String take(@NotNull String $receiver, int n) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ((n >= 0 ? 1 : null) == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested character count ");
            stringBuilder.append(n);
            stringBuilder.append(" is less than zero.");
            throw new IllegalArgumentException(stringBuilder.toString().toString());
        }
        String substring = $receiver.substring(0, RangesKt___RangesKt.coerceAtMost(n, $receiver.length()));
        Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    @NotNull
    public static final CharSequence takeLast(@NotNull CharSequence $receiver, int n) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ((n >= 0 ? 1 : null) == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested character count ");
            stringBuilder.append(n);
            stringBuilder.append(" is less than zero.");
            throw new IllegalArgumentException(stringBuilder.toString().toString());
        }
        int length = $receiver.length();
        return $receiver.subSequence(length - RangesKt___RangesKt.coerceAtMost(n, length), length);
    }

    @NotNull
    public static final String takeLast(@NotNull String $receiver, int n) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ((n >= 0 ? 1 : null) == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested character count ");
            stringBuilder.append(n);
            stringBuilder.append(" is less than zero.");
            throw new IllegalArgumentException(stringBuilder.toString().toString());
        }
        int length = $receiver.length();
        String substring = $receiver.substring(length - RangesKt___RangesKt.coerceAtMost(n, length));
        Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
        return substring;
    }

    @NotNull
    public static final CharSequence takeLastWhile(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int index = StringsKt__StringsKt.getLastIndex($receiver); index >= 0; index--) {
            if (!((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(index)))).booleanValue()) {
                return $receiver.subSequence(index + 1, $receiver.length());
            }
        }
        return $receiver.subSequence(0, $receiver.length());
    }

    @NotNull
    public static final String takeLastWhile(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int index = StringsKt__StringsKt.getLastIndex($receiver);
        while (index >= 0) {
            if (((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(index)))).booleanValue()) {
                index--;
            } else {
                String substring = $receiver.substring(index + 1);
                Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
                return substring;
            }
        }
        return $receiver;
    }

    @NotNull
    public static final CharSequence takeWhile(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = $receiver.length();
        for (int index = 0; index < length; index++) {
            if (!((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(index)))).booleanValue()) {
                return $receiver.subSequence(0, index);
            }
        }
        return $receiver.subSequence(0, $receiver.length());
    }

    @NotNull
    public static final String takeWhile(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = $receiver.length();
        int index = 0;
        while (index < length) {
            if (((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(index)))).booleanValue()) {
                index++;
            } else {
                String substring = $receiver.substring(0, index);
                Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                return substring;
            }
        }
        return $receiver;
    }

    @NotNull
    public static final CharSequence reversed(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        StringBuilder reverse = new StringBuilder($receiver).reverse();
        Intrinsics.checkExpressionValueIsNotNull(reverse, "StringBuilder(this).reverse()");
        return reverse;
    }

    @InlineOnly
    private static final String reversed(@NotNull String $receiver) {
        if ($receiver != null) {
            return reversed((CharSequence) $receiver).toString();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
    }

    @NotNull
    public static final <K, V> Map<K, V> associate(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends Pair<? extends K, ? extends V>> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Map destination$iv = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsKt.mapCapacity($receiver.length()), 16));
        CharSequence $receiver$iv = $receiver;
        int i = 0;
        int $i$f$associateTo = 0;
        while (i < $receiver$iv.length()) {
            Pair pair = (Pair) transform.invoke(Character.valueOf($receiver$iv.charAt(i)));
            destination$iv.put(pair.getFirst(), pair.getSecond());
            i++;
        }
        return destination$iv;
    }

    @NotNull
    public static final <K> Map<K, Character> associateBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Map destination$iv = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsKt.mapCapacity($receiver.length()), 16));
        CharSequence $receiver$iv = $receiver;
        int i = 0;
        int $i$f$associateByTo = 0;
        while (i < $receiver$iv.length()) {
            char element$iv = $receiver$iv.charAt(i);
            destination$iv.put(keySelector.invoke(Character.valueOf(element$iv)), Character.valueOf(element$iv));
            i++;
        }
        return destination$iv;
    }

    @NotNull
    public static final <K, V> Map<K, V> associateBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        Map destination$iv = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsKt.mapCapacity($receiver.length()), 16));
        CharSequence $receiver$iv = $receiver;
        int i = 0;
        int $i$f$associateByTo = 0;
        while (i < $receiver$iv.length()) {
            char element$iv = $receiver$iv.charAt(i);
            destination$iv.put(keySelector.invoke(Character.valueOf(element$iv)), valueTransform.invoke(Character.valueOf(element$iv)));
            i++;
        }
        return destination$iv;
    }

    @NotNull
    public static final <K, M extends Map<? super K, ? super Character>> M associateByTo(@NotNull CharSequence $receiver, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        for (int i = 0; i < $receiver.length(); i++) {
            char element = $receiver.charAt(i);
            destination.put(keySelector.invoke(Character.valueOf(element)), Character.valueOf(element));
        }
        return destination;
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M associateByTo(@NotNull CharSequence $receiver, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        for (int i = 0; i < $receiver.length(); i++) {
            char element = $receiver.charAt(i);
            destination.put(keySelector.invoke(Character.valueOf(element)), valueTransform.invoke(Character.valueOf(element)));
        }
        return destination;
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M associateTo(@NotNull CharSequence $receiver, @NotNull M destination, @NotNull Function1<? super Character, ? extends Pair<? extends K, ? extends V>> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (int i = 0; i < $receiver.length(); i++) {
            Pair pair = (Pair) transform.invoke(Character.valueOf($receiver.charAt(i)));
            destination.put(pair.getFirst(), pair.getSecond());
        }
        return destination;
    }

    @NotNull
    public static final <C extends Collection<? super Character>> C toCollection(@NotNull CharSequence $receiver, @NotNull C destination) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        for (int i = 0; i < $receiver.length(); i++) {
            destination.add(Character.valueOf($receiver.charAt(i)));
        }
        return destination;
    }

    @NotNull
    public static final HashSet<Character> toHashSet(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (HashSet) toCollection($receiver, new HashSet(MapsKt__MapsKt.mapCapacity($receiver.length())));
    }

    @NotNull
    public static final List<Character> toList(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        switch ($receiver.length()) {
            case 0:
                return CollectionsKt__CollectionsKt.emptyList();
            case 1:
                return CollectionsKt__CollectionsKt.listOf((Object) Character.valueOf($receiver.charAt(0)));
            default:
                return toMutableList($receiver);
        }
    }

    @NotNull
    public static final List<Character> toMutableList(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (List) toCollection($receiver, new ArrayList($receiver.length()));
    }

    @NotNull
    public static final Set<Character> toSet(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        switch ($receiver.length()) {
            case 0:
                return SetsKt__SetsKt.emptySet();
            case 1:
                return SetsKt__SetsKt.setOf((Object) Character.valueOf($receiver.charAt(0)));
            default:
                return (Set) toCollection($receiver, new LinkedHashSet(MapsKt__MapsKt.mapCapacity($receiver.length())));
        }
    }

    @NotNull
    public static final SortedSet<Character> toSortedSet(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (SortedSet) toCollection($receiver, new TreeSet());
    }

    @NotNull
    public static final <R> List<R> flatMap(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends Iterable<? extends R>> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Collection destination$iv = new ArrayList();
        CharSequence $receiver$iv = $receiver;
        for (int i = 0; i < $receiver$iv.length(); i++) {
            CollectionsKt__MutableCollectionsKt.addAll(destination$iv, (Iterable) transform.invoke(Character.valueOf($receiver$iv.charAt(i))));
        }
        return (List) destination$iv;
    }

    @NotNull
    public static final <R, C extends Collection<? super R>> C flatMapTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function1<? super Character, ? extends Iterable<? extends R>> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (int i = 0; i < $receiver.length(); i++) {
            CollectionsKt__MutableCollectionsKt.addAll((Collection) destination, (Iterable) transform.invoke(Character.valueOf($receiver.charAt(i))));
        }
        return destination;
    }

    @NotNull
    public static final <K> Map<K, List<Character>> groupBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Map destination$iv = new LinkedHashMap();
        CharSequence $receiver$iv = $receiver;
        for (int i = 0; i < $receiver$iv.length(); i++) {
            ArrayList arrayList;
            char element$iv = $receiver$iv.charAt(i);
            Object key$iv = keySelector.invoke(Character.valueOf(element$iv));
            Map $receiver$iv$iv = destination$iv;
            ArrayList value$iv$iv = $receiver$iv$iv.get(key$iv);
            if (value$iv$iv == null) {
                arrayList = new ArrayList();
                $receiver$iv$iv.put(key$iv, arrayList);
            } else {
                arrayList = value$iv$iv;
            }
            arrayList.add(Character.valueOf(element$iv));
        }
        return destination$iv;
    }

    @NotNull
    public static final <K, V> Map<K, List<V>> groupBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        Map destination$iv = new LinkedHashMap();
        CharSequence $receiver$iv = $receiver;
        for (int i = 0; i < $receiver$iv.length(); i++) {
            ArrayList arrayList;
            char element$iv = $receiver$iv.charAt(i);
            Object key$iv = keySelector.invoke(Character.valueOf(element$iv));
            Map $receiver$iv$iv = destination$iv;
            ArrayList value$iv$iv = $receiver$iv$iv.get(key$iv);
            if (value$iv$iv == null) {
                arrayList = new ArrayList();
                $receiver$iv$iv.put(key$iv, arrayList);
            } else {
                arrayList = value$iv$iv;
            }
            arrayList.add(valueTransform.invoke(Character.valueOf(element$iv)));
        }
        return destination$iv;
    }

    @NotNull
    public static final <K, M extends Map<? super K, List<Character>>> M groupByTo(@NotNull CharSequence $receiver, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        for (int i = 0; i < $receiver.length(); i++) {
            ArrayList arrayList;
            char element = $receiver.charAt(i);
            Object key = keySelector.invoke(Character.valueOf(element));
            M $receiver$iv = destination;
            ArrayList value$iv = $receiver$iv.get(key);
            if (value$iv == null) {
                arrayList = new ArrayList();
                $receiver$iv.put(key, arrayList);
            } else {
                arrayList = value$iv;
            }
            arrayList.add(Character.valueOf(element));
        }
        return destination;
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, List<V>>> M groupByTo(@NotNull CharSequence $receiver, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        for (int i = 0; i < $receiver.length(); i++) {
            ArrayList arrayList;
            char element = $receiver.charAt(i);
            Object key = keySelector.invoke(Character.valueOf(element));
            M $receiver$iv = destination;
            ArrayList value$iv = $receiver$iv.get(key);
            if (value$iv == null) {
                arrayList = new ArrayList();
                $receiver$iv.put(key, arrayList);
            } else {
                arrayList = value$iv;
            }
            arrayList.add(valueTransform.invoke(Character.valueOf(element)));
        }
        return destination;
    }

    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <K> Grouping<Character, K> groupingBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        return new StringsKt___StringsKt$groupingBy$1($receiver, keySelector);
    }

    @NotNull
    public static final <R> List<R> map(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Collection destination$iv = new ArrayList($receiver.length());
        CharSequence $receiver$iv = $receiver;
        for (int i = 0; i < $receiver$iv.length(); i++) {
            destination$iv.add(transform.invoke(Character.valueOf($receiver$iv.charAt(i))));
        }
        return (List) destination$iv;
    }

    @NotNull
    public static final <R> List<R> mapIndexed(@NotNull CharSequence $receiver, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Collection destination$iv = new ArrayList($receiver.length());
        CharSequence $receiver$iv = $receiver;
        int index$iv = 0;
        for (int i = 0; i < $receiver$iv.length(); i++) {
            char item$iv = $receiver$iv.charAt(i);
            Integer valueOf = Integer.valueOf(index$iv);
            index$iv++;
            destination$iv.add(transform.invoke(valueOf, Character.valueOf(item$iv)));
        }
        return (List) destination$iv;
    }

    @NotNull
    public static final <R> List<R> mapIndexedNotNull(@NotNull CharSequence $receiver, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
        Function2<? super Integer, ? super Character, ? extends R> function2 = transform;
        int $i$f$mapIndexedNotNullTo = 0;
        CharSequence charSequence = $receiver;
        Intrinsics.checkParameterIsNotNull(charSequence, "$receiver");
        Intrinsics.checkParameterIsNotNull(function2, "transform");
        Collection destination$iv = new ArrayList();
        CharSequence $receiver$iv$iv = charSequence;
        int i = 0;
        int $i$f$forEachIndexed = 0;
        int index$iv = 0;
        while (i < $receiver$iv$iv.length()) {
            int index$iv$iv = index$iv + 1;
            int $i$f$mapIndexedNotNullTo2 = $i$f$mapIndexedNotNullTo;
            $i$f$mapIndexedNotNullTo = function2.invoke(Integer.valueOf(index$iv), Character.valueOf($receiver$iv$iv.charAt(i)));
            if ($i$f$mapIndexedNotNullTo != 0) {
                destination$iv.add($i$f$mapIndexedNotNullTo);
            }
            i++;
            index$iv = index$iv$iv;
            $i$f$mapIndexedNotNullTo = $i$f$mapIndexedNotNullTo2;
        }
        return (List) destination$iv;
    }

    @NotNull
    public static final <R, C extends Collection<? super R>> C mapIndexedNotNullTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence $receiver$iv = $receiver;
        int index = 0;
        int i = 0;
        while (i < $receiver$iv.length()) {
            int index$iv = index + 1;
            Object it = transform.invoke(Integer.valueOf(index), Character.valueOf($receiver$iv.charAt(i)));
            if (it != null) {
                destination.add(it);
            }
            i++;
            index = index$iv;
        }
        return destination;
    }

    @NotNull
    public static final <R, C extends Collection<? super R>> C mapIndexedTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int index = 0;
        for (int i = 0; i < $receiver.length(); i++) {
            char item = $receiver.charAt(i);
            Integer valueOf = Integer.valueOf(index);
            index++;
            destination.add(transform.invoke(valueOf, Character.valueOf(item)));
        }
        return destination;
    }

    @NotNull
    public static final <R> List<R> mapNotNull(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        Collection destination$iv = new ArrayList();
        CharSequence $receiver$iv$iv = $receiver;
        int i = 0;
        int $i$f$forEach = 0;
        while (i < $receiver$iv$iv.length()) {
            Object it$iv = transform.invoke(Character.valueOf($receiver$iv$iv.charAt(i)));
            if (it$iv != null) {
                destination$iv.add(it$iv);
            }
            i++;
        }
        return (List) destination$iv;
    }

    @NotNull
    public static final <R, C extends Collection<? super R>> C mapNotNullTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function1<? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence $receiver$iv = $receiver;
        for (int i = 0; i < $receiver$iv.length(); i++) {
            Object it = transform.invoke(Character.valueOf($receiver$iv.charAt(i)));
            if (it != null) {
                destination.add(it);
            }
        }
        return destination;
    }

    @NotNull
    public static final <R, C extends Collection<? super R>> C mapTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function1<? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (int i = 0; i < $receiver.length(); i++) {
            destination.add(transform.invoke(Character.valueOf($receiver.charAt(i))));
        }
        return destination;
    }

    @NotNull
    public static final Iterable<IndexedValue<Character>> withIndex(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new IndexingIterable(new StringsKt___StringsKt$withIndex$1($receiver));
    }

    public static final boolean all(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < $receiver.length(); i++) {
            if (!((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(i)))).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public static final boolean any(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return ($receiver.length() == 0 ? 1 : 0) ^ true;
    }

    public static final boolean any(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < $receiver.length(); i++) {
            if (((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(i)))).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    @InlineOnly
    private static final int count(@NotNull CharSequence $receiver) {
        return $receiver.length();
    }

    public static final int count(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int count = 0;
        for (int i = 0; i < $receiver.length(); i++) {
            if (((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(i)))).booleanValue()) {
                count++;
            }
        }
        return count;
    }

    public static final <R> R fold(@NotNull CharSequence $receiver, R initial, @NotNull Function2<? super R, ? super Character, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Object accumulator = initial;
        for (int i = 0; i < $receiver.length(); i++) {
            accumulator = operation.invoke(accumulator, Character.valueOf($receiver.charAt(i)));
        }
        return accumulator;
    }

    public static final <R> R foldIndexed(@NotNull CharSequence $receiver, R initial, @NotNull Function3<? super Integer, ? super R, ? super Character, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = 0;
        Object accumulator = initial;
        for (int i = 0; i < $receiver.length(); i++) {
            char element = $receiver.charAt(i);
            Integer valueOf = Integer.valueOf(index);
            index++;
            accumulator = operation.invoke(valueOf, accumulator, Character.valueOf(element));
        }
        return accumulator;
    }

    public static final <R> R foldRight(@NotNull CharSequence $receiver, R initial, @NotNull Function2<? super Character, ? super R, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = StringsKt__StringsKt.getLastIndex($receiver);
        Object accumulator = initial;
        while (index >= 0) {
            int index2 = index - 1;
            accumulator = operation.invoke(Character.valueOf($receiver.charAt(index)), accumulator);
            index = index2;
        }
        return accumulator;
    }

    public static final <R> R foldRightIndexed(@NotNull CharSequence $receiver, R initial, @NotNull Function3<? super Integer, ? super Character, ? super R, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Object accumulator = initial;
        for (int index = StringsKt__StringsKt.getLastIndex($receiver); index >= 0; index--) {
            accumulator = operation.invoke(Integer.valueOf(index), Character.valueOf($receiver.charAt(index)), accumulator);
        }
        return accumulator;
    }

    public static final void forEach(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, NativeProtocol.WEB_DIALOG_ACTION);
        for (int i = 0; i < $receiver.length(); i++) {
            action.invoke(Character.valueOf($receiver.charAt(i)));
        }
    }

    public static final void forEachIndexed(@NotNull CharSequence $receiver, @NotNull Function2<? super Integer, ? super Character, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, NativeProtocol.WEB_DIALOG_ACTION);
        int index = 0;
        for (int i = 0; i < $receiver.length(); i++) {
            char item = $receiver.charAt(i);
            Integer valueOf = Integer.valueOf(index);
            index++;
            action.invoke(valueOf, Character.valueOf(item));
        }
    }

    @Nullable
    public static final Character max(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int i = 1;
        if (($receiver.length() == 0 ? 1 : null) != null) {
            return null;
        }
        char max = $receiver.charAt(0);
        int lastIndex = StringsKt__StringsKt.getLastIndex($receiver);
        if (1 <= lastIndex) {
            while (true) {
                char e = $receiver.charAt(i);
                if (max < e) {
                    max = e;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(max);
    }

    @Nullable
    public static final <R extends Comparable<? super R>> Character maxBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends R> selector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        int i = 1;
        if (($receiver.length() == 0 ? 1 : null) != null) {
            return null;
        }
        char maxElem = $receiver.charAt(0);
        Comparable maxValue = (Comparable) selector.invoke(Character.valueOf(maxElem));
        int lastIndex = StringsKt__StringsKt.getLastIndex($receiver);
        if (1 <= lastIndex) {
            while (true) {
                char e = $receiver.charAt(i);
                Comparable v = (Comparable) selector.invoke(Character.valueOf(e));
                if (maxValue.compareTo(v) < 0) {
                    maxElem = e;
                    maxValue = v;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(maxElem);
    }

    @Nullable
    public static final Character maxWith(@NotNull CharSequence $receiver, @NotNull Comparator<? super Character> comparator) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        int i = 1;
        if (($receiver.length() == 0 ? 1 : null) != null) {
            return null;
        }
        char max = $receiver.charAt(0);
        int lastIndex = StringsKt__StringsKt.getLastIndex($receiver);
        if (1 <= lastIndex) {
            while (true) {
                char e = $receiver.charAt(i);
                if (comparator.compare(Character.valueOf(max), Character.valueOf(e)) < 0) {
                    max = e;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(max);
    }

    @Nullable
    public static final Character min(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int i = 1;
        if (($receiver.length() == 0 ? 1 : null) != null) {
            return null;
        }
        char min = $receiver.charAt(0);
        int lastIndex = StringsKt__StringsKt.getLastIndex($receiver);
        if (1 <= lastIndex) {
            while (true) {
                char e = $receiver.charAt(i);
                if (min > e) {
                    min = e;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(min);
    }

    @Nullable
    public static final <R extends Comparable<? super R>> Character minBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends R> selector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        int i = 1;
        if (($receiver.length() == 0 ? 1 : null) != null) {
            return null;
        }
        char minElem = $receiver.charAt(0);
        Comparable minValue = (Comparable) selector.invoke(Character.valueOf(minElem));
        int lastIndex = StringsKt__StringsKt.getLastIndex($receiver);
        if (1 <= lastIndex) {
            while (true) {
                char e = $receiver.charAt(i);
                Comparable v = (Comparable) selector.invoke(Character.valueOf(e));
                if (minValue.compareTo(v) > 0) {
                    minElem = e;
                    minValue = v;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(minElem);
    }

    @Nullable
    public static final Character minWith(@NotNull CharSequence $receiver, @NotNull Comparator<? super Character> comparator) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        int i = 1;
        if (($receiver.length() == 0 ? 1 : null) != null) {
            return null;
        }
        char min = $receiver.charAt(0);
        int lastIndex = StringsKt__StringsKt.getLastIndex($receiver);
        if (1 <= lastIndex) {
            while (true) {
                char e = $receiver.charAt(i);
                if (comparator.compare(Character.valueOf(min), Character.valueOf(e)) > 0) {
                    min = e;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(min);
    }

    public static final boolean none(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.length() == 0;
    }

    public static final boolean none(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < $receiver.length(); i++) {
            if (((Boolean) predicate.invoke(Character.valueOf($receiver.charAt(i)))).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    @SinceKotlin(version = "1.1")
    public static final <S extends CharSequence> S onEach(@NotNull S $receiver, @NotNull Function1<? super Character, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, NativeProtocol.WEB_DIALOG_ACTION);
        for (int i = 0; i < $receiver.length(); i++) {
            action.invoke(Character.valueOf($receiver.charAt(i)));
        }
        return $receiver;
    }

    public static final char reduce(@NotNull CharSequence $receiver, @NotNull Function2<? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = 1;
        if (($receiver.length() == 0 ? 1 : null) != null) {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char accumulator = $receiver.charAt(0);
        int lastIndex = StringsKt__StringsKt.getLastIndex($receiver);
        if (1 <= lastIndex) {
            while (true) {
                accumulator = ((Character) operation.invoke(Character.valueOf(accumulator), Character.valueOf($receiver.charAt(index)))).charValue();
                if (index == lastIndex) {
                    break;
                }
                index++;
            }
        }
        return accumulator;
    }

    public static final char reduceIndexed(@NotNull CharSequence $receiver, @NotNull Function3<? super Integer, ? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = 1;
        if (($receiver.length() == 0 ? 1 : null) != null) {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char accumulator = $receiver.charAt(0);
        int lastIndex = StringsKt__StringsKt.getLastIndex($receiver);
        if (1 <= lastIndex) {
            while (true) {
                accumulator = ((Character) operation.invoke(Integer.valueOf(index), Character.valueOf(accumulator), Character.valueOf($receiver.charAt(index)))).charValue();
                if (index == lastIndex) {
                    break;
                }
                index++;
            }
        }
        return accumulator;
    }

    public static final char reduceRight(@NotNull CharSequence $receiver, @NotNull Function2<? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        char accumulator = StringsKt__StringsKt.getLastIndex($receiver);
        if (accumulator < '\u0000') {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        int index = accumulator - 1;
        accumulator = $receiver.charAt(accumulator);
        while (index >= 0) {
            int index2 = index - 1;
            accumulator = ((Character) operation.invoke(Character.valueOf($receiver.charAt(index)), Character.valueOf(accumulator))).charValue();
            index = index2;
        }
        return accumulator;
    }

    public static final char reduceRightIndexed(@NotNull CharSequence $receiver, @NotNull Function3<? super Integer, ? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        char accumulator = StringsKt__StringsKt.getLastIndex($receiver);
        if (accumulator < '\u0000') {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        accumulator = $receiver.charAt(accumulator);
        for (int index = accumulator - 1; index >= 0; index--) {
            accumulator = ((Character) operation.invoke(Integer.valueOf(index), Character.valueOf($receiver.charAt(index)), Character.valueOf(accumulator))).charValue();
        }
        return accumulator;
    }

    public static final int sumBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Integer> selector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        int sum = 0;
        for (int i = 0; i < $receiver.length(); i++) {
            sum += ((Number) selector.invoke(Character.valueOf($receiver.charAt(i)))).intValue();
        }
        return sum;
    }

    public static final double sumByDouble(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Double> selector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        double sum = BrickValues.SET_COLOR_TO;
        for (int i = 0; i < $receiver.length(); i++) {
            sum += ((Number) selector.invoke(Character.valueOf($receiver.charAt(i)))).doubleValue();
        }
        return sum;
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static final List<String> chunked(@NotNull CharSequence $receiver, int size) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return windowed($receiver, size, size, true);
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static final <R> List<R> chunked(@NotNull CharSequence $receiver, int size, @NotNull Function1<? super CharSequence, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return windowed($receiver, size, size, true, transform);
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static final Sequence<String> chunkedSequence(@NotNull CharSequence $receiver, int size) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return chunkedSequence($receiver, size, StringsKt___StringsKt$chunkedSequence$1.INSTANCE);
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static final <R> Sequence<R> chunkedSequence(@NotNull CharSequence $receiver, int size, @NotNull Function1<? super CharSequence, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return windowedSequence($receiver, size, size, true, transform);
    }

    @NotNull
    public static final Pair<CharSequence, CharSequence> partition(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        for (int i = 0; i < $receiver.length(); i++) {
            char element = $receiver.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(element))).booleanValue()) {
                first.append(element);
            } else {
                second.append(element);
            }
        }
        return new Pair(first, second);
    }

    @NotNull
    public static final Pair<String, String> partition(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        int length = $receiver.length();
        for (int i = 0; i < length; i++) {
            char element = $receiver.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(element))).booleanValue()) {
                first.append(element);
            } else {
                second.append(element);
            }
        }
        return new Pair(first.toString(), second.toString());
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static /* synthetic */ List windowed$default(CharSequence charSequence, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != null) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return windowed(charSequence, i, i2, z);
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static final List<String> windowed(@NotNull CharSequence $receiver, int size, int step, boolean partialWindows) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return windowed($receiver, size, step, partialWindows, StringsKt___StringsKt$windowed$1.INSTANCE);
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static /* synthetic */ List windowed$default(CharSequence charSequence, int i, int i2, boolean z, Function1 function1, int i3, Object obj) {
        if ((i3 & 2) != null) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return windowed(charSequence, i, i2, z, function1);
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static final <R> List<R> windowed(@NotNull CharSequence $receiver, int size, int step, boolean partialWindows, @NotNull Function1<? super CharSequence, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        SlidingWindowKt.checkWindowSizeStep(size, step);
        int thisSize = $receiver.length();
        ArrayList result = new ArrayList(((thisSize + step) - 1) / step);
        int index = 0;
        while (index < thisSize) {
            int coercedEnd;
            int end = index + size;
            if (end > thisSize) {
                if (!partialWindows) {
                    break;
                }
                coercedEnd = thisSize;
            } else {
                coercedEnd = end;
            }
            result.add(transform.invoke($receiver.subSequence(index, coercedEnd)));
            index += step;
        }
        return result;
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static /* synthetic */ Sequence windowedSequence$default(CharSequence charSequence, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != null) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return windowedSequence(charSequence, i, i2, z);
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static final Sequence<String> windowedSequence(@NotNull CharSequence $receiver, int size, int step, boolean partialWindows) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return windowedSequence($receiver, size, step, partialWindows, StringsKt___StringsKt$windowedSequence$1.INSTANCE);
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static /* synthetic */ Sequence windowedSequence$default(CharSequence charSequence, int i, int i2, boolean z, Function1 function1, int i3, Object obj) {
        if ((i3 & 2) != null) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return windowedSequence(charSequence, i, i2, z, function1);
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static final <R> Sequence<R> windowedSequence(@NotNull CharSequence $receiver, int size, int step, boolean partialWindows, @NotNull Function1<? super CharSequence, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        SlidingWindowKt.checkWindowSizeStep(size, step);
        return SequencesKt___SequencesKt.map(CollectionsKt___CollectionsKt.asSequence(RangesKt___RangesKt.step((IntProgression) partialWindows ? StringsKt__StringsKt.getIndices($receiver) : RangesKt___RangesKt.until(0, ($receiver.length() - size) + 1), step)), new StringsKt___StringsKt$windowedSequence$2($receiver, transform, size));
    }

    @NotNull
    public static final List<Pair<Character, Character>> zip(@NotNull CharSequence $receiver, @NotNull CharSequence other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, FacebookRequestErrorClassification.KEY_OTHER);
        CharSequence $receiver$iv = $receiver;
        int length$iv = Math.min($receiver$iv.length(), other.length());
        ArrayList list$iv = new ArrayList(length$iv);
        for (int i$iv = 0; i$iv < length$iv; i$iv++) {
            list$iv.add(TuplesKt.to(Character.valueOf($receiver$iv.charAt(i$iv)), Character.valueOf(other.charAt(i$iv))));
        }
        return list$iv;
    }

    @NotNull
    public static final <V> List<V> zip(@NotNull CharSequence $receiver, @NotNull CharSequence other, @NotNull Function2<? super Character, ? super Character, ? extends V> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, FacebookRequestErrorClassification.KEY_OTHER);
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int length = Math.min($receiver.length(), other.length());
        ArrayList list = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            list.add(transform.invoke(Character.valueOf($receiver.charAt(i)), Character.valueOf(other.charAt(i))));
        }
        return list;
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static final List<Pair<Character, Character>> zipWithNext(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharSequence $receiver$iv = $receiver;
        int size$iv = $receiver$iv.length() - 1;
        if (size$iv < 1) {
            return CollectionsKt__CollectionsKt.emptyList();
        }
        ArrayList result$iv = new ArrayList(size$iv);
        for (int index$iv = 0; index$iv < size$iv; index$iv++) {
            result$iv.add(TuplesKt.to(Character.valueOf($receiver$iv.charAt(index$iv)), Character.valueOf($receiver$iv.charAt(index$iv + 1))));
        }
        return result$iv;
    }

    @NotNull
    @SinceKotlin(version = "1.2")
    public static final <R> List<R> zipWithNext(@NotNull CharSequence $receiver, @NotNull Function2<? super Character, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int size = $receiver.length() - 1;
        if (size < 1) {
            return CollectionsKt__CollectionsKt.emptyList();
        }
        ArrayList result = new ArrayList(size);
        for (int index = 0; index < size; index++) {
            result.add(transform.invoke(Character.valueOf($receiver.charAt(index)), Character.valueOf($receiver.charAt(index + 1))));
        }
        return result;
    }

    @NotNull
    public static final Iterable<Character> asIterable(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver instanceof String) {
            if (($receiver.length() == 0 ? 1 : null) != null) {
                return CollectionsKt__CollectionsKt.emptyList();
            }
        }
        return new StringsKt___StringsKt$asIterable$$inlined$Iterable$1($receiver);
    }

    @NotNull
    public static final Sequence<Character> asSequence(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver instanceof String) {
            if (($receiver.length() == 0 ? 1 : null) != null) {
                return SequencesKt__SequencesKt.emptySequence();
            }
        }
        return new StringsKt___StringsKt$asSequence$$inlined$Sequence$1($receiver);
    }
}
