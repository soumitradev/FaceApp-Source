package com.google.gson.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.text.Typography;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.catrobat.catroid.common.Constants;

public class ISO8601Utils {
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);
    private static final String UTC_ID = "UTC";

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean millis) {
        return format(date, millis, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean millis, TimeZone tz) {
        Calendar calendar = new GregorianCalendar(tz, Locale.US);
        calendar.setTime(date);
        StringBuilder formatted = new StringBuilder(("yyyy-MM-ddThh:mm:ss".length() + (millis ? ".sss".length() : 0)) + (tz.getRawOffset() == 0 ? "Z" : "+hh:mm").length());
        padInt(formatted, calendar.get(1), "yyyy".length());
        char c = '-';
        formatted.append('-');
        padInt(formatted, calendar.get(2) + 1, "MM".length());
        formatted.append('-');
        padInt(formatted, calendar.get(5), "dd".length());
        formatted.append('T');
        padInt(formatted, calendar.get(11), "hh".length());
        formatted.append(':');
        padInt(formatted, calendar.get(12), "mm".length());
        formatted.append(':');
        padInt(formatted, calendar.get(13), "ss".length());
        if (millis) {
            formatted.append('.');
            padInt(formatted, calendar.get(14), "sss".length());
        }
        int offset = tz.getOffset(calendar.getTimeInMillis());
        if (offset != 0) {
            int hours = Math.abs((offset / 60000) / 60);
            int minutes = Math.abs((offset / 60000) % 60);
            if (offset >= 0) {
                c = '+';
            }
            formatted.append(c);
            padInt(formatted, hours, "hh".length());
            formatted.append(':');
            padInt(formatted, minutes, "mm".length());
        } else {
            formatted.append('Z');
        }
        return formatted.toString();
    }

    public static Date parse(String date, ParsePosition pos) throws ParseException {
        Exception fail;
        String str;
        StringBuilder stringBuilder;
        String msg;
        StringBuilder stringBuilder2;
        StringBuilder stringBuilder3;
        ParseException ex;
        String str2 = date;
        ParsePosition parsePosition = pos;
        Exception fail2 = null;
        Exception exception;
        String input;
        try {
            char c;
            int year = pos.getIndex();
            int month = year + 4;
            year = parseInt(str2, year, month);
            if (checkOffset(str2, month, '-')) {
                month++;
            }
            int day = month + 2;
            month = parseInt(str2, month, day);
            if (checkOffset(str2, day, '-')) {
                day++;
            }
            int offset = day + 2;
            day = parseInt(str2, day, offset);
            int hour = 0;
            int minutes = 0;
            int seconds = 0;
            int milliseconds = 0;
            boolean hasT = checkOffset(str2, offset, true);
            if (!hasT) {
                try {
                    if (date.length() <= offset) {
                        Calendar calendar = new GregorianCalendar(year, month - 1, day);
                        parsePosition.setIndex(offset);
                        return calendar.getTime();
                    }
                } catch (Exception e) {
                    fail = e;
                    exception = fail2;
                    if (str2 == null) {
                        str = null;
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(Typography.quote);
                        stringBuilder.append(str2);
                        stringBuilder.append(FormatHelper.QUOTE);
                        str = stringBuilder.toString();
                    }
                    input = str;
                    msg = fail.getMessage();
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(Constants.OPENING_BRACE);
                    stringBuilder2.append(fail.getClass().getName());
                    stringBuilder2.append(")");
                    msg = stringBuilder2.toString();
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Failed to parse date [");
                    stringBuilder3.append(input);
                    stringBuilder3.append("]: ");
                    stringBuilder3.append(msg);
                    ex = new ParseException(stringBuilder3.toString(), pos.getIndex());
                    ex.initCause(fail);
                    throw ex;
                } catch (Exception e2) {
                    fail = e2;
                    exception = fail2;
                    if (str2 == null) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(Typography.quote);
                        stringBuilder.append(str2);
                        stringBuilder.append(FormatHelper.QUOTE);
                        str = stringBuilder.toString();
                    } else {
                        str = null;
                    }
                    input = str;
                    msg = fail.getMessage();
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(Constants.OPENING_BRACE);
                    stringBuilder2.append(fail.getClass().getName());
                    stringBuilder2.append(")");
                    msg = stringBuilder2.toString();
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Failed to parse date [");
                    stringBuilder3.append(input);
                    stringBuilder3.append("]: ");
                    stringBuilder3.append(msg);
                    ex = new ParseException(stringBuilder3.toString(), pos.getIndex());
                    ex.initCause(fail);
                    throw ex;
                } catch (Exception e22) {
                    fail = e22;
                    exception = fail2;
                    if (str2 == null) {
                        str = null;
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(Typography.quote);
                        stringBuilder.append(str2);
                        stringBuilder.append(FormatHelper.QUOTE);
                        str = stringBuilder.toString();
                    }
                    input = str;
                    msg = fail.getMessage();
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(Constants.OPENING_BRACE);
                    stringBuilder2.append(fail.getClass().getName());
                    stringBuilder2.append(")");
                    msg = stringBuilder2.toString();
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Failed to parse date [");
                    stringBuilder3.append(input);
                    stringBuilder3.append("]: ");
                    stringBuilder3.append(msg);
                    ex = new ParseException(stringBuilder3.toString(), pos.getIndex());
                    ex.initCause(fail);
                    throw ex;
                }
            }
            if (hasT) {
                offset++;
                int offset2 = offset + 2;
                hour = parseInt(str2, offset, offset2);
                if (checkOffset(str2, offset2, ':')) {
                    offset2++;
                }
                int offset3 = offset2 + 2;
                minutes = parseInt(str2, offset2, offset3);
                if (checkOffset(str2, offset3, ':')) {
                    offset3++;
                }
                offset = offset3;
                if (date.length() > offset) {
                    c = str2.charAt(offset);
                    if (!(c == 'Z' || c == '+' || c == '-')) {
                        offset3 = offset + 2;
                        offset = parseInt(str2, offset, offset3);
                        if (offset > 59 && offset < 63) {
                            offset = 59;
                        }
                        seconds = offset;
                        if (checkOffset(str2, offset3, '.')) {
                            offset3++;
                            offset = indexOfNonDigit(str2, offset3 + 1);
                            int parseEndOffset = Math.min(offset, offset3 + 3);
                            int fraction = parseInt(str2, offset3, parseEndOffset);
                            switch (parseEndOffset - offset3) {
                                case 1:
                                    milliseconds = fraction * 100;
                                    break;
                                case 2:
                                    milliseconds = fraction * 10;
                                    break;
                                default:
                                    milliseconds = fraction;
                                    break;
                            }
                        }
                        offset = offset3;
                    }
                }
            }
            if (date.length() <= offset) {
                throw new IllegalArgumentException("No time zone indicator");
            }
            TimeZone timezone;
            c = str2.charAt(offset);
            char c2;
            if (c == 'Z') {
                timezone = TIMEZONE_UTC;
                offset++;
                exception = fail2;
                c2 = c;
            } else {
                int offset4;
                TimeZone timezone2;
                if (c == '+') {
                    timezone2 = null;
                    exception = fail2;
                } else if (c == '-') {
                    timezone2 = null;
                    exception = fail2;
                } else {
                    timezone2 = null;
                    StringBuilder stringBuilder4 = new StringBuilder();
                    try {
                        stringBuilder4.append("Invalid time zone indicator '");
                        stringBuilder4.append(c);
                        stringBuilder4.append(FormatHelper.QUOTE);
                        throw new IndexOutOfBoundsException(stringBuilder4.toString());
                    } catch (Exception e222) {
                        fail = e222;
                        if (str2 == null) {
                            str = null;
                        } else {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(Typography.quote);
                            stringBuilder.append(str2);
                            stringBuilder.append(FormatHelper.QUOTE);
                            str = stringBuilder.toString();
                        }
                        input = str;
                        msg = fail.getMessage();
                        if (msg == null || msg.isEmpty()) {
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append(Constants.OPENING_BRACE);
                            stringBuilder2.append(fail.getClass().getName());
                            stringBuilder2.append(")");
                            msg = stringBuilder2.toString();
                        }
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("Failed to parse date [");
                        stringBuilder3.append(input);
                        stringBuilder3.append("]: ");
                        stringBuilder3.append(msg);
                        ex = new ParseException(stringBuilder3.toString(), pos.getIndex());
                        ex.initCause(fail);
                        throw ex;
                    } catch (Exception e2222) {
                        fail = e2222;
                        if (str2 == null) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(Typography.quote);
                            stringBuilder.append(str2);
                            stringBuilder.append(FormatHelper.QUOTE);
                            str = stringBuilder.toString();
                        } else {
                            str = null;
                        }
                        input = str;
                        msg = fail.getMessage();
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(Constants.OPENING_BRACE);
                        stringBuilder2.append(fail.getClass().getName());
                        stringBuilder2.append(")");
                        msg = stringBuilder2.toString();
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("Failed to parse date [");
                        stringBuilder3.append(input);
                        stringBuilder3.append("]: ");
                        stringBuilder3.append(msg);
                        ex = new ParseException(stringBuilder3.toString(), pos.getIndex());
                        ex.initCause(fail);
                        throw ex;
                    } catch (Exception e22222) {
                        fail = e22222;
                        if (str2 == null) {
                            str = null;
                        } else {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(Typography.quote);
                            stringBuilder.append(str2);
                            stringBuilder.append(FormatHelper.QUOTE);
                            str = stringBuilder.toString();
                        }
                        input = str;
                        msg = fail.getMessage();
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(Constants.OPENING_BRACE);
                        stringBuilder2.append(fail.getClass().getName());
                        stringBuilder2.append(")");
                        msg = stringBuilder2.toString();
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("Failed to parse date [");
                        stringBuilder3.append(input);
                        stringBuilder3.append("]: ");
                        stringBuilder3.append(msg);
                        ex = new ParseException(stringBuilder3.toString(), pos.getIndex());
                        ex.initCause(fail);
                        throw ex;
                    }
                }
                String timezoneOffset = str2.substring(offset);
                if (timezoneOffset.length() >= 5) {
                    input = timezoneOffset;
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(timezoneOffset);
                    stringBuilder.append(TarConstants.VERSION_POSIX);
                    input = stringBuilder.toString();
                }
                timezoneOffset = input;
                offset += timezoneOffset.length();
                String str3;
                if ("+0000".equals(timezoneOffset)) {
                    str3 = timezoneOffset;
                    c2 = c;
                    offset4 = offset;
                } else if ("+00:00".equals(timezoneOffset)) {
                    str3 = timezoneOffset;
                    c2 = c;
                    offset4 = offset;
                } else {
                    input = new StringBuilder();
                    input.append("GMT");
                    input.append(timezoneOffset);
                    input = input.toString();
                    TimeZone timezone3 = TimeZone.getTimeZone(input);
                    timezoneOffset = timezone3.getID();
                    if (timezoneOffset.equals(input)) {
                        offset4 = offset;
                    } else {
                        offset4 = offset;
                        c = timezoneOffset.replace(":", "");
                        if (!c.equals(input)) {
                            String act = timezoneOffset;
                            timezoneOffset = new StringBuilder();
                            String cleaned = c;
                            timezoneOffset.append("Mismatching time zone indicator: ");
                            timezoneOffset.append(input);
                            timezoneOffset.append(" given, resolves to ");
                            timezoneOffset.append(timezone3.getID());
                            throw new IndexOutOfBoundsException(timezoneOffset.toString());
                        }
                    }
                    timezone = timezone3;
                    offset = offset4;
                }
                timezone = TIMEZONE_UTC;
                offset = offset4;
            }
            fail2 = new GregorianCalendar(timezone);
            fail2.setLenient(false);
            fail2.set(1, year);
            fail2.set(2, month - 1);
            fail2.set(5, day);
            fail2.set(11, hour);
            fail2.set(12, minutes);
            fail2.set(13, seconds);
            fail2.set(14, milliseconds);
            parsePosition.setIndex(offset);
            return fail2.getTime();
        } catch (Exception e222222) {
            exception = fail2;
            fail = e222222;
            if (str2 == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(Typography.quote);
                stringBuilder.append(str2);
                stringBuilder.append(FormatHelper.QUOTE);
                str = stringBuilder.toString();
            } else {
                str = null;
            }
            input = str;
            msg = fail.getMessage();
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(Constants.OPENING_BRACE);
            stringBuilder2.append(fail.getClass().getName());
            stringBuilder2.append(")");
            msg = stringBuilder2.toString();
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Failed to parse date [");
            stringBuilder3.append(input);
            stringBuilder3.append("]: ");
            stringBuilder3.append(msg);
            ex = new ParseException(stringBuilder3.toString(), pos.getIndex());
            ex.initCause(fail);
            throw ex;
        } catch (Exception e2222222) {
            exception = fail2;
            fail = e2222222;
            if (str2 == null) {
                str = null;
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(Typography.quote);
                stringBuilder.append(str2);
                stringBuilder.append(FormatHelper.QUOTE);
                str = stringBuilder.toString();
            }
            input = str;
            msg = fail.getMessage();
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(Constants.OPENING_BRACE);
            stringBuilder2.append(fail.getClass().getName());
            stringBuilder2.append(")");
            msg = stringBuilder2.toString();
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Failed to parse date [");
            stringBuilder3.append(input);
            stringBuilder3.append("]: ");
            stringBuilder3.append(msg);
            ex = new ParseException(stringBuilder3.toString(), pos.getIndex());
            ex.initCause(fail);
            throw ex;
        } catch (Exception e22222222) {
            exception = fail2;
            fail = e22222222;
            if (str2 == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(Typography.quote);
                stringBuilder.append(str2);
                stringBuilder.append(FormatHelper.QUOTE);
                str = stringBuilder.toString();
            } else {
                str = null;
            }
            input = str;
            msg = fail.getMessage();
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(Constants.OPENING_BRACE);
            stringBuilder2.append(fail.getClass().getName());
            stringBuilder2.append(")");
            msg = stringBuilder2.toString();
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Failed to parse date [");
            stringBuilder3.append(input);
            stringBuilder3.append("]: ");
            stringBuilder3.append(msg);
            ex = new ParseException(stringBuilder3.toString(), pos.getIndex());
            ex.initCause(fail);
            throw ex;
        }
    }

    private static boolean checkOffset(String value, int offset, char expected) {
        return offset < value.length() && value.charAt(offset) == expected;
    }

    private static int parseInt(java.lang.String r6, int r7, int r8) throws java.lang.NumberFormatException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        if (r7 < 0) goto L_0x0069;
    L_0x0002:
        r0 = r6.length();
        if (r8 > r0) goto L_0x0069;
    L_0x0008:
        if (r7 <= r8) goto L_0x000b;
    L_0x000a:
        goto L_0x0069;
    L_0x000b:
        r0 = r7;
        r1 = 0;
        r2 = 10;
        if (r0 >= r8) goto L_0x003a;
    L_0x0011:
        r3 = r0 + 1;
        r0 = r6.charAt(r0);
        r0 = java.lang.Character.digit(r0, r2);
        if (r0 >= 0) goto L_0x0038;
    L_0x001d:
        r2 = new java.lang.NumberFormatException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Invalid number: ";
        r4.append(r5);
        r5 = r6.substring(r7, r8);
        r4.append(r5);
        r4 = r4.toString();
        r2.<init>(r4);
        throw r2;
    L_0x0038:
        r1 = -r0;
    L_0x0039:
        r0 = r3;
    L_0x003a:
        if (r0 >= r8) goto L_0x0067;
    L_0x003c:
        r3 = r0 + 1;
        r0 = r6.charAt(r0);
        r0 = java.lang.Character.digit(r0, r2);
        if (r0 >= 0) goto L_0x0063;
    L_0x0048:
        r2 = new java.lang.NumberFormatException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Invalid number: ";
        r4.append(r5);
        r5 = r6.substring(r7, r8);
        r4.append(r5);
        r4 = r4.toString();
        r2.<init>(r4);
        throw r2;
    L_0x0063:
        r1 = r1 * 10;
        r1 = r1 - r0;
        goto L_0x0039;
    L_0x0067:
        r2 = -r1;
        return r2;
    L_0x0069:
        r0 = new java.lang.NumberFormatException;
        r0.<init>(r6);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.util.ISO8601Utils.parseInt(java.lang.String, int, int):int");
    }

    private static void padInt(StringBuilder buffer, int value, int length) {
        String strValue = Integer.toString(value);
        for (int i = length - strValue.length(); i > 0; i--) {
            buffer.append('0');
        }
        buffer.append(strValue);
    }

    private static int indexOfNonDigit(String string, int offset) {
        int i = offset;
        while (i < string.length()) {
            char c = string.charAt(i);
            if (c >= '0') {
                if (c <= '9') {
                    i++;
                }
            }
            return i;
        }
        return string.length();
    }
}
