package android.support.v4.view;

import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;

class LayoutInflaterCompat$LayoutInflaterCompatBaseImpl {
    LayoutInflaterCompat$LayoutInflaterCompatBaseImpl() {
    }

    public void setFactory(LayoutInflater inflater, LayoutInflaterFactory factory) {
        setFactory2(inflater, factory != null ? new LayoutInflaterCompat$Factory2Wrapper(factory) : null);
    }

    public void setFactory2(LayoutInflater inflater, Factory2 factory) {
        inflater.setFactory2(factory);
        Factory f = inflater.getFactory();
        if (f instanceof Factory2) {
            LayoutInflaterCompat.forceSetFactory2(inflater, (Factory2) f);
        } else {
            LayoutInflaterCompat.forceSetFactory2(inflater, factory);
        }
    }

    public LayoutInflaterFactory getFactory(LayoutInflater inflater) {
        Factory factory = inflater.getFactory();
        if (factory instanceof LayoutInflaterCompat$Factory2Wrapper) {
            return ((LayoutInflaterCompat$Factory2Wrapper) factory).mDelegateFactory;
        }
        return null;
    }
}
