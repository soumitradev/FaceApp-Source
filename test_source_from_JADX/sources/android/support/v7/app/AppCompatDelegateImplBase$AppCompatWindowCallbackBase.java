package android.support.v7.app;

import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window.Callback;

class AppCompatDelegateImplBase$AppCompatWindowCallbackBase extends WindowCallbackWrapper {
    final /* synthetic */ AppCompatDelegateImplBase this$0;

    AppCompatDelegateImplBase$AppCompatWindowCallbackBase(AppCompatDelegateImplBase this$0, Callback callback) {
        this.this$0 = this$0;
        super(callback);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (!this.this$0.dispatchKeyEvent(event)) {
            if (!super.dispatchKeyEvent(event)) {
                return false;
            }
        }
        return true;
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        if (!super.dispatchKeyShortcutEvent(event)) {
            if (!this.this$0.onKeyShortcut(event.getKeyCode(), event)) {
                return false;
            }
        }
        return true;
    }

    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId != 0 || (menu instanceof MenuBuilder)) {
            return super.onCreatePanelMenu(featureId, menu);
        }
        return false;
    }

    public void onContentChanged() {
    }

    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        MenuBuilder mb = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
        if (featureId == 0 && mb == null) {
            return false;
        }
        if (mb != null) {
            mb.setOverrideVisibleItems(true);
        }
        boolean handled = super.onPreparePanel(featureId, view, menu);
        if (mb != null) {
            mb.setOverrideVisibleItems(false);
        }
        return handled;
    }

    public boolean onMenuOpened(int featureId, Menu menu) {
        super.onMenuOpened(featureId, menu);
        this.this$0.onMenuOpened(featureId, menu);
        return true;
    }

    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
        this.this$0.onPanelClosed(featureId, menu);
    }
}
