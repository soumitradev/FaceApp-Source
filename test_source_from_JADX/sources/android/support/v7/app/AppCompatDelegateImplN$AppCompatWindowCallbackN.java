package android.support.v7.app;

import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.Window.Callback;
import java.util.List;

class AppCompatDelegateImplN$AppCompatWindowCallbackN extends AppCompatDelegateImplV23$AppCompatWindowCallbackV23 {
    final /* synthetic */ AppCompatDelegateImplN this$0;

    AppCompatDelegateImplN$AppCompatWindowCallbackN(AppCompatDelegateImplN this$0, Callback callback) {
        this.this$0 = this$0;
        super(this$0, callback);
    }

    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
        AppCompatDelegateImplV9$PanelFeatureState panel = this.this$0.getPanelState(0, true);
        if (panel == null || panel.menu == null) {
            super.onProvideKeyboardShortcuts(data, menu, deviceId);
        } else {
            super.onProvideKeyboardShortcuts(data, panel.menu, deviceId);
        }
    }
}
