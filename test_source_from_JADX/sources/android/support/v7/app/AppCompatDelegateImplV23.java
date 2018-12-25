package android.support.v7.app;

import android.app.UiModeManager;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.Window;
import android.view.Window.Callback;

@RequiresApi(23)
class AppCompatDelegateImplV23 extends AppCompatDelegateImplV14 {
    private final UiModeManager mUiModeManager;

    AppCompatDelegateImplV23(Context context, Window window, AppCompatCallback callback) {
        super(context, window, callback);
        this.mUiModeManager = (UiModeManager) context.getSystemService("uimode");
    }

    Callback wrapWindowCallback(Callback callback) {
        return new AppCompatDelegateImplV23$AppCompatWindowCallbackV23(this, callback);
    }

    int mapNightMode(int mode) {
        if (mode == 0 && this.mUiModeManager.getNightMode() == 0) {
            return -1;
        }
        return super.mapNightMode(mode);
    }
}
