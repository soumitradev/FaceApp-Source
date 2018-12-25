package android.support.v4.app;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public final class NotificationCompat$MessagingStyle$Message {
    static final String KEY_DATA_MIME_TYPE = "type";
    static final String KEY_DATA_URI = "uri";
    static final String KEY_EXTRAS_BUNDLE = "extras";
    static final String KEY_SENDER = "sender";
    static final String KEY_TEXT = "text";
    static final String KEY_TIMESTAMP = "time";
    private String mDataMimeType;
    private Uri mDataUri;
    private Bundle mExtras = new Bundle();
    private final CharSequence mSender;
    private final CharSequence mText;
    private final long mTimestamp;

    public NotificationCompat$MessagingStyle$Message(CharSequence text, long timestamp, CharSequence sender) {
        this.mText = text;
        this.mTimestamp = timestamp;
        this.mSender = sender;
    }

    public NotificationCompat$MessagingStyle$Message setData(String dataMimeType, Uri dataUri) {
        this.mDataMimeType = dataMimeType;
        this.mDataUri = dataUri;
        return this;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public CharSequence getSender() {
        return this.mSender;
    }

    public String getDataMimeType() {
        return this.mDataMimeType;
    }

    public Uri getDataUri() {
        return this.mDataUri;
    }

    private Bundle toBundle() {
        Bundle bundle = new Bundle();
        if (this.mText != null) {
            bundle.putCharSequence(KEY_TEXT, this.mText);
        }
        bundle.putLong(KEY_TIMESTAMP, this.mTimestamp);
        if (this.mSender != null) {
            bundle.putCharSequence(KEY_SENDER, this.mSender);
        }
        if (this.mDataMimeType != null) {
            bundle.putString("type", this.mDataMimeType);
        }
        if (this.mDataUri != null) {
            bundle.putParcelable("uri", this.mDataUri);
        }
        if (this.mExtras != null) {
            bundle.putBundle("extras", this.mExtras);
        }
        return bundle;
    }

    static Bundle[] getBundleArrayForMessages(List<NotificationCompat$MessagingStyle$Message> messages) {
        Bundle[] bundles = new Bundle[messages.size()];
        int N = messages.size();
        for (int i = 0; i < N; i++) {
            bundles[i] = ((NotificationCompat$MessagingStyle$Message) messages.get(i)).toBundle();
        }
        return bundles;
    }

    static List<NotificationCompat$MessagingStyle$Message> getMessagesFromBundleArray(Parcelable[] bundles) {
        List<NotificationCompat$MessagingStyle$Message> messages = new ArrayList(bundles.length);
        for (int i = 0; i < bundles.length; i++) {
            if (bundles[i] instanceof Bundle) {
                NotificationCompat$MessagingStyle$Message message = getMessageFromBundle((Bundle) bundles[i]);
                if (message != null) {
                    messages.add(message);
                }
            }
        }
        return messages;
    }

    static NotificationCompat$MessagingStyle$Message getMessageFromBundle(Bundle bundle) {
        try {
            if (bundle.containsKey(KEY_TEXT)) {
                if (bundle.containsKey(KEY_TIMESTAMP)) {
                    NotificationCompat$MessagingStyle$Message message = new NotificationCompat$MessagingStyle$Message(bundle.getCharSequence(KEY_TEXT), bundle.getLong(KEY_TIMESTAMP), bundle.getCharSequence(KEY_SENDER));
                    if (bundle.containsKey("type") && bundle.containsKey("uri")) {
                        message.setData(bundle.getString("type"), (Uri) bundle.getParcelable("uri"));
                    }
                    if (bundle.containsKey("extras")) {
                        message.getExtras().putAll(bundle.getBundle("extras"));
                    }
                    return message;
                }
            }
            return null;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
