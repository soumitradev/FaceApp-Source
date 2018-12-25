package com.thoughtworks.xstream.converters.basic;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.ErrorReporter;
import com.thoughtworks.xstream.converters.ErrorWriter;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.ThreadSafeSimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter extends AbstractSingleValueConverter implements ErrorReporter {
    private static final String[] DEFAULT_ACCEPTABLE_FORMATS;
    private static final String DEFAULT_ERA_PATTERN;
    private static final String DEFAULT_PATTERN;
    private static final long ERA_START;
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    private final ThreadSafeSimpleDateFormat[] acceptableFormats;
    private final ThreadSafeSimpleDateFormat defaultEraFormat;
    private final ThreadSafeSimpleDateFormat defaultFormat;
    private final Locale locale;

    static {
        String defaultPattern = "yyyy-MM-dd HH:mm:ss.S z";
        String defaultEraPattern = "yyyy-MM-dd G HH:mm:ss.S z";
        List acceptablePatterns = new ArrayList();
        boolean utcSupported = JVM.canParseUTCDateFormat();
        DEFAULT_PATTERN = utcSupported ? "yyyy-MM-dd HH:mm:ss.S z" : "yyyy-MM-dd HH:mm:ss.S 'UTC'";
        DEFAULT_ERA_PATTERN = utcSupported ? "yyyy-MM-dd G HH:mm:ss.S z" : "yyyy-MM-dd G HH:mm:ss.S 'UTC'";
        acceptablePatterns.add("yyyy-MM-dd HH:mm:ss.S z");
        if (!utcSupported) {
            acceptablePatterns.add("yyyy-MM-dd HH:mm:ss.S z");
        }
        acceptablePatterns.add("yyyy-MM-dd HH:mm:ss.S a");
        acceptablePatterns.add("yyyy-MM-dd HH:mm:ssz");
        acceptablePatterns.add("yyyy-MM-dd HH:mm:ss z");
        if (!utcSupported) {
            acceptablePatterns.add("yyyy-MM-dd HH:mm:ss 'UTC'");
        }
        acceptablePatterns.add("yyyy-MM-dd HH:mm:ssa");
        DEFAULT_ACCEPTABLE_FORMATS = (String[]) acceptablePatterns.toArray(new String[acceptablePatterns.size()]);
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(UTC);
        cal.clear();
        cal.set(1, 0, 1);
        ERA_START = cal.getTime().getTime();
    }

    public DateConverter() {
        this(false);
    }

    public DateConverter(TimeZone timeZone) {
        this(DEFAULT_PATTERN, DEFAULT_ACCEPTABLE_FORMATS, timeZone);
    }

    public DateConverter(boolean lenient) {
        this(DEFAULT_PATTERN, DEFAULT_ACCEPTABLE_FORMATS, lenient);
    }

    public DateConverter(String defaultFormat, String[] acceptableFormats) {
        this(defaultFormat, acceptableFormats, false);
    }

    public DateConverter(String defaultFormat, String[] acceptableFormats, TimeZone timeZone) {
        this(defaultFormat, acceptableFormats, timeZone, false);
    }

    public DateConverter(String defaultFormat, String[] acceptableFormats, boolean lenient) {
        this(defaultFormat, acceptableFormats, UTC, lenient);
    }

    public DateConverter(String defaultFormat, String[] acceptableFormats, TimeZone timeZone, boolean lenient) {
        this(DEFAULT_ERA_PATTERN, defaultFormat, acceptableFormats, Locale.ENGLISH, timeZone, lenient);
    }

    public DateConverter(String defaultEraFormat, String defaultFormat, String[] acceptableFormats, Locale locale, TimeZone timeZone, boolean lenient) {
        this.locale = locale;
        if (defaultEraFormat != null) {
            this.defaultEraFormat = new ThreadSafeSimpleDateFormat(defaultEraFormat, timeZone, locale, 4, 20, lenient);
        } else {
            this.defaultEraFormat = null;
        }
        this.defaultFormat = new ThreadSafeSimpleDateFormat(defaultFormat, timeZone, locale, 4, 20, lenient);
        int i = 0;
        this.acceptableFormats = acceptableFormats != null ? new ThreadSafeSimpleDateFormat[acceptableFormats.length] : new ThreadSafeSimpleDateFormat[0];
        while (i < this.acceptableFormats.length) {
            this.acceptableFormats[i] = new ThreadSafeSimpleDateFormat(acceptableFormats[i], timeZone, 1, 20, lenient);
            i++;
        }
    }

    public boolean canConvert(Class type) {
        return type.equals(Date.class);
    }

    public Object fromString(String str) {
        if (this.defaultEraFormat != null) {
            try {
                return this.defaultEraFormat.parse(str);
            } catch (ParseException e) {
            }
        }
        if (this.defaultEraFormat != this.defaultFormat) {
            try {
                return this.defaultFormat.parse(str);
            } catch (ParseException e2) {
            }
        }
        int i = 0;
        while (i < this.acceptableFormats.length) {
            try {
                return this.acceptableFormats[i].parse(str);
            } catch (ParseException e3) {
                i++;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot parse date ");
        stringBuilder.append(str);
        throw new ConversionException(stringBuilder.toString());
    }

    public String toString(Object obj) {
        Date date = (Date) obj;
        if (date.getTime() >= ERA_START || this.defaultEraFormat == null) {
            return this.defaultFormat.format(date);
        }
        return this.defaultEraFormat.format(date);
    }

    public void appendErrors(ErrorWriter errorWriter) {
        errorWriter.add("Default date pattern", this.defaultFormat.toString());
        if (this.defaultEraFormat != null) {
            errorWriter.add("Default era date pattern", this.defaultEraFormat.toString());
        }
        for (ThreadSafeSimpleDateFormat threadSafeSimpleDateFormat : this.acceptableFormats) {
            errorWriter.add("Alternative date pattern", threadSafeSimpleDateFormat.toString());
        }
    }
}
