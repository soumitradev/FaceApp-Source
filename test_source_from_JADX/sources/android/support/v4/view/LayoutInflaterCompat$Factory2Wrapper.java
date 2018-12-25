package android.support.v4.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater.Factory2;
import android.view.View;

class LayoutInflaterCompat$Factory2Wrapper implements Factory2 {
    final LayoutInflaterFactory mDelegateFactory;

    LayoutInflaterCompat$Factory2Wrapper(LayoutInflaterFactory delegateFactory) {
        this.mDelegateFactory = delegateFactory;
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return this.mDelegateFactory.onCreateView(null, name, context, attrs);
    }

    public View onCreateView(View parent, String name, Context context, AttributeSet attributeSet) {
        return this.mDelegateFactory.onCreateView(parent, name, context, attributeSet);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getName());
        stringBuilder.append("{");
        stringBuilder.append(this.mDelegateFactory);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
