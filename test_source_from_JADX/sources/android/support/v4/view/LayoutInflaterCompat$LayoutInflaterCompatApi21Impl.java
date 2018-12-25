package android.support.v4.view;

import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;

@RequiresApi(21)
class LayoutInflaterCompat$LayoutInflaterCompatApi21Impl extends LayoutInflaterCompat$LayoutInflaterCompatBaseImpl {
    LayoutInflaterCompat$LayoutInflaterCompatApi21Impl() {
    }

    public void setFactory(LayoutInflater inflater, LayoutInflaterFactory factory) {
        inflater.setFactory2(factory != null ? new LayoutInflaterCompat$Factory2Wrapper(factory) : null);
    }

    public void setFactory2(LayoutInflater inflater, Factory2 factory) {
        inflater.setFactory2(factory);
    }
}
