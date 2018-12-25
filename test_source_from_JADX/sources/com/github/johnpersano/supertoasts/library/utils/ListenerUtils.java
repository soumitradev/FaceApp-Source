package com.github.johnpersano.supertoasts.library.utils;

import com.github.johnpersano.supertoasts.library.SuperActivityToast.OnButtonClickListener;
import com.github.johnpersano.supertoasts.library.SuperToast.OnDismissListener;
import java.util.HashMap;

public class ListenerUtils {
    private final HashMap<String, OnButtonClickListener> mOnButtonClickListenerHashMap = new HashMap();
    private final HashMap<String, OnDismissListener> mOnDismissListenerHashMap = new HashMap();

    public static ListenerUtils newInstance() {
        return new ListenerUtils();
    }

    public ListenerUtils putListener(String tag, OnDismissListener onDismissListener) {
        this.mOnDismissListenerHashMap.put(tag, onDismissListener);
        return this;
    }

    public ListenerUtils putListener(String tag, OnButtonClickListener onButtonClickListener) {
        this.mOnButtonClickListenerHashMap.put(tag, onButtonClickListener);
        return this;
    }

    public HashMap<String, OnDismissListener> getOnDismissListenerHashMap() {
        return this.mOnDismissListenerHashMap;
    }

    public HashMap<String, OnButtonClickListener> getOnButtonClickListenerHashMap() {
        return this.mOnButtonClickListenerHashMap;
    }
}
