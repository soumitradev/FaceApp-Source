package com.google.common.base;

import java.util.Iterator;
import org.catrobat.catroid.common.Constants;

class Splitter$5 implements Iterable<String> {
    final /* synthetic */ Splitter this$0;
    final /* synthetic */ CharSequence val$sequence;

    Splitter$5(Splitter splitter, CharSequence charSequence) {
        this.this$0 = splitter;
        this.val$sequence = charSequence;
    }

    public Iterator<String> iterator() {
        return Splitter.access$000(this.this$0, this.val$sequence);
    }

    public String toString() {
        Joiner on = Joiner.on(", ");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.REMIX_URL_PREFIX_INDICATOR);
        StringBuilder appendTo = on.appendTo(stringBuilder, this);
        appendTo.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        return appendTo.toString();
    }
}
