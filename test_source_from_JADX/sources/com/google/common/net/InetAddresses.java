package com.google.common.net;

import android.support.v4.internal.view.SupportMenu;
import com.facebook.appevents.AppEventsConstants;
import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Locale;
import javax.annotation.Nullable;
import org.billthefarmer.mididriver.GeneralMidiConstants;

@Beta
public final class InetAddresses {
    private static final Inet4Address ANY4 = ((Inet4Address) forString("0.0.0.0"));
    private static final int IPV4_PART_COUNT = 4;
    private static final int IPV6_PART_COUNT = 8;
    private static final Inet4Address LOOPBACK4 = ((Inet4Address) forString("127.0.0.1"));

    @Beta
    public static final class TeredoInfo {
        private final Inet4Address client;
        private final int flags;
        private final int port;
        private final Inet4Address server;

        public TeredoInfo(@Nullable Inet4Address server, @Nullable Inet4Address client, int port, int flags) {
            boolean z = port >= 0 && port <= SupportMenu.USER_MASK;
            Preconditions.checkArgument(z, "port '%s' is out of range (0 <= port <= 0xffff)", Integer.valueOf(port));
            boolean z2 = flags >= 0 && flags <= SupportMenu.USER_MASK;
            Preconditions.checkArgument(z2, "flags '%s' is out of range (0 <= flags <= 0xffff)", Integer.valueOf(flags));
            this.server = (Inet4Address) MoreObjects.firstNonNull(server, InetAddresses.ANY4);
            this.client = (Inet4Address) MoreObjects.firstNonNull(client, InetAddresses.ANY4);
            this.port = port;
            this.flags = flags;
        }

        public Inet4Address getServer() {
            return this.server;
        }

        public Inet4Address getClient() {
            return this.client;
        }

        public int getPort() {
            return this.port;
        }

        public int getFlags() {
            return this.flags;
        }
    }

    private InetAddresses() {
    }

    private static Inet4Address getInet4Address(byte[] bytes) {
        Preconditions.checkArgument(bytes.length == 4, "Byte array has invalid length for an IPv4 address: %s != 4.", Integer.valueOf(bytes.length));
        return (Inet4Address) bytesToInetAddress(bytes);
    }

    public static InetAddress forString(String ipString) {
        byte[] addr = ipStringToBytes(ipString);
        if (addr != null) {
            return bytesToInetAddress(addr);
        }
        throw formatIllegalArgumentException("'%s' is not an IP string literal.", ipString);
    }

    public static boolean isInetAddress(String ipString) {
        return ipStringToBytes(ipString) != null;
    }

    private static byte[] ipStringToBytes(String ipString) {
        boolean hasColon = false;
        boolean hasDot = false;
        for (int i = 0; i < ipString.length(); i++) {
            char c = ipString.charAt(i);
            if (c == '.') {
                hasDot = true;
            } else if (c == ':') {
                if (hasDot) {
                    return null;
                }
                hasColon = true;
            } else if (Character.digit(c, 16) == -1) {
                return null;
            }
        }
        if (hasColon) {
            if (hasDot) {
                ipString = convertDottedQuadToHex(ipString);
                if (ipString == null) {
                    return null;
                }
            }
            return textToNumericFormatV6(ipString);
        } else if (hasDot) {
            return textToNumericFormatV4(ipString);
        } else {
            return null;
        }
    }

    private static byte[] textToNumericFormatV4(String ipString) {
        String[] address = ipString.split("\\.", 5);
        if (address.length != 4) {
            return null;
        }
        byte[] bytes = new byte[4];
        int i = 0;
        while (i < bytes.length) {
            try {
                bytes[i] = parseOctet(address[i]);
                i++;
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return bytes;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] textToNumericFormatV6(java.lang.String r10) {
        /*
        r0 = ":";
        r1 = 10;
        r0 = r10.split(r0, r1);
        r1 = r0.length;
        r2 = 0;
        r3 = 3;
        if (r1 < r3) goto L_0x0095;
    L_0x000d:
        r1 = r0.length;
        r3 = 9;
        if (r1 <= r3) goto L_0x0014;
    L_0x0012:
        goto L_0x0095;
    L_0x0014:
        r1 = -1;
        r3 = 1;
        r4 = r1;
        r1 = 1;
    L_0x0018:
        r5 = r0.length;
        r5 = r5 - r3;
        if (r1 >= r5) goto L_0x002b;
    L_0x001c:
        r5 = r0[r1];
        r5 = r5.length();
        if (r5 != 0) goto L_0x0028;
    L_0x0024:
        if (r4 < 0) goto L_0x0027;
    L_0x0026:
        return r2;
    L_0x0027:
        r4 = r1;
    L_0x0028:
        r1 = r1 + 1;
        goto L_0x0018;
    L_0x002b:
        r1 = 0;
        if (r4 < 0) goto L_0x004e;
    L_0x002e:
        r5 = r4;
        r6 = r0.length;
        r6 = r6 - r4;
        r6 = r6 - r3;
        r7 = r0[r1];
        r7 = r7.length();
        if (r7 != 0) goto L_0x003f;
    L_0x003a:
        r5 = r5 + -1;
        if (r5 == 0) goto L_0x003f;
    L_0x003e:
        return r2;
    L_0x003f:
        r7 = r0.length;
        r7 = r7 - r3;
        r7 = r0[r7];
        r7 = r7.length();
        if (r7 != 0) goto L_0x0050;
    L_0x0049:
        r6 = r6 + -1;
        if (r6 == 0) goto L_0x0050;
    L_0x004d:
        return r2;
    L_0x004e:
        r5 = r0.length;
        r6 = 0;
    L_0x0050:
        r7 = r5 + r6;
        r7 = 8 - r7;
        if (r4 < 0) goto L_0x0059;
    L_0x0056:
        if (r7 < r3) goto L_0x005b;
    L_0x0058:
        goto L_0x005c;
    L_0x0059:
        if (r7 == 0) goto L_0x005c;
    L_0x005b:
        return r2;
    L_0x005c:
        r3 = 16;
        r3 = java.nio.ByteBuffer.allocate(r3);
        r8 = 0;
    L_0x0063:
        if (r8 >= r5) goto L_0x0073;
    L_0x0065:
        r9 = r0[r8];	 Catch:{ NumberFormatException -> 0x0071 }
        r9 = parseHextet(r9);	 Catch:{ NumberFormatException -> 0x0071 }
        r3.putShort(r9);	 Catch:{ NumberFormatException -> 0x0071 }
        r8 = r8 + 1;
        goto L_0x0063;
    L_0x0071:
        r1 = move-exception;
        goto L_0x008d;
    L_0x0073:
        r8 = 0;
    L_0x0074:
        if (r8 >= r7) goto L_0x007c;
    L_0x0076:
        r3.putShort(r1);	 Catch:{ NumberFormatException -> 0x0071 }
        r8 = r8 + 1;
        goto L_0x0074;
    L_0x007c:
        r1 = r6;
    L_0x007d:
        if (r1 <= 0) goto L_0x008f;
    L_0x007f:
        r8 = r0.length;	 Catch:{ NumberFormatException -> 0x0071 }
        r8 = r8 - r1;
        r8 = r0[r8];	 Catch:{ NumberFormatException -> 0x0071 }
        r8 = parseHextet(r8);	 Catch:{ NumberFormatException -> 0x0071 }
        r3.putShort(r8);	 Catch:{ NumberFormatException -> 0x0071 }
        r1 = r1 + -1;
        goto L_0x007d;
        return r2;
        r1 = r3.array();
        return r1;
    L_0x0095:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.net.InetAddresses.textToNumericFormatV6(java.lang.String):byte[]");
    }

    private static String convertDottedQuadToHex(String ipString) {
        int lastColon = ipString.lastIndexOf(58);
        String initialPart = ipString.substring(0, lastColon + 1);
        byte[] quad = textToNumericFormatV4(ipString.substring(lastColon + 1));
        if (quad == null) {
            return null;
        }
        String penultimate = Integer.toHexString(((quad[0] & 255) << 8) | (quad[1] & 255));
        String ultimate = Integer.toHexString(((quad[2] & 255) << 8) | (quad[3] & 255));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(initialPart);
        stringBuilder.append(penultimate);
        stringBuilder.append(":");
        stringBuilder.append(ultimate);
        return stringBuilder.toString();
    }

    private static byte parseOctet(String ipPart) {
        int octet = Integer.parseInt(ipPart);
        if (octet <= 255) {
            if (!ipPart.startsWith(AppEventsConstants.EVENT_PARAM_VALUE_NO) || ipPart.length() <= 1) {
                return (byte) octet;
            }
        }
        throw new NumberFormatException();
    }

    private static short parseHextet(String ipPart) {
        int hextet = Integer.parseInt(ipPart, 16);
        if (hextet <= SupportMenu.USER_MASK) {
            return (short) hextet;
        }
        throw new NumberFormatException();
    }

    private static InetAddress bytesToInetAddress(byte[] addr) {
        try {
            return InetAddress.getByAddress(addr);
        } catch (UnknownHostException e) {
            throw new AssertionError(e);
        }
    }

    public static String toAddrString(InetAddress ip) {
        Preconditions.checkNotNull(ip);
        if (ip instanceof Inet4Address) {
            return ip.getHostAddress();
        }
        Preconditions.checkArgument(ip instanceof Inet6Address);
        byte[] bytes = ip.getAddress();
        int[] hextets = new int[8];
        for (int i = 0; i < hextets.length; i++) {
            hextets[i] = Ints.fromBytes((byte) 0, (byte) 0, bytes[i * 2], bytes[(i * 2) + 1]);
        }
        compressLongestRunOfZeroes(hextets);
        return hextetsToIPv6String(hextets);
    }

    private static void compressLongestRunOfZeroes(int[] hextets) {
        int bestRunStart = -1;
        int bestRunLength = -1;
        int runStart = -1;
        int i = 0;
        while (i < hextets.length + 1) {
            if (i >= hextets.length || hextets[i] != 0) {
                if (runStart >= 0) {
                    int runLength = i - runStart;
                    if (runLength > bestRunLength) {
                        bestRunStart = runStart;
                        bestRunLength = runLength;
                    }
                    runStart = -1;
                }
            } else if (runStart < 0) {
                runStart = i;
            }
            i++;
        }
        if (bestRunLength >= 2) {
            Arrays.fill(hextets, bestRunStart, bestRunStart + bestRunLength, -1);
        }
    }

    private static String hextetsToIPv6String(int[] hextets) {
        StringBuilder buf = new StringBuilder(39);
        boolean lastWasNumber = false;
        for (int i = 0; i < hextets.length; i++) {
            boolean thisIsNumber = hextets[i] >= 0;
            if (thisIsNumber) {
                if (lastWasNumber) {
                    buf.append(':');
                }
                buf.append(Integer.toHexString(hextets[i]));
            } else if (i == 0 || lastWasNumber) {
                buf.append("::");
            }
            lastWasNumber = thisIsNumber;
        }
        return buf.toString();
    }

    public static String toUriString(InetAddress ip) {
        if (!(ip instanceof Inet6Address)) {
            return toAddrString(ip);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(toAddrString(ip));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static InetAddress forUriString(String hostAddr) {
        String ipString;
        int expectBytes;
        Preconditions.checkNotNull(hostAddr);
        if (hostAddr.startsWith("[") && hostAddr.endsWith("]")) {
            ipString = hostAddr.substring(1, hostAddr.length() - 1);
            expectBytes = 16;
        } else {
            ipString = hostAddr;
            expectBytes = 4;
        }
        byte[] addr = ipStringToBytes(ipString);
        if (addr != null) {
            if (addr.length == expectBytes) {
                return bytesToInetAddress(addr);
            }
        }
        throw formatIllegalArgumentException("Not a valid URI IP literal: '%s'", hostAddr);
    }

    public static boolean isUriInetAddress(String ipString) {
        try {
            forUriString(ipString);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isCompatIPv4Address(Inet6Address ip) {
        if (!ip.isIPv4CompatibleAddress()) {
            return false;
        }
        byte[] bytes = ip.getAddress();
        if (bytes[12] == (byte) 0 && bytes[13] == (byte) 0 && bytes[14] == (byte) 0 && (bytes[15] == (byte) 0 || bytes[15] == (byte) 1)) {
            return false;
        }
        return true;
    }

    public static Inet4Address getCompatIPv4Address(Inet6Address ip) {
        Preconditions.checkArgument(isCompatIPv4Address(ip), "Address '%s' is not IPv4-compatible.", toAddrString(ip));
        return getInet4Address(Arrays.copyOfRange(ip.getAddress(), 12, 16));
    }

    public static boolean is6to4Address(Inet6Address ip) {
        byte[] bytes = ip.getAddress();
        return bytes[0] == (byte) 32 && bytes[1] == (byte) 2;
    }

    public static Inet4Address get6to4IPv4Address(Inet6Address ip) {
        Preconditions.checkArgument(is6to4Address(ip), "Address '%s' is not a 6to4 address.", toAddrString(ip));
        return getInet4Address(Arrays.copyOfRange(ip.getAddress(), 2, 6));
    }

    public static boolean isTeredoAddress(Inet6Address ip) {
        byte[] bytes = ip.getAddress();
        return bytes[0] == (byte) 32 && bytes[1] == (byte) 1 && bytes[2] == (byte) 0 && bytes[3] == (byte) 0;
    }

    public static TeredoInfo getTeredoInfo(Inet6Address ip) {
        Object[] objArr = new Object[1];
        int i = 0;
        objArr[0] = toAddrString(ip);
        Preconditions.checkArgument(isTeredoAddress(ip), "Address '%s' is not a Teredo address.", objArr);
        byte[] bytes = ip.getAddress();
        Inet4Address server = getInet4Address(Arrays.copyOfRange(bytes, 4, 8));
        int flags = ByteStreams.newDataInput(bytes, 8).readShort() & SupportMenu.USER_MASK;
        int port = SupportMenu.USER_MASK & (ByteStreams.newDataInput(bytes, 10).readShort() ^ -1);
        byte[] clientBytes = Arrays.copyOfRange(bytes, 12, 16);
        while (i < clientBytes.length) {
            clientBytes[i] = (byte) (clientBytes[i] ^ -1);
            i++;
        }
        return new TeredoInfo(server, getInet4Address(clientBytes), port, flags);
    }

    public static boolean isIsatapAddress(Inet6Address ip) {
        boolean z = false;
        if (isTeredoAddress(ip)) {
            return false;
        }
        byte[] bytes = ip.getAddress();
        if ((bytes[8] | 3) != 3) {
            return false;
        }
        if (bytes[9] == (byte) 0 && bytes[10] == GeneralMidiConstants.PAD_6_HALO && bytes[11] == (byte) -2) {
            z = true;
        }
        return z;
    }

    public static Inet4Address getIsatapIPv4Address(Inet6Address ip) {
        Preconditions.checkArgument(isIsatapAddress(ip), "Address '%s' is not an ISATAP address.", toAddrString(ip));
        return getInet4Address(Arrays.copyOfRange(ip.getAddress(), 12, 16));
    }

    public static boolean hasEmbeddedIPv4ClientAddress(Inet6Address ip) {
        if (!(isCompatIPv4Address(ip) || is6to4Address(ip))) {
            if (!isTeredoAddress(ip)) {
                return false;
            }
        }
        return true;
    }

    public static Inet4Address getEmbeddedIPv4ClientAddress(Inet6Address ip) {
        if (isCompatIPv4Address(ip)) {
            return getCompatIPv4Address(ip);
        }
        if (is6to4Address(ip)) {
            return get6to4IPv4Address(ip);
        }
        if (isTeredoAddress(ip)) {
            return getTeredoInfo(ip).getClient();
        }
        throw formatIllegalArgumentException("'%s' has no embedded IPv4 address.", toAddrString(ip));
    }

    public static boolean isMappedIPv4Address(String ipString) {
        byte[] bytes = ipStringToBytes(ipString);
        if (bytes == null || bytes.length != 16) {
            return false;
        }
        int i = 0;
        while (true) {
            int i2 = 10;
            if (i >= 10) {
                break;
            } else if (bytes[i] != (byte) 0) {
                return false;
            } else {
                i++;
            }
        }
        while (true) {
            i = i2;
            if (i >= 12) {
                return true;
            }
            if (bytes[i] != (byte) -1) {
                return false;
            }
            i2 = i + 1;
        }
    }

    public static Inet4Address getCoercedIPv4Address(InetAddress ip) {
        if (ip instanceof Inet4Address) {
            return (Inet4Address) ip;
        }
        byte[] bytes = ip.getAddress();
        boolean leadingBytesOfZero = true;
        for (int i = 0; i < 15; i++) {
            if (bytes[i] != (byte) 0) {
                leadingBytesOfZero = false;
                break;
            }
        }
        if (leadingBytesOfZero && bytes[15] == (byte) 1) {
            return LOOPBACK4;
        }
        if (leadingBytesOfZero && bytes[15] == (byte) 0) {
            return ANY4;
        }
        long addressAsLong;
        Inet6Address ip6 = (Inet6Address) ip;
        if (hasEmbeddedIPv4ClientAddress(ip6)) {
            addressAsLong = (long) getEmbeddedIPv4ClientAddress(ip6).hashCode();
        } else {
            addressAsLong = ByteBuffer.wrap(ip6.getAddress(), 0, 8).getLong();
        }
        int coercedHash = Hashing.murmur3_32().hashLong(addressAsLong).asInt() | -536870912;
        if (coercedHash == -1) {
            coercedHash = -2;
        }
        return getInet4Address(Ints.toByteArray(coercedHash));
    }

    public static int coerceToInteger(InetAddress ip) {
        return ByteStreams.newDataInput(getCoercedIPv4Address(ip).getAddress()).readInt();
    }

    public static Inet4Address fromInteger(int address) {
        return getInet4Address(Ints.toByteArray(address));
    }

    public static InetAddress fromLittleEndianByteArray(byte[] addr) throws UnknownHostException {
        byte[] reversed = new byte[addr.length];
        for (int i = 0; i < addr.length; i++) {
            reversed[i] = addr[(addr.length - i) - 1];
        }
        return InetAddress.getByAddress(reversed);
    }

    public static InetAddress decrement(InetAddress address) {
        byte[] addr = address.getAddress();
        int i = addr.length - 1;
        while (i >= 0 && addr[i] == (byte) 0) {
            addr[i] = (byte) -1;
            i--;
        }
        Preconditions.checkArgument(i >= 0, "Decrementing %s would wrap.", address);
        addr[i] = (byte) (addr[i] - 1);
        return bytesToInetAddress(addr);
    }

    public static InetAddress increment(InetAddress address) {
        byte[] addr = address.getAddress();
        int i = addr.length - 1;
        while (i >= 0 && addr[i] == (byte) -1) {
            addr[i] = (byte) 0;
            i--;
        }
        Preconditions.checkArgument(i >= 0, "Incrementing %s would wrap.", address);
        addr[i] = (byte) (addr[i] + 1);
        return bytesToInetAddress(addr);
    }

    public static boolean isMaximum(InetAddress address) {
        byte[] addr = address.getAddress();
        for (byte b : addr) {
            if (b != (byte) -1) {
                return false;
            }
        }
        return true;
    }

    private static IllegalArgumentException formatIllegalArgumentException(String format, Object... args) {
        return new IllegalArgumentException(String.format(Locale.ROOT, format, args));
    }
}
