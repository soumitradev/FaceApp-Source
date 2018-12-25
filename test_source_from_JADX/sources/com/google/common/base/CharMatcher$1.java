package com.google.common.base;

class CharMatcher$1 extends CharMatcher$NegatedFastMatcher {
    final /* synthetic */ CharMatcher this$0;
    final /* synthetic */ String val$description;

    CharMatcher$1(CharMatcher charMatcher, CharMatcher x0, String str) {
        this.this$0 = charMatcher;
        this.val$description = str;
        super(x0);
    }

    public String toString() {
        return this.val$description;
    }
}
