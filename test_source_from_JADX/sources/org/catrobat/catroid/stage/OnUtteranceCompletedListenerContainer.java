package org.catrobat.catroid.stage;

import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnUtteranceCompletedListenerContainer implements OnUtteranceCompletedListener {
    private final Map<String, List<OnUtteranceCompletedListener>> listeners = new HashMap();

    public synchronized boolean addOnUtteranceCompletedListener(File speechFile, OnUtteranceCompletedListener onUtteranceCompletedListener, String utteranceId) {
        List<OnUtteranceCompletedListener> utteranceIdListeners = (List) this.listeners.get(utteranceId);
        if (utteranceIdListeners != null) {
            utteranceIdListeners.add(onUtteranceCompletedListener);
            return false;
        } else if (speechFile.exists()) {
            onUtteranceCompletedListener.onUtteranceCompleted(utteranceId);
            return false;
        } else {
            ArrayList utteranceIdListeners2 = new ArrayList();
            utteranceIdListeners2.add(onUtteranceCompletedListener);
            this.listeners.put(utteranceId, utteranceIdListeners2);
            return true;
        }
    }

    public synchronized void onUtteranceCompleted(String utteranceId) {
        for (OnUtteranceCompletedListener listener : (List) this.listeners.get(utteranceId)) {
            listener.onUtteranceCompleted(utteranceId);
        }
        this.listeners.put(utteranceId, null);
    }
}
