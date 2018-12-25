package kotlin.properties;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J)\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00028\u00002\u0006\u0010\b\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"kotlin/properties/Delegates$observable$1", "Lkotlin/properties/ObservableProperty;", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;Ljava/lang/Object;)V", "afterChange", "", "property", "Lkotlin/reflect/KProperty;", "oldValue", "newValue", "(Lkotlin/reflect/KProperty;Ljava/lang/Object;Ljava/lang/Object;)V", "kotlin-stdlib"}, k = 1, mv = {1, 1, 10})
/* compiled from: Delegates.kt */
public final class Delegates$observable$1 extends ObservableProperty<T> {
    final /* synthetic */ Object $initialValue;
    final /* synthetic */ Function3 $onChange;

    public Delegates$observable$1(Function3 $captured_local_variable$0, Object $captured_local_variable$1, Object $super_call_param$2) {
        this.$onChange = $captured_local_variable$0;
        this.$initialValue = $captured_local_variable$1;
        super($super_call_param$2);
    }

    protected void afterChange(@NotNull KProperty<?> property, T oldValue, T newValue) {
        Intrinsics.checkParameterIsNotNull(property, "property");
        this.$onChange.invoke(property, oldValue, newValue);
    }
}
