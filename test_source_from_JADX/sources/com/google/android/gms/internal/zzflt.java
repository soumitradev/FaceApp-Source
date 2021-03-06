package com.google.android.gms.internal;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.text.Typography;

public final class zzflt {
    public static <T extends zzfls> String zza(T t) {
        String str;
        String valueOf;
        if (t == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            zza(null, t, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        } catch (IllegalAccessException e) {
            str = "Error printing proto: ";
            valueOf = String.valueOf(e.getMessage());
            return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
        } catch (InvocationTargetException e2) {
            str = "Error printing proto: ";
            valueOf = String.valueOf(e2.getMessage());
            return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
        }
    }

    private static String zza(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (i != 0) {
                if (Character.isUpperCase(charAt)) {
                    stringBuffer.append('_');
                }
                stringBuffer.append(charAt);
            }
            charAt = Character.toLowerCase(charAt);
            stringBuffer.append(charAt);
        }
        return stringBuffer.toString();
    }

    private static void zza(String str, Object obj, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, InvocationTargetException {
        if (obj != null) {
            if (obj instanceof zzfls) {
                String name;
                int length = stringBuffer.length();
                if (str != null) {
                    stringBuffer2.append(stringBuffer);
                    stringBuffer2.append(zza(str));
                    stringBuffer2.append(" <\n");
                    stringBuffer.append("  ");
                }
                Class cls = obj.getClass();
                for (Field field : cls.getFields()) {
                    int modifiers = field.getModifiers();
                    name = field.getName();
                    if (!("cachedSize".equals(name) || (modifiers & 1) != 1 || (modifiers & 8) == 8 || name.startsWith(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR) || name.endsWith(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR))) {
                        Class type = field.getType();
                        Object obj2 = field.get(obj);
                        if (!type.isArray() || type.getComponentType() == Byte.TYPE) {
                            zza(name, obj2, stringBuffer, stringBuffer2);
                        } else {
                            modifiers = obj2 == null ? 0 : Array.getLength(obj2);
                            for (int i = 0; i < modifiers; i++) {
                                zza(name, Array.get(obj2, i), stringBuffer, stringBuffer2);
                            }
                        }
                    }
                }
                for (Method name2 : cls.getMethods()) {
                    String name3 = name2.getName();
                    if (name3.startsWith("set")) {
                        name3 = name3.substring(3);
                        try {
                            String str2 = "has";
                            name = String.valueOf(name3);
                            if (((Boolean) cls.getMethod(name.length() != 0 ? str2.concat(name) : new String(str2), new Class[0]).invoke(obj, new Object[0])).booleanValue()) {
                                str2 = "get";
                                name = String.valueOf(name3);
                                zza(name3, cls.getMethod(name.length() != 0 ? str2.concat(name) : new String(str2), new Class[0]).invoke(obj, new Object[0]), stringBuffer, stringBuffer2);
                            }
                        } catch (NoSuchMethodException e) {
                        }
                    }
                }
                if (str != null) {
                    stringBuffer.setLength(length);
                    stringBuffer2.append(stringBuffer);
                    stringBuffer2.append(">\n");
                }
                return;
            }
            str = zza(str);
            stringBuffer2.append(stringBuffer);
            stringBuffer2.append(str);
            stringBuffer2.append(": ");
            if (obj instanceof String) {
                String str3 = (String) obj;
                if (!str3.startsWith("http") && str3.length() > 200) {
                    str3 = String.valueOf(str3.substring(0, 200)).concat("[...]");
                }
                str = zzb(str3);
                stringBuffer2.append("\"");
                stringBuffer2.append(str);
                stringBuffer2.append("\"");
            } else if (obj instanceof byte[]) {
                zza((byte[]) obj, stringBuffer2);
            } else {
                stringBuffer2.append(obj);
            }
            stringBuffer2.append("\n");
        }
    }

    private static void zza(byte[] bArr, StringBuffer stringBuffer) {
        if (bArr == null) {
            stringBuffer.append("\"\"");
            return;
        }
        stringBuffer.append(Typography.quote);
        for (byte b : bArr) {
            int i = b & 255;
            if (i != 92) {
                if (i != 34) {
                    if (i < 32 || i >= MetaEvent.SEQUENCER_SPECIFIC) {
                        stringBuffer.append(String.format("\\%03o", new Object[]{Integer.valueOf(i)}));
                    } else {
                        stringBuffer.append((char) i);
                    }
                }
            }
            stringBuffer.append('\\');
            stringBuffer.append((char) i);
        }
        stringBuffer.append(Typography.quote);
    }

    private static String zzb(String str) {
        int length = str.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt < ' ' || charAt > '~' || charAt == Typography.quote || charAt == '\'') {
                stringBuilder.append(String.format("\\u%04x", new Object[]{Integer.valueOf(charAt)}));
            } else {
                stringBuilder.append(charAt);
            }
        }
        return stringBuilder.toString();
    }
}
