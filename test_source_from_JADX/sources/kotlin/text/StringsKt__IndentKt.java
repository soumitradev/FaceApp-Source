package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002¢\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002¢\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\b¢\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002¨\u0006\u0015"}, d2 = {"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"}, k = 5, mv = {1, 1, 10}, xi = 1, xs = "kotlin/text/StringsKt")
/* compiled from: Indent.kt */
class StringsKt__IndentKt {
    @NotNull
    public static /* synthetic */ String trimMargin$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "|";
        }
        return trimMargin(str, str2);
    }

    @NotNull
    public static final String trimMargin(@NotNull String $receiver, @NotNull String marginPrefix) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(marginPrefix, "marginPrefix");
        return replaceIndentByMargin($receiver, "", marginPrefix);
    }

    @NotNull
    public static /* synthetic */ String replaceIndentByMargin$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != null) {
            str2 = "";
        }
        if ((i & 2) != 0) {
            str3 = "|";
        }
        return replaceIndentByMargin(str, str2, str3);
    }

    @NotNull
    public static final String replaceIndentByMargin(@NotNull String $receiver, @NotNull String newIndent, @NotNull String marginPrefix) {
        String str = $receiver;
        String str2 = marginPrefix;
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        Intrinsics.checkParameterIsNotNull(newIndent, "newIndent");
        Intrinsics.checkParameterIsNotNull(str2, "marginPrefix");
        if ((StringsKt__StringsJVMKt.isBlank(str2) ^ 1) == 0) {
            Object obj = null;
            throw new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
        }
        List lines = StringsKt__StringsKt.lines(str);
        int resultSizeEstimate$iv = $receiver.length() + (newIndent.length() * lines.size());
        Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
        List $receiver$iv = lines;
        int $i$f$reindent$StringsKt__IndentKt = 0;
        int lastIndex$iv = CollectionsKt__CollectionsKt.getLastIndex($receiver$iv);
        int $i$f$mapIndexedNotNull = 0;
        Collection destination$iv$iv$iv = new ArrayList();
        int $i$f$mapIndexedNotNullTo = 0;
        Iterable $receiver$iv$iv$iv$iv = $receiver$iv;
        int $i$f$forEachIndexed = 0;
        int index$iv$iv$iv$iv = 0;
        Iterator it = $receiver$iv$iv$iv$iv.iterator();
        int $i$a$1$forEachIndexed = 0;
        int $i$a$1$mapIndexedNotNull = 0;
        while (it.hasNext()) {
            Iterator it2;
            Iterable $receiver$iv$iv$iv$iv2;
            Collection destination$iv$iv$iv2;
            int index$iv$iv$iv$iv2 = index$iv$iv$iv$iv + 1;
            int $i$a$1$forEachIndexed2 = $i$a$1$forEachIndexed;
            String str3 = (String) it.next();
            $i$a$1$forEachIndexed = index$iv$iv$iv$iv;
            int $i$a$1$mapIndexedNotNull2 = $i$a$1$mapIndexedNotNull;
            if (($i$a$1$forEachIndexed == 0 || $i$a$1$forEachIndexed == lastIndex$iv) && StringsKt__StringsJVMKt.isBlank(str3)) {
                it2 = it;
                $receiver$iv$iv$iv$iv2 = $receiver$iv$iv$iv$iv;
                destination$iv$iv$iv2 = destination$iv$iv$iv;
                str3 = null;
            } else {
                String line;
                String line2;
                String line3 = str3;
                CharSequence $receiver$iv2 = line3;
                int length = $receiver$iv2.length();
                int firstNonWhitespaceIndex = 0;
                while (true) {
                    int index$iv = $i$a$1$forEachIndexed;
                    if (firstNonWhitespaceIndex >= length) {
                        break;
                    } else if ((CharsKt__CharJVMKt.isWhitespace($receiver$iv2.charAt(firstNonWhitespaceIndex)) ^ 1) != 0) {
                        break;
                    } else {
                        firstNonWhitespaceIndex++;
                        $i$a$1$forEachIndexed = index$iv;
                    }
                    if (firstNonWhitespaceIndex != -1) {
                        it2 = it;
                        $receiver$iv$iv$iv$iv2 = $receiver$iv$iv$iv$iv;
                        destination$iv$iv$iv2 = destination$iv$iv$iv;
                    } else {
                        line = line3;
                        it2 = it;
                        $receiver$iv$iv$iv$iv2 = $receiver$iv$iv$iv$iv;
                        destination$iv$iv$iv2 = destination$iv$iv$iv;
                        if (StringsKt__StringsJVMKt.startsWith$default(line3, str2, firstNonWhitespaceIndex, false, 4, null)) {
                        } else {
                            index$iv$iv$iv$iv = marginPrefix.length() + firstNonWhitespaceIndex;
                            line2 = line;
                            if (line2 != null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }
                            obj = line2.substring(index$iv$iv$iv$iv);
                            Intrinsics.checkExpressionValueIsNotNull(obj, "(this as java.lang.String).substring(startIndex)");
                            if (obj != null) {
                                str = (String) indentAddFunction$iv.invoke(obj);
                                if (str != null) {
                                    str3 = str;
                                }
                            }
                        }
                    }
                    obj = null;
                    if (obj != null) {
                        str = (String) indentAddFunction$iv.invoke(obj);
                        if (str != null) {
                            str3 = str;
                        }
                    }
                }
                firstNonWhitespaceIndex = -1;
                if (firstNonWhitespaceIndex != -1) {
                    line = line3;
                    it2 = it;
                    $receiver$iv$iv$iv$iv2 = $receiver$iv$iv$iv$iv;
                    destination$iv$iv$iv2 = destination$iv$iv$iv;
                    if (StringsKt__StringsJVMKt.startsWith$default(line3, str2, firstNonWhitespaceIndex, false, 4, null)) {
                    } else {
                        index$iv$iv$iv$iv = marginPrefix.length() + firstNonWhitespaceIndex;
                        line2 = line;
                        if (line2 != null) {
                            obj = line2.substring(index$iv$iv$iv$iv);
                            Intrinsics.checkExpressionValueIsNotNull(obj, "(this as java.lang.String).substring(startIndex)");
                            if (obj != null) {
                                str = (String) indentAddFunction$iv.invoke(obj);
                                if (str != null) {
                                    str3 = str;
                                }
                            }
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                    }
                }
                it2 = it;
                $receiver$iv$iv$iv$iv2 = $receiver$iv$iv$iv$iv;
                destination$iv$iv$iv2 = destination$iv$iv$iv;
                obj = null;
                if (obj != null) {
                    str = (String) indentAddFunction$iv.invoke(obj);
                    if (str != null) {
                        str3 = str;
                    }
                }
            }
            if (str3 != null) {
                index$iv$iv$iv$iv = 0;
                destination$iv$iv$iv2.add(str3);
            }
            destination$iv$iv$iv = destination$iv$iv$iv2;
            $receiver$iv$iv$iv$iv = $receiver$iv$iv$iv$iv2;
            index$iv$iv$iv$iv = index$iv$iv$iv$iv2;
            $i$a$1$forEachIndexed = $i$a$1$forEachIndexed2;
            $i$a$1$mapIndexedNotNull = $i$a$1$mapIndexedNotNull2;
            it = it2;
            str = $receiver;
            String destination$iv$iv$iv3 = newIndent;
        }
        str = ((StringBuilder) CollectionsKt___CollectionsKt.joinTo$default((List) destination$iv$iv$iv, new StringBuilder(resultSizeEstimate$iv), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(str, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return str;
    }

    @NotNull
    public static final String trimIndent(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return replaceIndent($receiver, "");
    }

    @NotNull
    public static /* synthetic */ String replaceIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        return replaceIndent(str, str2);
    }

    @NotNull
    public static final String replaceIndent(@NotNull String $receiver, @NotNull String newIndent) {
        int minCommonIndent;
        String str = $receiver;
        Intrinsics.checkParameterIsNotNull(str, "$receiver");
        Intrinsics.checkParameterIsNotNull(newIndent, "newIndent");
        List lines = StringsKt__StringsKt.lines(str);
        int $i$f$filter = 0;
        Collection destination$iv$iv = new ArrayList();
        int $i$f$filterTo = 0;
        for (String element$iv$iv : lines) {
            if ((StringsKt__StringsJVMKt.isBlank(element$iv$iv) ^ 1) != 0) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable<String> $receiver$iv = (List) destination$iv$iv;
        $i$f$filter = $i$f$filterTo;
        destination$iv$iv = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault($receiver$iv, 10));
        $i$f$filterTo = 0;
        for (String p1 : $receiver$iv) {
            destination$iv$iv.add(Integer.valueOf(indentWidth$StringsKt__IndentKt(p1)));
        }
        Integer num = (Integer) CollectionsKt___CollectionsKt.min((Iterable) (List) destination$iv$iv);
        if (num != null) {
            minCommonIndent = num.intValue();
        } else {
            minCommonIndent = 0;
        }
        $i$f$filter = $receiver.length() + (newIndent.length() * lines.size());
        Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
        List<String> $receiver$iv2 = lines;
        $i$f$filterTo = 0;
        int lastIndex$iv = CollectionsKt__CollectionsKt.getLastIndex($receiver$iv2);
        int $i$f$mapIndexedNotNull = 0;
        Collection destination$iv$iv$iv = new ArrayList();
        int $i$f$mapIndexedNotNullTo = 0;
        int $i$f$forEachIndexed = 0;
        int index$iv$iv$iv = 0;
        for (String value$iv : $receiver$iv2) {
            Object it$iv$iv$iv;
            int index$iv$iv$iv$iv = index$iv$iv$iv + 1;
            int index$iv = index$iv$iv$iv;
            if (index$iv != 0) {
                if (index$iv != lastIndex$iv) {
                    int i = index$iv;
                    str = StringsKt___StringsKt.drop(value$iv, minCommonIndent);
                    if (str != null) {
                        it$iv$iv$iv = (String) indentAddFunction$iv.invoke(str);
                        if (it$iv$iv$iv != null) {
                            if (it$iv$iv$iv != null) {
                                destination$iv$iv$iv.add(it$iv$iv$iv);
                            }
                            index$iv$iv$iv = index$iv$iv$iv$iv;
                            str = $receiver;
                        }
                    }
                    it$iv$iv$iv = value$iv;
                    if (it$iv$iv$iv != null) {
                        destination$iv$iv$iv.add(it$iv$iv$iv);
                    }
                    index$iv$iv$iv = index$iv$iv$iv$iv;
                    str = $receiver;
                }
            }
            if (StringsKt__StringsJVMKt.isBlank(value$iv)) {
                it$iv$iv$iv = null;
                if (it$iv$iv$iv != null) {
                    destination$iv$iv$iv.add(it$iv$iv$iv);
                }
                index$iv$iv$iv = index$iv$iv$iv$iv;
                str = $receiver;
            }
            str = StringsKt___StringsKt.drop(value$iv, minCommonIndent);
            if (str != null) {
                it$iv$iv$iv = (String) indentAddFunction$iv.invoke(str);
                if (it$iv$iv$iv != null) {
                    if (it$iv$iv$iv != null) {
                        destination$iv$iv$iv.add(it$iv$iv$iv);
                    }
                    index$iv$iv$iv = index$iv$iv$iv$iv;
                    str = $receiver;
                }
            }
            it$iv$iv$iv = value$iv;
            if (it$iv$iv$iv != null) {
                destination$iv$iv$iv.add(it$iv$iv$iv);
            }
            index$iv$iv$iv = index$iv$iv$iv$iv;
            str = $receiver;
        }
        str = ((StringBuilder) CollectionsKt___CollectionsKt.joinTo$default((List) destination$iv$iv$iv, new StringBuilder($i$f$filter), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(str, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return str;
    }

    @NotNull
    public static /* synthetic */ String prependIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "    ";
        }
        return prependIndent(str, str2);
    }

    @NotNull
    public static final String prependIndent(@NotNull String $receiver, @NotNull String indent) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(indent, "indent");
        return SequencesKt___SequencesKt.joinToString$default(SequencesKt___SequencesKt.map(StringsKt__StringsKt.lineSequence($receiver), new StringsKt__IndentKt$prependIndent$1(indent)), "\n", null, null, 0, null, null, 62, null);
    }

    private static final int indentWidth$StringsKt__IndentKt(@NotNull String $receiver) {
        CharSequence $receiver$iv = $receiver;
        int length = $receiver$iv.length();
        int i = 0;
        while (i < length) {
            if ((CharsKt__CharJVMKt.isWhitespace($receiver$iv.charAt(i)) ^ 1) != 0) {
                break;
            }
            i++;
        }
        i = -1;
        int it = i;
        int $i$a$2$let = length;
        return it == -1 ? $receiver.length() : it;
    }

    private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(String indent) {
        Function1<String, String> function1;
        if ((((CharSequence) indent).length() == 0 ? 1 : null) != null) {
            function1 = StringsKt__IndentKt$getIndentFunction$1.INSTANCE;
        } else {
            function1 = new StringsKt__IndentKt$getIndentFunction$2(indent);
        }
        return function1;
    }

    private static final String reindent$StringsKt__IndentKt(@NotNull List<String> $receiver, int resultSizeEstimate, Function1<? super String, String> indentAddFunction, Function1<? super String, String> indentCutFunction) {
        int i;
        String $i$f$reindent$StringsKt__IndentKt;
        int $i$f$reindent$StringsKt__IndentKt2 = 0;
        int lastIndex = CollectionsKt__CollectionsKt.getLastIndex($receiver);
        int $i$f$mapIndexedNotNull = 0;
        Collection destination$iv$iv = new ArrayList();
        int $i$f$mapIndexedNotNullTo = 0;
        int $i$f$forEachIndexed = 0;
        int index$iv$iv = 0;
        for (String $i$f$reindent$StringsKt__IndentKt3 : $receiver) {
            String value;
            int index$iv$iv$iv = index$iv$iv + 1;
            int $i$f$reindent$StringsKt__IndentKt4 = $i$f$reindent$StringsKt__IndentKt2;
            int index = index$iv$iv;
            if (index != 0) {
                if (index != lastIndex) {
                    i = lastIndex;
                    value = $i$f$reindent$StringsKt__IndentKt3;
                    $i$f$reindent$StringsKt__IndentKt3 = (String) indentCutFunction.invoke($i$f$reindent$StringsKt__IndentKt3);
                    if ($i$f$reindent$StringsKt__IndentKt3 == null) {
                        $i$f$reindent$StringsKt__IndentKt2 = (String) indentAddFunction.invoke($i$f$reindent$StringsKt__IndentKt3);
                        if ($i$f$reindent$StringsKt__IndentKt2 != null) {
                            if ($i$f$reindent$StringsKt__IndentKt2 != 0) {
                                index = 0;
                                destination$iv$iv.add($i$f$reindent$StringsKt__IndentKt2);
                            }
                            index$iv$iv = index$iv$iv$iv;
                            $i$f$reindent$StringsKt__IndentKt2 = $i$f$reindent$StringsKt__IndentKt4;
                            lastIndex = i;
                        }
                    } else {
                        Function1<? super String, String> function1 = indentAddFunction;
                    }
                    $i$f$reindent$StringsKt__IndentKt2 = value;
                    if ($i$f$reindent$StringsKt__IndentKt2 != 0) {
                        index = 0;
                        destination$iv$iv.add($i$f$reindent$StringsKt__IndentKt2);
                    }
                    index$iv$iv = index$iv$iv$iv;
                    $i$f$reindent$StringsKt__IndentKt2 = $i$f$reindent$StringsKt__IndentKt4;
                    lastIndex = i;
                }
            }
            i = lastIndex;
            if (StringsKt__StringsJVMKt.isBlank($i$f$reindent$StringsKt__IndentKt3)) {
                $i$f$reindent$StringsKt__IndentKt2 = null;
                function1 = indentAddFunction;
                if ($i$f$reindent$StringsKt__IndentKt2 != 0) {
                    index = 0;
                    destination$iv$iv.add($i$f$reindent$StringsKt__IndentKt2);
                }
                index$iv$iv = index$iv$iv$iv;
                $i$f$reindent$StringsKt__IndentKt2 = $i$f$reindent$StringsKt__IndentKt4;
                lastIndex = i;
            }
            value = $i$f$reindent$StringsKt__IndentKt3;
            $i$f$reindent$StringsKt__IndentKt3 = (String) indentCutFunction.invoke($i$f$reindent$StringsKt__IndentKt3);
            if ($i$f$reindent$StringsKt__IndentKt3 == null) {
                Function1<? super String, String> function12 = indentAddFunction;
            } else {
                $i$f$reindent$StringsKt__IndentKt2 = (String) indentAddFunction.invoke($i$f$reindent$StringsKt__IndentKt3);
                if ($i$f$reindent$StringsKt__IndentKt2 != null) {
                    if ($i$f$reindent$StringsKt__IndentKt2 != 0) {
                        index = 0;
                        destination$iv$iv.add($i$f$reindent$StringsKt__IndentKt2);
                    }
                    index$iv$iv = index$iv$iv$iv;
                    $i$f$reindent$StringsKt__IndentKt2 = $i$f$reindent$StringsKt__IndentKt4;
                    lastIndex = i;
                }
            }
            $i$f$reindent$StringsKt__IndentKt2 = value;
            if ($i$f$reindent$StringsKt__IndentKt2 != 0) {
                index = 0;
                destination$iv$iv.add($i$f$reindent$StringsKt__IndentKt2);
            }
            index$iv$iv = index$iv$iv$iv;
            $i$f$reindent$StringsKt__IndentKt2 = $i$f$reindent$StringsKt__IndentKt4;
            lastIndex = i;
        }
        i = lastIndex;
        lastIndex = indentAddFunction;
        $i$f$reindent$StringsKt__IndentKt3 = ((StringBuilder) CollectionsKt___CollectionsKt.joinTo$default((List) destination$iv$iv, new StringBuilder(resultSizeEstimate), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull($i$f$reindent$StringsKt__IndentKt3, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return $i$f$reindent$StringsKt__IndentKt3;
    }
}
