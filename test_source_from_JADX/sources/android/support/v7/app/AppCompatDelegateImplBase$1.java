package android.support.v7.app;

import android.content.res.Resources.NotFoundException;
import java.lang.Thread.UncaughtExceptionHandler;

class AppCompatDelegateImplBase$1 implements UncaughtExceptionHandler {
    final /* synthetic */ UncaughtExceptionHandler val$defHandler;

    AppCompatDelegateImplBase$1(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.val$defHandler = uncaughtExceptionHandler;
    }

    public void uncaughtException(Thread thread, Throwable thowable) {
        if (shouldWrapException(thowable)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(thowable.getMessage());
            stringBuilder.append(". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
            Throwable wrapped = new NotFoundException(stringBuilder.toString());
            wrapped.initCause(thowable.getCause());
            wrapped.setStackTrace(thowable.getStackTrace());
            this.val$defHandler.uncaughtException(thread, wrapped);
            return;
        }
        this.val$defHandler.uncaughtException(thread, thowable);
    }

    private boolean shouldWrapException(Throwable throwable) {
        boolean z = false;
        if (!(throwable instanceof NotFoundException)) {
            return false;
        }
        String message = throwable.getMessage();
        if (message != null && (message.contains("drawable") || message.contains("Drawable"))) {
            z = true;
        }
        return z;
    }
}
