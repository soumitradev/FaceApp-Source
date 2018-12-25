package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import java.util.concurrent.Executor;
import org.catrobat.catroid.common.BrickValues;

@Beta
public class AsyncEventBus extends EventBus {
    public AsyncEventBus(String identifier, Executor executor) {
        super(identifier, executor, Dispatcher.legacyAsync(), LoggingHandler.INSTANCE);
    }

    public AsyncEventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {
        super(BrickValues.STRING_VALUE, executor, Dispatcher.legacyAsync(), subscriberExceptionHandler);
    }

    public AsyncEventBus(Executor executor) {
        super(BrickValues.STRING_VALUE, executor, Dispatcher.legacyAsync(), LoggingHandler.INSTANCE);
    }
}
