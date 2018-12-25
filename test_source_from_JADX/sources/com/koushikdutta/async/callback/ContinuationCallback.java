package com.koushikdutta.async.callback;

import com.koushikdutta.async.future.Continuation;

public interface ContinuationCallback {
    void onContinue(Continuation continuation, CompletedCallback completedCallback) throws Exception;
}
