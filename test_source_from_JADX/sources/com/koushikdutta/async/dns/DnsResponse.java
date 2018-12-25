package com.koushikdutta.async.dns;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.http.Multimap;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;

public class DnsResponse {
    public ArrayList<InetAddress> addresses = new ArrayList();
    public ArrayList<String> names = new ArrayList();
    public InetSocketAddress source;
    public Multimap txt = new Multimap();

    private static String parseName(ByteBufferList bb, ByteBuffer backReference) {
        int i;
        int len;
        bb.order(ByteOrder.BIG_ENDIAN);
        String ret = "";
        while (true) {
            i = bb.get() & 255;
            len = i;
            if (i == 0) {
                return ret;
            }
            if ((len & ReportAnalogPinMessageWriter.COMMAND) == ReportAnalogPinMessageWriter.COMMAND) {
                break;
            }
            StringBuilder stringBuilder;
            byte[] bytes = new byte[len];
            bb.get(bytes);
            if (ret.length() > 0) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(ret);
                stringBuilder.append(".");
                ret = stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(ret);
            stringBuilder.append(new String(bytes));
            ret = stringBuilder.toString();
        }
        i = ((len & 63) << 8) | (bb.get() & 255);
        if (ret.length() > 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(ret);
            stringBuilder.append(".");
            ret = stringBuilder.toString();
        }
        ByteBufferList sub = new ByteBufferList();
        ByteBuffer duplicate = backReference.duplicate();
        duplicate.get(new byte[i]);
        sub.add(duplicate);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(ret);
        stringBuilder2.append(parseName(sub, backReference));
        return stringBuilder2.toString();
    }

    public static DnsResponse parse(ByteBufferList bb) {
        int questions;
        ByteBufferList byteBufferList = bb;
        ByteBuffer b = bb.getAll();
        byteBufferList.add(b.duplicate());
        byteBufferList.order(ByteOrder.BIG_ENDIAN);
        bb.getShort();
        bb.getShort();
        int questions2 = bb.getShort();
        int answers = bb.getShort();
        int authorities = bb.getShort();
        int additionals = bb.getShort();
        for (int i = 0; i < questions2; i++) {
            parseName(byteBufferList, b);
            bb.getShort();
            bb.getShort();
        }
        DnsResponse response = new DnsResponse();
        int i2 = 0;
        while (i2 < answers) {
            String name = parseName(byteBufferList, b);
            int type = bb.getShort();
            int clazz = bb.getShort();
            int ttl = bb.getInt();
            int length = bb.getShort();
            if (type == 1) {
                try {
                    byte[] data = new byte[length];
                    byteBufferList.get(data);
                    questions = questions2;
                    try {
                        response.addresses.add(InetAddress.getByAddress(data));
                    } catch (Exception e) {
                        i2++;
                        questions2 = questions;
                    }
                } catch (Exception e2) {
                    questions = questions2;
                    i2++;
                    questions2 = questions;
                }
            } else {
                questions = questions2;
                if (type == 12) {
                    response.names.add(parseName(byteBufferList, b));
                } else if (type == 16) {
                    questions2 = new ByteBufferList();
                    byteBufferList.get(questions2, length);
                    response.parseTxt(questions2);
                } else {
                    byteBufferList.get(new byte[length]);
                }
            }
            i2++;
            questions2 = questions;
        }
        for (questions2 = 0; questions2 < authorities; questions2++) {
            String name2 = parseName(byteBufferList, b);
            i2 = bb.getShort();
            int clazz2 = bb.getShort();
            type = bb.getInt();
            try {
                byteBufferList.get(new byte[bb.getShort()]);
            } catch (Exception e3) {
            }
        }
        int i3 = 0;
        while (true) {
            questions2 = i3;
            if (questions2 >= additionals) {
                return response;
            }
            name2 = parseName(byteBufferList, b);
            i2 = bb.getShort();
            short s = bb.getShort();
            type = bb.getInt();
            clazz = bb.getShort();
            if (i2 == 16) {
                try {
                    ByteBufferList txt = new ByteBufferList();
                    byteBufferList.get(txt, clazz);
                    response.parseTxt(txt);
                } catch (Exception e4) {
                }
            } else {
                byteBufferList.get(new byte[clazz]);
            }
            i3 = questions2 + 1;
        }
    }

    void parseTxt(ByteBufferList bb) {
        while (bb.hasRemaining()) {
            byte[] bytes = new byte[(bb.get() & 255)];
            bb.get(bytes);
            String[] pair = new String(bytes).split("=");
            this.txt.add(pair[0], pair[1]);
        }
    }

    public String toString() {
        String ret = "addresses:\n";
        Iterator it = this.addresses.iterator();
        while (it.hasNext()) {
            InetAddress address = (InetAddress) it.next();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ret);
            stringBuilder.append(address.toString());
            stringBuilder.append("\n");
            ret = stringBuilder.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(ret);
        stringBuilder2.append("names:\n");
        ret = stringBuilder2.toString();
        it = this.names.iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
            stringBuilder = new StringBuilder();
            stringBuilder.append(ret);
            stringBuilder.append(name);
            stringBuilder.append("\n");
            ret = stringBuilder.toString();
        }
        return ret;
    }
}
