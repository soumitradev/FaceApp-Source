package android.support.v4.media.session;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaSessionCompat.Callback;
import android.support.v4.media.session.MediaSessionCompat.MediaSessionImplBase;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.util.Log;
import android.view.KeyEvent;

class MediaSessionCompat$MediaSessionImplBase$MessageHandler extends Handler {
    private static final int KEYCODE_MEDIA_PAUSE = 127;
    private static final int KEYCODE_MEDIA_PLAY = 126;
    private static final int MSG_ADD_QUEUE_ITEM = 25;
    private static final int MSG_ADD_QUEUE_ITEM_AT = 26;
    private static final int MSG_ADJUST_VOLUME = 2;
    private static final int MSG_COMMAND = 1;
    private static final int MSG_CUSTOM_ACTION = 20;
    private static final int MSG_FAST_FORWARD = 16;
    private static final int MSG_MEDIA_BUTTON = 21;
    private static final int MSG_NEXT = 14;
    private static final int MSG_PAUSE = 12;
    private static final int MSG_PLAY = 7;
    private static final int MSG_PLAY_MEDIA_ID = 8;
    private static final int MSG_PLAY_SEARCH = 9;
    private static final int MSG_PLAY_URI = 10;
    private static final int MSG_PREPARE = 3;
    private static final int MSG_PREPARE_MEDIA_ID = 4;
    private static final int MSG_PREPARE_SEARCH = 5;
    private static final int MSG_PREPARE_URI = 6;
    private static final int MSG_PREVIOUS = 15;
    private static final int MSG_RATE = 19;
    private static final int MSG_RATE_EXTRA = 31;
    private static final int MSG_REMOVE_QUEUE_ITEM = 27;
    private static final int MSG_REMOVE_QUEUE_ITEM_AT = 28;
    private static final int MSG_REWIND = 17;
    private static final int MSG_SEEK_TO = 18;
    private static final int MSG_SET_CAPTIONING_ENABLED = 29;
    private static final int MSG_SET_REPEAT_MODE = 23;
    private static final int MSG_SET_SHUFFLE_MODE = 30;
    private static final int MSG_SET_VOLUME = 22;
    private static final int MSG_SKIP_TO_ITEM = 11;
    private static final int MSG_STOP = 13;
    final /* synthetic */ MediaSessionImplBase this$0;

    public MediaSessionCompat$MediaSessionImplBase$MessageHandler(MediaSessionImplBase this$0, Looper looper) {
        this.this$0 = this$0;
        super(looper);
    }

    public void post(int what, Object obj, Bundle bundle) {
        Message msg = obtainMessage(what, obj);
        msg.setData(bundle);
        msg.sendToTarget();
    }

    public void post(int what, Object obj) {
        obtainMessage(what, obj).sendToTarget();
    }

    public void post(int what) {
        post(what, null);
    }

    public void post(int what, Object obj, int arg1) {
        obtainMessage(what, arg1, 0, obj).sendToTarget();
    }

    public void handleMessage(Message msg) {
        Callback cb = this.this$0.mCallback;
        if (cb != null) {
            switch (msg.what) {
                case 1:
                    MediaSessionCompat$MediaSessionImplBase$Command cmd = msg.obj;
                    cb.onCommand(cmd.command, cmd.extras, cmd.stub);
                    break;
                case 2:
                    this.this$0.adjustVolume(msg.arg1, 0);
                    break;
                case 3:
                    cb.onPrepare();
                    break;
                case 4:
                    cb.onPrepareFromMediaId((String) msg.obj, msg.getData());
                    break;
                case 5:
                    cb.onPrepareFromSearch((String) msg.obj, msg.getData());
                    break;
                case 6:
                    cb.onPrepareFromUri((Uri) msg.obj, msg.getData());
                    break;
                case 7:
                    cb.onPlay();
                    break;
                case 8:
                    cb.onPlayFromMediaId((String) msg.obj, msg.getData());
                    break;
                case 9:
                    cb.onPlayFromSearch((String) msg.obj, msg.getData());
                    break;
                case 10:
                    cb.onPlayFromUri((Uri) msg.obj, msg.getData());
                    break;
                case 11:
                    cb.onSkipToQueueItem(((Long) msg.obj).longValue());
                    break;
                case 12:
                    cb.onPause();
                    break;
                case 13:
                    cb.onStop();
                    break;
                case 14:
                    cb.onSkipToNext();
                    break;
                case 15:
                    cb.onSkipToPrevious();
                    break;
                case 16:
                    cb.onFastForward();
                    break;
                case 17:
                    cb.onRewind();
                    break;
                case 18:
                    cb.onSeekTo(((Long) msg.obj).longValue());
                    break;
                case 19:
                    cb.onSetRating((RatingCompat) msg.obj);
                    break;
                case 20:
                    cb.onCustomAction((String) msg.obj, msg.getData());
                    break;
                case 21:
                    KeyEvent keyEvent = msg.obj;
                    Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                    intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                    if (!cb.onMediaButtonEvent(intent)) {
                        onMediaButtonEvent(keyEvent, cb);
                        break;
                    }
                    break;
                case 22:
                    this.this$0.setVolumeTo(msg.arg1, 0);
                    break;
                case 23:
                    cb.onSetRepeatMode(msg.arg1);
                    break;
                case 25:
                    cb.onAddQueueItem((MediaDescriptionCompat) msg.obj);
                    break;
                case 26:
                    cb.onAddQueueItem((MediaDescriptionCompat) msg.obj, msg.arg1);
                    break;
                case 27:
                    cb.onRemoveQueueItem((MediaDescriptionCompat) msg.obj);
                    break;
                case 28:
                    if (this.this$0.mQueue != null) {
                        QueueItem item = (msg.arg1 < 0 || msg.arg1 >= this.this$0.mQueue.size()) ? null : (QueueItem) this.this$0.mQueue.get(msg.arg1);
                        if (item != null) {
                            cb.onRemoveQueueItem(item.getDescription());
                        }
                        break;
                    }
                    break;
                case 29:
                    cb.onSetCaptioningEnabled(((Boolean) msg.obj).booleanValue());
                    break;
                case 30:
                    cb.onSetShuffleMode(msg.arg1);
                    break;
                case 31:
                    cb.onSetRating((RatingCompat) msg.obj, msg.getData());
                    break;
                default:
                    break;
            }
        }
    }

    private void onMediaButtonEvent(KeyEvent ke, Callback cb) {
        if (ke != null) {
            if (ke.getAction() == 0) {
                long validActions = this.this$0.mState == null ? 0 : this.this$0.mState.getActions();
                int keyCode = ke.getKeyCode();
                if (keyCode != 79) {
                    switch (keyCode) {
                        case 85:
                            break;
                        case 86:
                            if ((validActions & 1) != 0) {
                                cb.onStop();
                                break;
                            }
                            break;
                        case 87:
                            if ((validActions & 32) != 0) {
                                cb.onSkipToNext();
                                break;
                            }
                            break;
                        case 88:
                            if ((validActions & 16) != 0) {
                                cb.onSkipToPrevious();
                                break;
                            }
                            break;
                        case 89:
                            if ((validActions & 8) != 0) {
                                cb.onRewind();
                                break;
                            }
                            break;
                        case 90:
                            if ((validActions & 64) != 0) {
                                cb.onFastForward();
                                break;
                            }
                            break;
                        default:
                            switch (keyCode) {
                                case KEYCODE_MEDIA_PLAY /*126*/:
                                    if ((validActions & 4) != 0) {
                                        cb.onPlay();
                                        break;
                                    }
                                    break;
                                case 127:
                                    if ((validActions & 2) != 0) {
                                        cb.onPause();
                                        break;
                                    }
                                    break;
                                default:
                                    break;
                            }
                    }
                }
                Log.w("MediaSessionCompat", "KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_HEADSETHOOK are handled already");
            }
        }
    }
}
