package kotlin.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0002\b\f\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0006H\u0002¢\u0006\u0002\u0010\u0019J\u0006\u0010\u001a\u001a\u00020\u001bJ\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u001dH\u0002J\u000e\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0006J\u0015\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tH\u0014¢\u0006\u0002\u0010!J'\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00010\t\"\u0004\b\u0001\u0010\u00012\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00010\tH\u0015¢\u0006\u0002\u0010#J9\u0010$\u001a\u00020\u0014\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\t2\u0006\u0010\u0015\u001a\u0002H\u00012\b\b\u0002\u0010%\u001a\u00020\u00062\b\b\u0002\u0010&\u001a\u00020\u0006H\u0002¢\u0006\u0002\u0010'J\u0015\u0010(\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\bR\u0018\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR$\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006@RX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\r\"\u0004\b\u0011\u0010\u0007R\u000e\u0010\u0012\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lkotlin/collections/RingBuffer;", "T", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "capacity", "", "(I)V", "buffer", "", "", "[Ljava/lang/Object;", "getCapacity", "()I", "<set-?>", "size", "getSize", "setSize", "startIndex", "add", "", "element", "(Ljava/lang/Object;)V", "get", "index", "(I)Ljava/lang/Object;", "isFull", "", "iterator", "", "removeFirst", "n", "toArray", "()[Ljava/lang/Object;", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "fill", "fromIndex", "toIndex", "([Ljava/lang/Object;Ljava/lang/Object;II)V", "forward", "kotlin-stdlib"}, k = 1, mv = {1, 1, 10})
/* compiled from: SlidingWindow.kt */
final class RingBuffer<T> extends AbstractList<T> implements RandomAccess {
    private final Object[] buffer;
    private final int capacity;
    private int size;
    private int startIndex;

    public RingBuffer(int capacity) {
        this.capacity = capacity;
        if ((this.capacity >= 0 ? 1 : null) == null) {
            int $i$a$1$require = 0;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ring buffer capacity should not be negative but it is ");
            stringBuilder.append(this.capacity);
            throw new IllegalArgumentException(stringBuilder.toString().toString());
        }
        this.buffer = new Object[this.capacity];
    }

    public final int getCapacity() {
        return this.capacity;
    }

    private void setSize(int <set-?>) {
        this.size = <set-?>;
    }

    public int getSize() {
        return this.size;
    }

    public T get(int index) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(index, size());
        return this.buffer[(this.startIndex + index) % getCapacity()];
    }

    public final boolean isFull() {
        return size() == this.capacity;
    }

    @NotNull
    public Iterator<T> iterator() {
        return new RingBuffer$iterator$1(this);
    }

    @NotNull
    public <T> T[] toArray(@NotNull T[] array) {
        Object[] result;
        Intrinsics.checkParameterIsNotNull(array, "array");
        if (array.length < size()) {
            result = Arrays.copyOf(array, size());
            Intrinsics.checkExpressionValueIsNotNull(result, "java.util.Arrays.copyOf(this, newSize)");
        } else {
            result = array;
        }
        int size = size();
        int widx = 0;
        int idx = this.startIndex;
        while (widx < size && idx < this.capacity) {
            result[widx] = this.buffer[idx];
            widx++;
            idx++;
        }
        idx = 0;
        while (widx < size) {
            result[widx] = this.buffer[idx];
            widx++;
            idx++;
        }
        if (result.length > size()) {
            result[size()] = null;
        }
        if (result != null) {
            return result;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    @NotNull
    public Object[] toArray() {
        return toArray(new Object[size()]);
    }

    public final void add(T element) {
        if (isFull()) {
            throw new IllegalStateException("ring buffer is full");
        }
        this.buffer[(this.startIndex + size()) % getCapacity()] = element;
        setSize(size() + 1);
    }

    public final void removeFirst(int n) {
        Object obj = 1;
        if ((n >= 0 ? 1 : null) == null) {
            int $i$a$1$require = 0;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("n shouldn't be negative but it is ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString().toString());
        }
        if (n > size()) {
            obj = null;
        }
        if (obj == null) {
            $i$a$1$require = 0;
            stringBuilder = new StringBuilder();
            stringBuilder.append("n shouldn't be greater than the buffer size: n = ");
            stringBuilder.append(n);
            stringBuilder.append(", size = ");
            stringBuilder.append(size());
            throw new IllegalArgumentException(stringBuilder.toString().toString());
        } else if (n > 0) {
            $i$a$1$require = this.startIndex;
            int $i$f$forward = 0;
            int end = ($i$a$1$require + n) % getCapacity();
            if ($i$a$1$require > end) {
                fill(this.buffer, null, $i$a$1$require, this.capacity);
                fill(this.buffer, null, 0, end);
            } else {
                fill(this.buffer, null, $i$a$1$require, end);
            }
            this.startIndex = end;
            setSize(size() - n);
        }
    }

    private final int forward(int $receiver, int n) {
        return ($receiver + n) % getCapacity();
    }

    static /* synthetic */ void fill$default(RingBuffer ringBuffer, Object[] objArr, Object obj, int i, int i2, int i3, Object obj2) {
        if ((i3 & 2) != null) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        ringBuffer.fill(objArr, obj, i, i2);
    }

    private final <T> void fill(@NotNull T[] $receiver, T element, int fromIndex, int toIndex) {
        for (int idx = fromIndex; idx < toIndex; idx++) {
            $receiver[idx] = element;
        }
    }
}