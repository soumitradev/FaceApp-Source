package android.support.v4.app;

import android.app.PendingIntent;
import java.util.ArrayList;
import java.util.List;

public class NotificationCompat$CarExtender$UnreadConversation {
    private final long mLatestTimestamp;
    private final String[] mMessages;
    private final String[] mParticipants;
    private final PendingIntent mReadPendingIntent;
    private final RemoteInput mRemoteInput;
    private final PendingIntent mReplyPendingIntent;

    /* renamed from: android.support.v4.app.NotificationCompat$CarExtender$UnreadConversation$Builder */
    public static class Builder {
        private long mLatestTimestamp;
        private final List<String> mMessages = new ArrayList();
        private final String mParticipant;
        private PendingIntent mReadPendingIntent;
        private RemoteInput mRemoteInput;
        private PendingIntent mReplyPendingIntent;

        public Builder(String name) {
            this.mParticipant = name;
        }

        public Builder addMessage(String message) {
            this.mMessages.add(message);
            return this;
        }

        public Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
            this.mRemoteInput = remoteInput;
            this.mReplyPendingIntent = pendingIntent;
            return this;
        }

        public Builder setReadPendingIntent(PendingIntent pendingIntent) {
            this.mReadPendingIntent = pendingIntent;
            return this;
        }

        public Builder setLatestTimestamp(long timestamp) {
            this.mLatestTimestamp = timestamp;
            return this;
        }

        public NotificationCompat$CarExtender$UnreadConversation build() {
            return new NotificationCompat$CarExtender$UnreadConversation((String[]) this.mMessages.toArray(new String[this.mMessages.size()]), this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, new String[]{this.mParticipant}, this.mLatestTimestamp);
        }
    }

    NotificationCompat$CarExtender$UnreadConversation(String[] messages, RemoteInput remoteInput, PendingIntent replyPendingIntent, PendingIntent readPendingIntent, String[] participants, long latestTimestamp) {
        this.mMessages = messages;
        this.mRemoteInput = remoteInput;
        this.mReadPendingIntent = readPendingIntent;
        this.mReplyPendingIntent = replyPendingIntent;
        this.mParticipants = participants;
        this.mLatestTimestamp = latestTimestamp;
    }

    public String[] getMessages() {
        return this.mMessages;
    }

    public RemoteInput getRemoteInput() {
        return this.mRemoteInput;
    }

    public PendingIntent getReplyPendingIntent() {
        return this.mReplyPendingIntent;
    }

    public PendingIntent getReadPendingIntent() {
        return this.mReadPendingIntent;
    }

    public String[] getParticipants() {
        return this.mParticipants;
    }

    public String getParticipant() {
        return this.mParticipants.length > 0 ? this.mParticipants[0] : null;
    }

    public long getLatestTimestamp() {
        return this.mLatestTimestamp;
    }
}
