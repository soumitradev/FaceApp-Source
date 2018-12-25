package android.support.v7.app;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.v7.appcompat.C0034R;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.view.menu.MenuView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

protected final class AppCompatDelegateImplV9$PanelFeatureState {
    int background;
    View createdPanelView;
    ViewGroup decorView;
    int featureId;
    Bundle frozenActionViewState;
    Bundle frozenMenuState;
    int gravity;
    boolean isHandled;
    boolean isOpen;
    boolean isPrepared;
    ListMenuPresenter listMenuPresenter;
    Context listPresenterContext;
    MenuBuilder menu;
    public boolean qwertyMode;
    boolean refreshDecorView = false;
    boolean refreshMenuContent;
    View shownPanelView;
    boolean wasLastOpen;
    int windowAnimations;
    /* renamed from: x */
    int f21x;
    /* renamed from: y */
    int f22y;

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV9$PanelFeatureState$SavedState */
    private static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C02301();
        int featureId;
        boolean isOpen;
        Bundle menuState;

        /* renamed from: android.support.v7.app.AppCompatDelegateImplV9$PanelFeatureState$SavedState$1 */
        static class C02301 implements ClassLoaderCreator<SavedState> {
            C02301() {
            }

            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return SavedState.readFromParcel(in, loader);
            }

            public SavedState createFromParcel(Parcel in) {
                return SavedState.readFromParcel(in, null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        SavedState() {
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.featureId);
            dest.writeInt(this.isOpen);
            if (this.isOpen) {
                dest.writeBundle(this.menuState);
            }
        }

        static SavedState readFromParcel(Parcel source, ClassLoader loader) {
            SavedState savedState = new SavedState();
            savedState.featureId = source.readInt();
            boolean z = true;
            if (source.readInt() != 1) {
                z = false;
            }
            savedState.isOpen = z;
            if (savedState.isOpen) {
                savedState.menuState = source.readBundle(loader);
            }
            return savedState;
        }
    }

    AppCompatDelegateImplV9$PanelFeatureState(int featureId) {
        this.featureId = featureId;
    }

    public boolean hasPanelItems() {
        boolean z = false;
        if (this.shownPanelView == null) {
            return false;
        }
        if (this.createdPanelView != null) {
            return true;
        }
        if (this.listMenuPresenter.getAdapter().getCount() > 0) {
            z = true;
        }
        return z;
    }

    public void clearMenuPresenters() {
        if (this.menu != null) {
            this.menu.removeMenuPresenter(this.listMenuPresenter);
        }
        this.listMenuPresenter = null;
    }

    void setStyle(Context context) {
        TypedValue outValue = new TypedValue();
        Theme widgetTheme = context.getResources().newTheme();
        widgetTheme.setTo(context.getTheme());
        widgetTheme.resolveAttribute(C0034R.attr.actionBarPopupTheme, outValue, true);
        if (outValue.resourceId != 0) {
            widgetTheme.applyStyle(outValue.resourceId, true);
        }
        widgetTheme.resolveAttribute(C0034R.attr.panelMenuListTheme, outValue, true);
        if (outValue.resourceId != 0) {
            widgetTheme.applyStyle(outValue.resourceId, true);
        } else {
            widgetTheme.applyStyle(C0034R.style.Theme_AppCompat_CompactMenu, true);
        }
        context = new ContextThemeWrapper(context, 0);
        context.getTheme().setTo(widgetTheme);
        this.listPresenterContext = context;
        TypedArray a = context.obtainStyledAttributes(C0034R.styleable.AppCompatTheme);
        this.background = a.getResourceId(C0034R.styleable.AppCompatTheme_panelBackground, 0);
        this.windowAnimations = a.getResourceId(C0034R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
        a.recycle();
    }

    void setMenu(MenuBuilder menu) {
        if (menu != this.menu) {
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.menu = menu;
            if (!(menu == null || this.listMenuPresenter == null)) {
                menu.addMenuPresenter(this.listMenuPresenter);
            }
        }
    }

    MenuView getListMenuView(Callback cb) {
        if (this.menu == null) {
            return null;
        }
        if (this.listMenuPresenter == null) {
            this.listMenuPresenter = new ListMenuPresenter(this.listPresenterContext, C0034R.layout.abc_list_menu_item_layout);
            this.listMenuPresenter.setCallback(cb);
            this.menu.addMenuPresenter(this.listMenuPresenter);
        }
        return this.listMenuPresenter.getMenuView(this.decorView);
    }

    Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.featureId = this.featureId;
        savedState.isOpen = this.isOpen;
        if (this.menu != null) {
            savedState.menuState = new Bundle();
            this.menu.savePresenterStates(savedState.menuState);
        }
        return savedState;
    }

    void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        this.featureId = savedState.featureId;
        this.wasLastOpen = savedState.isOpen;
        this.frozenMenuState = savedState.menuState;
        this.shownPanelView = null;
        this.decorView = null;
    }

    void applyFrozenState() {
        if (this.menu != null && this.frozenMenuState != null) {
            this.menu.restorePresenterStates(this.frozenMenuState);
            this.frozenMenuState = null;
        }
    }
}
