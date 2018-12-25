package android.support.v7.app;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.Window;
import android.view.Window.Callback;

@RequiresApi(24)
class AppCompatDelegateImplN extends AppCompatDelegateImplV23 {
    AppCompatDelegateImplN(Context context, Window window, AppCompatCallback callback) {
        super(context, window, callback);
    }

    Callback wrapWindowCallback(Callback callback) {
        return new AppCompatDelegateImplN$AppCompatWindowCallbackN(this, callback);
    }
}
