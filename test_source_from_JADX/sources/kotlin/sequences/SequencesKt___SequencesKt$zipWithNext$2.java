package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.SequenceBuilder;
import kotlin.coroutines.experimental.jvm.internal.CoroutineImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "T", "R", "Lkotlin/coroutines/experimental/SequenceBuilder;", "invoke", "(Lkotlin/coroutines/experimental/SequenceBuilder;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 10})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$zipWithNext$2 extends CoroutineImpl implements Function2<SequenceBuilder<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2 $transform;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    private SequenceBuilder p$;
    final /* synthetic */ Sequence receiver$0;

    SequencesKt___SequencesKt$zipWithNext$2(Sequence sequence, Function2 function2, Continuation continuation) {
        this.receiver$0 = sequence;
        this.$transform = function2;
        super(2, continuation);
    }

    @NotNull
    public final Continuation<Unit> create(@NotNull SequenceBuilder<? super R> sequenceBuilder, @NotNull Continuation<? super Unit> continuation) {
        Intrinsics.checkParameterIsNotNull(sequenceBuilder, "$receiver");
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        Continuation sequencesKt___SequencesKt$zipWithNext$2 = new SequencesKt___SequencesKt$zipWithNext$2(this.receiver$0, this.$transform, continuation);
        sequencesKt___SequencesKt$zipWithNext$2.p$ = sequenceBuilder;
        return sequencesKt___SequencesKt$zipWithNext$2;
    }

    @Nullable
    public final Object invoke(@NotNull SequenceBuilder<? super R> sequenceBuilder, @NotNull Continuation<? super Unit> continuation) {
        Intrinsics.checkParameterIsNotNull(sequenceBuilder, "$receiver");
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        return ((SequencesKt___SequencesKt$zipWithNext$2) create((SequenceBuilder) sequenceBuilder, (Continuation) continuation)).doResume(Unit.INSTANCE, null);
    }

    @org.jetbrains.annotations.Nullable
    public final java.lang.Object doResume(@org.jetbrains.annotations.Nullable java.lang.Object r6, @org.jetbrains.annotations.Nullable java.lang.Throwable r7) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r5 = this;
        r6 = kotlin.coroutines.experimental.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED();
        r0 = r5.label;
        switch(r0) {
            case 0: goto L_0x0024;
            case 1: goto L_0x0011;
            default: goto L_0x0009;
        };
    L_0x0009:
        r6 = new java.lang.IllegalStateException;
        r7 = "call to 'resume' before 'invoke' with coroutine";
        r6.<init>(r7);
        throw r6;
    L_0x0011:
        r0 = r5.L$3;
        r1 = r5.L$2;
        r1 = r5.L$1;
        r1 = (java.util.Iterator) r1;
        r2 = r5.L$0;
        r2 = (kotlin.coroutines.experimental.SequenceBuilder) r2;
        if (r7 == 0) goto L_0x0020;
    L_0x001f:
        throw r7;
    L_0x0020:
        r7 = r0;
        r0 = r6;
        r6 = r5;
        goto L_0x0068;
    L_0x0024:
        if (r7 == 0) goto L_0x0027;
    L_0x0026:
        throw r7;
    L_0x0027:
        r7 = r5.p$;
        r0 = r5.receiver$0;
        r0 = r0.iterator();
        r1 = r0.hasNext();
        if (r1 != 0) goto L_0x0038;
    L_0x0035:
        r6 = kotlin.Unit.INSTANCE;
        return r6;
    L_0x0038:
        r1 = r0.next();
        r2 = r7;
        r7 = r1;
        r1 = r0;
        r0 = r6;
        r6 = r5;
    L_0x0041:
        r3 = r1.hasNext();
        if (r3 == 0) goto L_0x0069;
    L_0x0047:
        r3 = r1.next();
        r4 = r6.$transform;
        r4 = r4.invoke(r7, r3);
        r6.L$0 = r2;
        r6.L$1 = r1;
        r6.L$2 = r7;
        r6.L$3 = r3;
        r7 = 1;
        r6.label = r7;
        r7 = r2.yield(r4, r6);
        r4 = 2;
        kotlin.jvm.internal.InlineMarker.mark(r4);
        if (r7 != r0) goto L_0x0067;
    L_0x0066:
        return r0;
    L_0x0067:
        r7 = r3;
    L_0x0068:
        goto L_0x0041;
    L_0x0069:
        r6 = kotlin.Unit.INSTANCE;
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$zipWithNext$2.doResume(java.lang.Object, java.lang.Throwable):java.lang.Object");
    }
}
