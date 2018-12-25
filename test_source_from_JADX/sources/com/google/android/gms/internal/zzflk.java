package com.google.android.gms.internal;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;

public final class zzflk {
    private final ByteBuffer zza;

    private zzflk(ByteBuffer byteBuffer) {
        this.zza = byteBuffer;
        this.zza.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzflk(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    public static int zza(int i) {
        return i >= 0 ? zzd(i) : 10;
    }

    public static int zza(long j) {
        return (j & -128) == 0 ? 1 : (j & -16384) == 0 ? 2 : (j & -2097152) == 0 ? 3 : (j & -268435456) == 0 ? 4 : (j & -34359738368L) == 0 ? 5 : (j & -4398046511104L) == 0 ? 6 : (j & -562949953421312L) == 0 ? 7 : (j & -72057594037927936L) == 0 ? 8 : (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    private static int zza(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < '') {
            i2++;
        }
        int i3 = length;
        while (i2 < length) {
            StringBuilder stringBuilder;
            char charAt = charSequence.charAt(i2);
            if (charAt < 'ࠀ') {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < 'ࠀ') {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if ('?' <= charAt2 && charAt2 <= '?') {
                            if (Character.codePointAt(charSequence, i2) < 65536) {
                                stringBuilder = new StringBuilder(39);
                                stringBuilder.append("Unpaired surrogate at index ");
                                stringBuilder.append(i2);
                                throw new IllegalArgumentException(stringBuilder.toString());
                            }
                            i2++;
                        }
                    }
                    i2++;
                }
                i3 += i;
                if (i3 < length) {
                    return i3;
                }
                long j = ((long) i3) + 4294967296L;
                stringBuilder = new StringBuilder(54);
                stringBuilder.append("UTF-8 length does not fit in int: ");
                stringBuilder.append(j);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        if (i3 < length) {
            return i3;
        }
        long j2 = ((long) i3) + 4294967296L;
        stringBuilder = new StringBuilder(54);
        stringBuilder.append("UTF-8 length does not fit in int: ");
        stringBuilder.append(j2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static int zza(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int length = charSequence.length();
        i2 += i;
        int i3 = 0;
        while (i3 < length) {
            int i4 = i3 + i;
            if (i4 >= i2) {
                break;
            }
            char charAt = charSequence.charAt(i3);
            if (charAt >= '') {
                break;
            }
            bArr[i4] = (byte) charAt;
            i3++;
        }
        if (i3 == length) {
            return i + length;
        }
        i += i3;
        while (i3 < length) {
            int i5;
            char charAt2 = charSequence.charAt(i3);
            if (charAt2 < '' && i < i2) {
                i5 = i + 1;
                bArr[i] = (byte) charAt2;
            } else if (charAt2 < 'ࠀ' && i <= i2 - 2) {
                i5 = i + 1;
                bArr[i] = (byte) ((charAt2 >>> 6) | 960);
                i = i5 + 1;
                bArr[i5] = (byte) ((charAt2 & 63) | 128);
                i3++;
            } else if ((charAt2 < '?' || '?' < charAt2) && i <= i2 - 3) {
                i5 = i + 1;
                bArr[i] = (byte) ((charAt2 >>> 12) | 480);
                i = i5 + 1;
                bArr[i5] = (byte) (((charAt2 >>> 6) & 63) | 128);
                i5 = i + 1;
                bArr[i] = (byte) ((charAt2 & 63) | 128);
            } else if (i <= i2 - 4) {
                i5 = i3 + 1;
                if (i5 != charSequence.length()) {
                    char charAt3 = charSequence.charAt(i5);
                    if (Character.isSurrogatePair(charAt2, charAt3)) {
                        i3 = Character.toCodePoint(charAt2, charAt3);
                        i4 = i + 1;
                        bArr[i] = (byte) ((i3 >>> 18) | SysexMessageWriter.COMMAND_START);
                        i = i4 + 1;
                        bArr[i4] = (byte) (((i3 >>> 12) & 63) | 128);
                        i4 = i + 1;
                        bArr[i] = (byte) (((i3 >>> 6) & 63) | 128);
                        i = i4 + 1;
                        bArr[i4] = (byte) ((i3 & 63) | 128);
                        i3 = i5;
                        i3++;
                    } else {
                        i3 = i5;
                    }
                }
                i3--;
                StringBuilder stringBuilder = new StringBuilder(39);
                stringBuilder.append("Unpaired surrogate at index ");
                stringBuilder.append(i3);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else {
                StringBuilder stringBuilder2 = new StringBuilder(37);
                stringBuilder2.append("Failed writing ");
                stringBuilder2.append(charAt2);
                stringBuilder2.append(" at index ");
                stringBuilder2.append(i);
                throw new ArrayIndexOutOfBoundsException(stringBuilder2.toString());
            }
            i = i5;
            i3++;
        }
        return i;
    }

    public static int zza(String str) {
        int zza = zza((CharSequence) str);
        return zzd(zza) + zza;
    }

    public static zzflk zza(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    public static zzflk zza(byte[] bArr, int i, int i2) {
        return new zzflk(bArr, 0, i2);
    }

    private static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(zza(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
            } catch (Throwable e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        } else {
            zzb(charSequence, byteBuffer);
        }
    }

    public static int zzb(int i) {
        return zzd(i << 3);
    }

    public static int zzb(int i, int i2) {
        return zzb(i) + zza(i2);
    }

    public static int zzb(int i, zzfls zzfls) {
        i = zzb(i);
        int zzf = zzfls.zzf();
        return i + (zzd(zzf) + zzf);
    }

    public static int zzb(int i, String str) {
        return zzb(i) + zza(str);
    }

    public static int zzb(int i, byte[] bArr) {
        return zzb(i) + zzb(bArr);
    }

    public static int zzb(byte[] bArr) {
        return zzd(bArr.length) + bArr.length;
    }

    private final void zzb(long j) throws IOException {
        while ((j & -128) != 0) {
            zzf((((int) j) & MetaEvent.SEQUENCER_SPECIFIC) | 128);
            j >>>= 7;
        }
        zzf((int) j);
    }

    private static void zzb(CharSequence charSequence, ByteBuffer byteBuffer) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            int charAt = charSequence.charAt(i);
            if (charAt >= 128) {
                int i2;
                if (charAt < 2048) {
                    i2 = (charAt >>> 6) | 960;
                } else {
                    if (charAt >= 55296) {
                        if (57343 >= charAt) {
                            i2 = i + 1;
                            if (i2 != charSequence.length()) {
                                char charAt2 = charSequence.charAt(i2);
                                if (Character.isSurrogatePair(charAt, charAt2)) {
                                    i = Character.toCodePoint(charAt, charAt2);
                                    byteBuffer.put((byte) ((i >>> 18) | SysexMessageWriter.COMMAND_START));
                                    byteBuffer.put((byte) (((i >>> 12) & 63) | 128));
                                    byteBuffer.put((byte) (((i >>> 6) & 63) | 128));
                                    byteBuffer.put((byte) ((i & 63) | 128));
                                    i = i2;
                                    i++;
                                } else {
                                    i = i2;
                                }
                            }
                            i--;
                            StringBuilder stringBuilder = new StringBuilder(39);
                            stringBuilder.append("Unpaired surrogate at index ");
                            stringBuilder.append(i);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                    }
                    byteBuffer.put((byte) ((charAt >>> 12) | 480));
                    i2 = ((charAt >>> 6) & 63) | 128;
                }
                byteBuffer.put((byte) i2);
                charAt = (charAt & 63) | 128;
            }
            byteBuffer.put((byte) charAt);
            i++;
        }
    }

    private final void zzc(long j) throws IOException {
        if (this.zza.remaining() < 8) {
            throw new zzfll(this.zza.position(), this.zza.limit());
        }
        this.zza.putLong(j);
    }

    public static int zzd(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (i & -268435456) == 0 ? 4 : 5;
    }

    private static long zzd(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public static int zze(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static int zze(int i, long j) {
        return zzb(i) + zza(j);
    }

    public static int zzf(int i, long j) {
        return zzb(i) + zza(zzd(j));
    }

    private final void zzf(int i) throws IOException {
        byte b = (byte) i;
        if (this.zza.hasRemaining()) {
            this.zza.put(b);
            return;
        }
        throw new zzfll(this.zza.position(), this.zza.limit());
    }

    public final void zza() {
        if (this.zza.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", new Object[]{Integer.valueOf(this.zza.remaining())}));
        }
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, 1);
        zzc(Double.doubleToLongBits(d));
    }

    public final void zza(int i, float f) throws IOException {
        zzc(i, 5);
        i = Float.floatToIntBits(f);
        if (this.zza.remaining() < 4) {
            throw new zzfll(this.zza.position(), this.zza.limit());
        }
        this.zza.putInt(i);
    }

    public final void zza(int i, int i2) throws IOException {
        zzc(i, 0);
        if (i2 >= 0) {
            zzc(i2);
        } else {
            zzb((long) i2);
        }
    }

    public final void zza(int i, long j) throws IOException {
        zzc(i, 0);
        zzb(j);
    }

    public final void zza(int i, zzfls zzfls) throws IOException {
        zzc(i, 2);
        zza(zzfls);
    }

    public final void zza(int i, String str) throws IOException {
        zzc(i, 2);
        try {
            i = zzd(str.length());
            if (i == zzd(str.length() * 3)) {
                int position = this.zza.position();
                if (this.zza.remaining() < i) {
                    throw new zzfll(position + i, this.zza.limit());
                }
                this.zza.position(position + i);
                zza((CharSequence) str, this.zza);
                int position2 = this.zza.position();
                this.zza.position(position);
                zzc((position2 - position) - i);
                this.zza.position(position2);
                return;
            }
            zzc(zza((CharSequence) str));
            zza((CharSequence) str, this.zza);
        } catch (Throwable e) {
            zzfll zzfll = new zzfll(this.zza.position(), this.zza.limit());
            zzfll.initCause(e);
            throw zzfll;
        }
    }

    public final void zza(int i, boolean z) throws IOException {
        zzc(i, 0);
        byte b = (byte) z;
        if (this.zza.hasRemaining()) {
            this.zza.put(b);
            return;
        }
        throw new zzfll(this.zza.position(), this.zza.limit());
    }

    public final void zza(int i, byte[] bArr) throws IOException {
        zzc(i, 2);
        zzc(bArr.length);
        zzc(bArr);
    }

    public final void zza(zzfls zzfls) throws IOException {
        zzc(zzfls.zze());
        zzfls.zza(this);
    }

    public final void zzb(int i, long j) throws IOException {
        zzc(i, 0);
        zzb(j);
    }

    public final void zzc(int i) throws IOException {
        while ((i & -128) != 0) {
            zzf((i & MetaEvent.SEQUENCER_SPECIFIC) | 128);
            i >>>= 7;
        }
        zzf(i);
    }

    public final void zzc(int i, int i2) throws IOException {
        zzc((i << 3) | i2);
    }

    public final void zzc(int i, long j) throws IOException {
        zzc(i, 1);
        zzc(j);
    }

    public final void zzc(byte[] bArr) throws IOException {
        int length = bArr.length;
        if (this.zza.remaining() >= length) {
            this.zza.put(bArr, 0, length);
            return;
        }
        throw new zzfll(this.zza.position(), this.zza.limit());
    }

    public final void zzd(int i, long j) throws IOException {
        zzc(i, 0);
        zzb(zzd(j));
    }
}
