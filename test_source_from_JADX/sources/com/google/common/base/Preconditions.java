package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import javax.annotation.Nullable;
import org.catrobat.catroid.common.Constants;

@GwtCompatible
public final class Preconditions {
    private Preconditions() {
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static void checkArgument(boolean expression, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    public static void checkState(boolean expression, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static <T> T checkNotNull(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException();
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(String.valueOf(errorMessage));
    }

    public static <T> T checkNotNull(T reference, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
    }

    public static int checkElementIndex(int index, int size) {
        return checkElementIndex(index, size, FirebaseAnalytics$Param.INDEX);
    }

    public static int checkElementIndex(int index, int size, @Nullable String desc) {
        if (index >= 0) {
            if (index < size) {
                return index;
            }
        }
        throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
    }

    private static String badElementIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, Integer.valueOf(index));
        } else if (size < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("negative size: ");
            stringBuilder.append(size);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else {
            return format("%s (%s) must be less than size (%s)", desc, Integer.valueOf(index), Integer.valueOf(size));
        }
    }

    public static int checkPositionIndex(int index, int size) {
        return checkPositionIndex(index, size, FirebaseAnalytics$Param.INDEX);
    }

    public static int checkPositionIndex(int index, int size, @Nullable String desc) {
        if (index >= 0) {
            if (index <= size) {
                return index;
            }
        }
        throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
    }

    private static String badPositionIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, Integer.valueOf(index));
        } else if (size < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("negative size: ");
            stringBuilder.append(size);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else {
            return format("%s (%s) must not be greater than size (%s)", desc, Integer.valueOf(index), Integer.valueOf(size));
        }
    }

    public static void checkPositionIndexes(int start, int end, int size) {
        if (start >= 0 && end >= start) {
            if (end <= size) {
                return;
            }
        }
        throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
    }

    private static String badPositionIndexes(int start, int end, int size) {
        if (start >= 0) {
            if (start <= size) {
                if (end >= 0) {
                    if (end <= size) {
                        return format("end index (%s) must not be less than start index (%s)", Integer.valueOf(end), Integer.valueOf(start));
                    }
                }
                return badPositionIndex(end, size, "end index");
            }
        }
        return badPositionIndex(start, size, "start index");
    }

    static String format(String template, @Nullable Object... args) {
        template = String.valueOf(template);
        StringBuilder builder = new StringBuilder(template.length() + (args.length * 16));
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            int i2 = i + 1;
            builder.append(args[i]);
            templateStart = placeholderStart + 2;
            i = i2;
        }
        builder.append(template.substring(templateStart));
        if (i < args.length) {
            builder.append(" [");
            placeholderStart = i + 1;
            builder.append(args[i]);
            while (true) {
                i = placeholderStart;
                if (i >= args.length) {
                    break;
                }
                builder.append(", ");
                placeholderStart = i + 1;
                builder.append(args[i]);
            }
            builder.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        }
        return builder.toString();
    }
}
