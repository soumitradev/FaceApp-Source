package android.support.v4.app;

import android.os.Bundle;
import java.util.HashSet;
import java.util.Set;

public final class RemoteInput$Builder {
    private boolean mAllowFreeFormTextInput = true;
    private final Set<String> mAllowedDataTypes = new HashSet();
    private CharSequence[] mChoices;
    private Bundle mExtras = new Bundle();
    private CharSequence mLabel;
    private final String mResultKey;

    public RemoteInput$Builder(String resultKey) {
        if (resultKey == null) {
            throw new IllegalArgumentException("Result key can't be null");
        }
        this.mResultKey = resultKey;
    }

    public RemoteInput$Builder setLabel(CharSequence label) {
        this.mLabel = label;
        return this;
    }

    public RemoteInput$Builder setChoices(CharSequence[] choices) {
        this.mChoices = choices;
        return this;
    }

    public RemoteInput$Builder setAllowDataType(String mimeType, boolean doAllow) {
        if (doAllow) {
            this.mAllowedDataTypes.add(mimeType);
        } else {
            this.mAllowedDataTypes.remove(mimeType);
        }
        return this;
    }

    public RemoteInput$Builder setAllowFreeFormInput(boolean allowFreeFormTextInput) {
        this.mAllowFreeFormTextInput = allowFreeFormTextInput;
        return this;
    }

    public RemoteInput$Builder addExtras(Bundle extras) {
        if (extras != null) {
            this.mExtras.putAll(extras);
        }
        return this;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public RemoteInput build() {
        return new RemoteInput(this.mResultKey, this.mLabel, this.mChoices, this.mAllowFreeFormTextInput, this.mExtras, this.mAllowedDataTypes);
    }
}
