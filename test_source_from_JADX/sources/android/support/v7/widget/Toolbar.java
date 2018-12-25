package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.C0034R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.support.v7.widget.ActionMenuView.OnMenuItemClickListener;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.List;

public class Toolbar extends ViewGroup {
    private static final String TAG = "Toolbar";
    private Callback mActionMenuPresenterCallback;
    int mButtonGravity;
    ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private int mContentInsetEndWithActions;
    private int mContentInsetStartWithNavigation;
    private RtlSpacingHelper mContentInsets;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private Toolbar$ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity;
    private final ArrayList<View> mHiddenViews;
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final OnMenuItemClickListener mMenuViewItemClickListener;
    private ImageButton mNavButtonView;
    Toolbar$OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable;
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private int mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins;
    private final ArrayList<View> mTempViews;
    private int mTitleMarginBottom;
    private int mTitleMarginEnd;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, C0034R.attr.toolbarStyle);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        int i;
        super(context, attrs, defStyleAttr);
        this.mGravity = 8388627;
        this.mTempViews = new ArrayList();
        this.mHiddenViews = new ArrayList();
        this.mTempMargins = new int[2];
        this.mMenuViewItemClickListener = new Toolbar$1(this);
        this.mShowOverflowMenuRunnable = new Toolbar$2(this);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, C0034R.styleable.Toolbar, defStyleAttr, 0);
        this.mTitleTextAppearance = a.getResourceId(C0034R.styleable.Toolbar_titleTextAppearance, 0);
        this.mSubtitleTextAppearance = a.getResourceId(C0034R.styleable.Toolbar_subtitleTextAppearance, 0);
        this.mGravity = a.getInteger(C0034R.styleable.Toolbar_android_gravity, this.mGravity);
        this.mButtonGravity = a.getInteger(C0034R.styleable.Toolbar_buttonGravity, 48);
        int titleMargin = a.getDimensionPixelOffset(C0034R.styleable.Toolbar_titleMargin, 0);
        if (a.hasValue(C0034R.styleable.Toolbar_titleMargins)) {
            titleMargin = a.getDimensionPixelOffset(C0034R.styleable.Toolbar_titleMargins, titleMargin);
        }
        r0.mTitleMarginBottom = titleMargin;
        r0.mTitleMarginTop = titleMargin;
        r0.mTitleMarginEnd = titleMargin;
        r0.mTitleMarginStart = titleMargin;
        int marginStart = a.getDimensionPixelOffset(C0034R.styleable.Toolbar_titleMarginStart, -1);
        if (marginStart >= 0) {
            r0.mTitleMarginStart = marginStart;
        }
        int marginEnd = a.getDimensionPixelOffset(C0034R.styleable.Toolbar_titleMarginEnd, -1);
        if (marginEnd >= 0) {
            r0.mTitleMarginEnd = marginEnd;
        }
        int marginTop = a.getDimensionPixelOffset(C0034R.styleable.Toolbar_titleMarginTop, -1);
        if (marginTop >= 0) {
            r0.mTitleMarginTop = marginTop;
        }
        int marginBottom = a.getDimensionPixelOffset(C0034R.styleable.Toolbar_titleMarginBottom, -1);
        if (marginBottom >= 0) {
            r0.mTitleMarginBottom = marginBottom;
        }
        r0.mMaxButtonHeight = a.getDimensionPixelSize(C0034R.styleable.Toolbar_maxButtonHeight, -1);
        int contentInsetStart = a.getDimensionPixelOffset(C0034R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int contentInsetEnd = a.getDimensionPixelOffset(C0034R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int contentInsetLeft = a.getDimensionPixelSize(C0034R.styleable.Toolbar_contentInsetLeft, 0);
        int contentInsetRight = a.getDimensionPixelSize(C0034R.styleable.Toolbar_contentInsetRight, 0);
        ensureContentInsets();
        r0.mContentInsets.setAbsolute(contentInsetLeft, contentInsetRight);
        if (!(contentInsetStart == Integer.MIN_VALUE && contentInsetEnd == Integer.MIN_VALUE)) {
            r0.mContentInsets.setRelative(contentInsetStart, contentInsetEnd);
        }
        r0.mContentInsetStartWithNavigation = a.getDimensionPixelOffset(C0034R.styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        r0.mContentInsetEndWithActions = a.getDimensionPixelOffset(C0034R.styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        r0.mCollapseIcon = a.getDrawable(C0034R.styleable.Toolbar_collapseIcon);
        r0.mCollapseDescription = a.getText(C0034R.styleable.Toolbar_collapseContentDescription);
        CharSequence title = a.getText(C0034R.styleable.Toolbar_title);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        CharSequence subtitle = a.getText(C0034R.styleable.Toolbar_subtitle);
        if (!TextUtils.isEmpty(subtitle)) {
            setSubtitle(subtitle);
        }
        r0.mPopupContext = getContext();
        setPopupTheme(a.getResourceId(C0034R.styleable.Toolbar_popupTheme, 0));
        Drawable navIcon = a.getDrawable(C0034R.styleable.Toolbar_navigationIcon);
        if (navIcon != null) {
            setNavigationIcon(navIcon);
        }
        CharSequence navDesc = a.getText(C0034R.styleable.Toolbar_navigationContentDescription);
        if (!TextUtils.isEmpty(navDesc)) {
            setNavigationContentDescription(navDesc);
        }
        navIcon = a.getDrawable(C0034R.styleable.Toolbar_logo);
        if (navIcon != null) {
            setLogo(navIcon);
        }
        CharSequence logoDesc = a.getText(C0034R.styleable.Toolbar_logoDescription);
        if (!TextUtils.isEmpty(logoDesc)) {
            setLogoDescription(logoDesc);
        }
        if (a.hasValue(C0034R.styleable.Toolbar_titleTextColor)) {
            i = -1;
            setTitleTextColor(a.getColor(C0034R.styleable.Toolbar_titleTextColor, -1));
        } else {
            i = -1;
        }
        if (a.hasValue(C0034R.styleable.Toolbar_subtitleTextColor)) {
            setSubtitleTextColor(a.getColor(C0034R.styleable.Toolbar_subtitleTextColor, i));
        }
        a.recycle();
    }

    public void setPopupTheme(@StyleRes int resId) {
        if (this.mPopupTheme != resId) {
            this.mPopupTheme = resId;
            if (resId == 0) {
                this.mPopupContext = getContext();
            } else {
                this.mPopupContext = new ContextThemeWrapper(getContext(), resId);
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public void setTitleMargin(int start, int top, int end, int bottom) {
        this.mTitleMarginStart = start;
        this.mTitleMarginTop = top;
        this.mTitleMarginEnd = end;
        this.mTitleMarginBottom = bottom;
        requestLayout();
    }

    public int getTitleMarginStart() {
        return this.mTitleMarginStart;
    }

    public void setTitleMarginStart(int margin) {
        this.mTitleMarginStart = margin;
        requestLayout();
    }

    public int getTitleMarginTop() {
        return this.mTitleMarginTop;
    }

    public void setTitleMarginTop(int margin) {
        this.mTitleMarginTop = margin;
        requestLayout();
    }

    public int getTitleMarginEnd() {
        return this.mTitleMarginEnd;
    }

    public void setTitleMarginEnd(int margin) {
        this.mTitleMarginEnd = margin;
        requestLayout();
    }

    public int getTitleMarginBottom() {
        return this.mTitleMarginBottom;
    }

    public void setTitleMarginBottom(int margin) {
        this.mTitleMarginBottom = margin;
        requestLayout();
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        if (VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(layoutDirection);
        }
        ensureContentInsets();
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        boolean z = true;
        if (layoutDirection != 1) {
            z = false;
        }
        rtlSpacingHelper.setDirection(z);
    }

    public void setLogo(@DrawableRes int resId) {
        setLogo(AppCompatResources.getDrawable(getContext(), resId));
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean canShowOverflowMenu() {
        return getVisibility() == 0 && this.mMenuView != null && this.mMenuView.isOverflowReserved();
    }

    public boolean isOverflowMenuShowing() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowing();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isOverflowMenuShowPending() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.hideOverflowMenu();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setMenu(MenuBuilder menu, ActionMenuPresenter outerPresenter) {
        if (menu != null || this.mMenuView != null) {
            ensureMenuView();
            MenuBuilder oldMenu = this.mMenuView.peekMenu();
            if (oldMenu != menu) {
                if (oldMenu != null) {
                    oldMenu.removeMenuPresenter(this.mOuterActionMenuPresenter);
                    oldMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
                }
                if (this.mExpandedMenuPresenter == null) {
                    this.mExpandedMenuPresenter = new Toolbar$ExpandedActionViewMenuPresenter(this);
                }
                outerPresenter.setExpandedActionViewsExclusive(true);
                if (menu != null) {
                    menu.addMenuPresenter(outerPresenter, this.mPopupContext);
                    menu.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
                } else {
                    outerPresenter.initForMenu(this.mPopupContext, null);
                    this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
                    outerPresenter.updateMenuView(true);
                    this.mExpandedMenuPresenter.updateMenuView(true);
                }
                this.mMenuView.setPopupTheme(this.mPopupTheme);
                this.mMenuView.setPresenter(outerPresenter);
                this.mOuterActionMenuPresenter = outerPresenter;
            }
        }
    }

    public void dismissPopupMenus() {
        if (this.mMenuView != null) {
            this.mMenuView.dismissPopupMenus();
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isTitleTruncated() {
        if (this.mTitleTextView == null) {
            return false;
        }
        Layout titleLayout = this.mTitleTextView.getLayout();
        if (titleLayout == null) {
            return false;
        }
        int lineCount = titleLayout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            if (titleLayout.getEllipsisCount(i) > 0) {
                return true;
            }
        }
        return false;
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            ensureLogoView();
            if (!isChildOrHidden(this.mLogoView)) {
                addSystemView(this.mLogoView, true);
            }
        } else if (this.mLogoView != null && isChildOrHidden(this.mLogoView)) {
            removeView(this.mLogoView);
            this.mHiddenViews.remove(this.mLogoView);
        }
        if (this.mLogoView != null) {
            this.mLogoView.setImageDrawable(drawable);
        }
    }

    public Drawable getLogo() {
        return this.mLogoView != null ? this.mLogoView.getDrawable() : null;
    }

    public void setLogoDescription(@StringRes int resId) {
        setLogoDescription(getContext().getText(resId));
    }

    public void setLogoDescription(CharSequence description) {
        if (!TextUtils.isEmpty(description)) {
            ensureLogoView();
        }
        if (this.mLogoView != null) {
            this.mLogoView.setContentDescription(description);
        }
    }

    public CharSequence getLogoDescription() {
        return this.mLogoView != null ? this.mLogoView.getContentDescription() : null;
    }

    private void ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = new AppCompatImageView(getContext());
        }
    }

    public boolean hasExpandedActionView() {
        return (this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    public void collapseActionView() {
        MenuItemImpl item = this.mExpandedMenuPresenter == null ? null : this.mExpandedMenuPresenter.mCurrentExpandedItem;
        if (item != null) {
            item.collapseActionView();
        }
    }

    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (this.mTitleTextView == null) {
                Context context = getContext();
                this.mTitleTextView = new AppCompatTextView(context);
                this.mTitleTextView.setSingleLine();
                this.mTitleTextView.setEllipsize(TruncateAt.END);
                if (this.mTitleTextAppearance != 0) {
                    this.mTitleTextView.setTextAppearance(context, this.mTitleTextAppearance);
                }
                if (this.mTitleTextColor != 0) {
                    this.mTitleTextView.setTextColor(this.mTitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mTitleTextView)) {
                addSystemView(this.mTitleTextView, true);
            }
        } else if (this.mTitleTextView != null && isChildOrHidden(this.mTitleTextView)) {
            removeView(this.mTitleTextView);
            this.mHiddenViews.remove(this.mTitleTextView);
        }
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText(title);
        }
        this.mTitleText = title;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }

    public void setSubtitle(@StringRes int resId) {
        setSubtitle(getContext().getText(resId));
    }

    public void setSubtitle(CharSequence subtitle) {
        if (!TextUtils.isEmpty(subtitle)) {
            if (this.mSubtitleTextView == null) {
                Context context = getContext();
                this.mSubtitleTextView = new AppCompatTextView(context);
                this.mSubtitleTextView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TruncateAt.END);
                if (this.mSubtitleTextAppearance != 0) {
                    this.mSubtitleTextView.setTextAppearance(context, this.mSubtitleTextAppearance);
                }
                if (this.mSubtitleTextColor != 0) {
                    this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mSubtitleTextView)) {
                addSystemView(this.mSubtitleTextView, true);
            }
        } else if (this.mSubtitleTextView != null && isChildOrHidden(this.mSubtitleTextView)) {
            removeView(this.mSubtitleTextView);
            this.mHiddenViews.remove(this.mSubtitleTextView);
        }
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setText(subtitle);
        }
        this.mSubtitleText = subtitle;
    }

    public void setTitleTextAppearance(Context context, @StyleRes int resId) {
        this.mTitleTextAppearance = resId;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextAppearance(context, resId);
        }
    }

    public void setSubtitleTextAppearance(Context context, @StyleRes int resId) {
        this.mSubtitleTextAppearance = resId;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextAppearance(context, resId);
        }
    }

    public void setTitleTextColor(@ColorInt int color) {
        this.mTitleTextColor = color;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextColor(color);
        }
    }

    public void setSubtitleTextColor(@ColorInt int color) {
        this.mSubtitleTextColor = color;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextColor(color);
        }
    }

    @Nullable
    public CharSequence getNavigationContentDescription() {
        return this.mNavButtonView != null ? this.mNavButtonView.getContentDescription() : null;
    }

    public void setNavigationContentDescription(@StringRes int resId) {
        setNavigationContentDescription(resId != 0 ? getContext().getText(resId) : null);
    }

    public void setNavigationContentDescription(@Nullable CharSequence description) {
        if (!TextUtils.isEmpty(description)) {
            ensureNavButtonView();
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setContentDescription(description);
        }
    }

    public void setNavigationIcon(@DrawableRes int resId) {
        setNavigationIcon(AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setNavigationIcon(@Nullable Drawable icon) {
        if (icon != null) {
            ensureNavButtonView();
            if (!isChildOrHidden(this.mNavButtonView)) {
                addSystemView(this.mNavButtonView, true);
            }
        } else if (this.mNavButtonView != null && isChildOrHidden(this.mNavButtonView)) {
            removeView(this.mNavButtonView);
            this.mHiddenViews.remove(this.mNavButtonView);
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setImageDrawable(icon);
        }
    }

    @Nullable
    public Drawable getNavigationIcon() {
        return this.mNavButtonView != null ? this.mNavButtonView.getDrawable() : null;
    }

    public void setNavigationOnClickListener(OnClickListener listener) {
        ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(listener);
    }

    public Menu getMenu() {
        ensureMenu();
        return this.mMenuView.getMenu();
    }

    public void setOverflowIcon(@Nullable Drawable icon) {
        ensureMenu();
        this.mMenuView.setOverflowIcon(icon);
    }

    @Nullable
    public Drawable getOverflowIcon() {
        ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    private void ensureMenu() {
        ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder menu = (MenuBuilder) this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new Toolbar$ExpandedActionViewMenuPresenter(this);
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            menu.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        }
    }

    private void ensureMenuView() {
        if (this.mMenuView == null) {
            this.mMenuView = new ActionMenuView(getContext());
            this.mMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
            Toolbar$LayoutParams lp = generateDefaultLayoutParams();
            lp.gravity = GravityCompat.END | (this.mButtonGravity & 112);
            this.mMenuView.setLayoutParams(lp);
            addSystemView(this.mMenuView, false);
        }
    }

    private MenuInflater getMenuInflater() {
        return new SupportMenuInflater(getContext());
    }

    public void inflateMenu(@MenuRes int resId) {
        getMenuInflater().inflate(resId, getMenu());
    }

    public void setOnMenuItemClickListener(Toolbar$OnMenuItemClickListener listener) {
        this.mOnMenuItemClickListener = listener;
    }

    public void setContentInsetsRelative(int contentInsetStart, int contentInsetEnd) {
        ensureContentInsets();
        this.mContentInsets.setRelative(contentInsetStart, contentInsetEnd);
    }

    public int getContentInsetStart() {
        return this.mContentInsets != null ? this.mContentInsets.getStart() : 0;
    }

    public int getContentInsetEnd() {
        return this.mContentInsets != null ? this.mContentInsets.getEnd() : 0;
    }

    public void setContentInsetsAbsolute(int contentInsetLeft, int contentInsetRight) {
        ensureContentInsets();
        this.mContentInsets.setAbsolute(contentInsetLeft, contentInsetRight);
    }

    public int getContentInsetLeft() {
        return this.mContentInsets != null ? this.mContentInsets.getLeft() : 0;
    }

    public int getContentInsetRight() {
        return this.mContentInsets != null ? this.mContentInsets.getRight() : 0;
    }

    public int getContentInsetStartWithNavigation() {
        if (this.mContentInsetStartWithNavigation != Integer.MIN_VALUE) {
            return this.mContentInsetStartWithNavigation;
        }
        return getContentInsetStart();
    }

    public void setContentInsetStartWithNavigation(int insetStartWithNavigation) {
        if (insetStartWithNavigation < 0) {
            insetStartWithNavigation = Integer.MIN_VALUE;
        }
        if (insetStartWithNavigation != this.mContentInsetStartWithNavigation) {
            this.mContentInsetStartWithNavigation = insetStartWithNavigation;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getContentInsetEndWithActions() {
        if (this.mContentInsetEndWithActions != Integer.MIN_VALUE) {
            return this.mContentInsetEndWithActions;
        }
        return getContentInsetEnd();
    }

    public void setContentInsetEndWithActions(int insetEndWithActions) {
        if (insetEndWithActions < 0) {
            insetEndWithActions = Integer.MIN_VALUE;
        }
        if (insetEndWithActions != this.mContentInsetEndWithActions) {
            this.mContentInsetEndWithActions = insetEndWithActions;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getCurrentContentInsetStart() {
        if (getNavigationIcon() != null) {
            return Math.max(getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
        }
        return getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        boolean hasActions = false;
        if (this.mMenuView != null) {
            MenuBuilder mb = this.mMenuView.peekMenu();
            boolean z = mb != null && mb.hasVisibleItems();
            hasActions = z;
        }
        if (hasActions) {
            return Math.max(getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0));
        }
        return getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        if (ViewCompat.getLayoutDirection(this) == 1) {
            return getCurrentContentInsetEnd();
        }
        return getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        if (ViewCompat.getLayoutDirection(this) == 1) {
            return getCurrentContentInsetStart();
        }
        return getCurrentContentInsetEnd();
    }

    private void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new AppCompatImageButton(getContext(), null, C0034R.attr.toolbarNavigationButtonStyle);
            Toolbar$LayoutParams lp = generateDefaultLayoutParams();
            lp.gravity = 8388611 | (this.mButtonGravity & 112);
            this.mNavButtonView.setLayoutParams(lp);
        }
    }

    void ensureCollapseButtonView() {
        if (this.mCollapseButtonView == null) {
            this.mCollapseButtonView = new AppCompatImageButton(getContext(), null, C0034R.attr.toolbarNavigationButtonStyle);
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            Toolbar$LayoutParams lp = generateDefaultLayoutParams();
            lp.gravity = 8388611 | (this.mButtonGravity & 112);
            lp.mViewType = 2;
            this.mCollapseButtonView.setLayoutParams(lp);
            this.mCollapseButtonView.setOnClickListener(new Toolbar$3(this));
        }
    }

    private void addSystemView(View v, boolean allowHide) {
        Toolbar$LayoutParams lp;
        LayoutParams vlp = v.getLayoutParams();
        if (vlp == null) {
            lp = generateDefaultLayoutParams();
        } else if (checkLayoutParams(vlp)) {
            lp = (Toolbar$LayoutParams) vlp;
            lp.mViewType = 1;
            if (allowHide || this.mExpandedActionView == null) {
                addView(v, lp);
            }
            v.setLayoutParams(lp);
            this.mHiddenViews.add(v);
            return;
        } else {
            lp = generateLayoutParams(vlp);
        }
        lp.mViewType = 1;
        if (allowHide) {
        }
        addView(v, lp);
    }

    protected Parcelable onSaveInstanceState() {
        Toolbar$SavedState state = new Toolbar$SavedState(super.onSaveInstanceState());
        if (!(this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null)) {
            state.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        state.isOverflowOpen = isOverflowMenuShowing();
        return state;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Toolbar$SavedState) {
            Toolbar$SavedState ss = (Toolbar$SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            Menu menu = this.mMenuView != null ? this.mMenuView.peekMenu() : null;
            if (!(ss.expandedMenuItemId == 0 || this.mExpandedMenuPresenter == null || menu == null)) {
                MenuItem item = menu.findItem(ss.expandedMenuItemId);
                if (item != null) {
                    item.expandActionView();
                }
            }
            if (ss.isOverflowOpen) {
                postShowOverflowMenu();
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    private void postShowOverflowMenu() {
        removeCallbacks(this.mShowOverflowMenuRunnable);
        post(this.mShowOverflowMenuRunnable);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean handled = super.onTouchEvent(ev);
            if (action == 0 && !handled) {
                this.mEatingTouch = true;
            }
        }
        if (action == 1 || action == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    public boolean onHoverEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean handled = super.onHoverEvent(ev);
            if (action == 9 && !handled) {
                this.mEatingHover = true;
            }
        }
        if (action == 10 || action == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    private void measureChildConstrained(View child, int parentWidthSpec, int widthUsed, int parentHeightSpec, int heightUsed, int heightConstraint) {
        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        int childWidthSpec = getChildMeasureSpec(parentWidthSpec, (((getPaddingLeft() + getPaddingRight()) + lp.leftMargin) + lp.rightMargin) + widthUsed, lp.width);
        int childHeightSpec = getChildMeasureSpec(parentHeightSpec, (((getPaddingTop() + getPaddingBottom()) + lp.topMargin) + lp.bottomMargin) + heightUsed, lp.height);
        int childHeightMode = MeasureSpec.getMode(childHeightSpec);
        if (childHeightMode != Ints.MAX_POWER_OF_TWO && heightConstraint >= 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(childHeightMode != 0 ? Math.min(MeasureSpec.getSize(childHeightSpec), heightConstraint) : heightConstraint, Ints.MAX_POWER_OF_TWO);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private int measureChildCollapseMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed, int[] collapsingMargins) {
        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        int leftDiff = lp.leftMargin - collapsingMargins[0];
        int rightDiff = lp.rightMargin - collapsingMargins[1];
        int hMargins = Math.max(0, leftDiff) + Math.max(0, rightDiff);
        collapsingMargins[0] = Math.max(0, -leftDiff);
        collapsingMargins[1] = Math.max(0, -rightDiff);
        child.measure(getChildMeasureSpec(parentWidthMeasureSpec, ((getPaddingLeft() + getPaddingRight()) + hMargins) + widthUsed, lp.width), getChildMeasureSpec(parentHeightMeasureSpec, (((getPaddingTop() + getPaddingBottom()) + lp.topMargin) + lp.bottomMargin) + heightUsed, lp.height));
        return child.getMeasuredWidth() + hMargins;
    }

    private boolean shouldCollapse() {
        if (!this.mCollapsible) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (shouldLayout(child) && child.getMeasuredWidth() > 0 && child.getMeasuredHeight() > 0) {
                return false;
            }
        }
        return true;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int marginStartIndex;
        int marginEndIndex;
        int childState;
        int i;
        int menuWidth;
        int height = 0;
        int childState2 = 0;
        int[] collapsingMargins = this.mTempMargins;
        if (ViewUtils.isLayoutRtl(this)) {
            marginStartIndex = 1;
            marginEndIndex = 0;
        } else {
            marginStartIndex = 0;
            marginEndIndex = 1;
        }
        int marginStartIndex2 = marginStartIndex;
        int marginEndIndex2 = marginEndIndex;
        int navWidth = 0;
        if (shouldLayout(r7.mNavButtonView)) {
            measureChildConstrained(r7.mNavButtonView, widthMeasureSpec, 0, heightMeasureSpec, 0, r7.mMaxButtonHeight);
            navWidth = r7.mNavButtonView.getMeasuredWidth() + getHorizontalMargins(r7.mNavButtonView);
            height = Math.max(0, r7.mNavButtonView.getMeasuredHeight() + getVerticalMargins(r7.mNavButtonView));
            childState2 = View.combineMeasuredStates(0, r7.mNavButtonView.getMeasuredState());
        }
        if (shouldLayout(r7.mCollapseButtonView)) {
            measureChildConstrained(r7.mCollapseButtonView, widthMeasureSpec, 0, heightMeasureSpec, 0, r7.mMaxButtonHeight);
            navWidth = r7.mCollapseButtonView.getMeasuredWidth() + getHorizontalMargins(r7.mCollapseButtonView);
            height = Math.max(height, r7.mCollapseButtonView.getMeasuredHeight() + getVerticalMargins(r7.mCollapseButtonView));
            childState2 = View.combineMeasuredStates(childState2, r7.mCollapseButtonView.getMeasuredState());
        }
        int contentInsetStart = getCurrentContentInsetStart();
        int width = 0 + Math.max(contentInsetStart, navWidth);
        collapsingMargins[marginStartIndex2] = Math.max(0, contentInsetStart - navWidth);
        if (shouldLayout(r7.mMenuView)) {
            marginStartIndex2 = 0;
            measureChildConstrained(r7.mMenuView, widthMeasureSpec, width, heightMeasureSpec, 0, r7.mMaxButtonHeight);
            int menuWidth2 = r7.mMenuView.getMeasuredWidth() + getHorizontalMargins(r7.mMenuView);
            height = Math.max(height, r7.mMenuView.getMeasuredHeight() + getVerticalMargins(r7.mMenuView));
            childState = View.combineMeasuredStates(childState2, r7.mMenuView.getMeasuredState());
            childState2 = height;
            height = menuWidth2;
        } else {
            marginStartIndex2 = 0;
            childState = childState2;
            childState2 = height;
            height = 0;
        }
        int contentInsetEnd = getCurrentContentInsetEnd();
        width += Math.max(contentInsetEnd, height);
        collapsingMargins[marginEndIndex2] = Math.max(marginStartIndex2, contentInsetEnd - height);
        if (shouldLayout(r7.mExpandedActionView)) {
            marginStartIndex2 = childState;
            width += measureChildCollapseMargins(r7.mExpandedActionView, widthMeasureSpec, width, heightMeasureSpec, 0, collapsingMargins);
            childState2 = Math.max(childState2, r7.mExpandedActionView.getMeasuredHeight() + getVerticalMargins(r7.mExpandedActionView));
            marginStartIndex2 = View.combineMeasuredStates(marginStartIndex2, r7.mExpandedActionView.getMeasuredState());
        } else {
            marginStartIndex2 = childState;
        }
        if (shouldLayout(r7.mLogoView)) {
            width += measureChildCollapseMargins(r7.mLogoView, widthMeasureSpec, width, heightMeasureSpec, 0, collapsingMargins);
            childState2 = Math.max(childState2, r7.mLogoView.getMeasuredHeight() + getVerticalMargins(r7.mLogoView));
            marginStartIndex2 = View.combineMeasuredStates(marginStartIndex2, r7.mLogoView.getMeasuredState());
        }
        childState = getChildCount();
        marginStartIndex = 0;
        while (true) {
            contentInsetEnd = marginStartIndex;
            if (contentInsetEnd >= childState) {
                break;
            }
            View child = getChildAt(contentInsetEnd);
            Toolbar$LayoutParams lp = (Toolbar$LayoutParams) child.getLayoutParams();
            if (lp.mViewType != 0) {
                i = contentInsetEnd;
                menuWidth2 = childState;
                menuWidth = height;
            } else if (shouldLayout(child)) {
                menuWidth = height;
                height = child;
                i = contentInsetEnd;
                menuWidth2 = childState;
                width += measureChildCollapseMargins(child, widthMeasureSpec, width, heightMeasureSpec, 0, collapsingMargins);
                childState2 = Math.max(childState2, height.getMeasuredHeight() + getVerticalMargins(height));
                marginStartIndex2 = View.combineMeasuredStates(marginStartIndex2, height.getMeasuredState());
            } else {
                i = contentInsetEnd;
                menuWidth2 = childState;
                menuWidth = height;
            }
            marginStartIndex = i + 1;
            childState = menuWidth2;
            height = menuWidth;
        }
        menuWidth = height;
        height = 0;
        int titleHeight = 0;
        i = r7.mTitleMarginTop + r7.mTitleMarginBottom;
        int titleHorizMargins = r7.mTitleMarginStart + r7.mTitleMarginEnd;
        if (shouldLayout(r7.mTitleTextView)) {
            marginStartIndex = measureChildCollapseMargins(r7.mTitleTextView, widthMeasureSpec, width + titleHorizMargins, heightMeasureSpec, i, collapsingMargins);
            height = r7.mTitleTextView.getMeasuredWidth() + getHorizontalMargins(r7.mTitleTextView);
            titleHeight = r7.mTitleTextView.getMeasuredHeight() + getVerticalMargins(r7.mTitleTextView);
            marginStartIndex2 = View.combineMeasuredStates(marginStartIndex2, r7.mTitleTextView.getMeasuredState());
        }
        if (shouldLayout(r7.mSubtitleTextView)) {
            height = Math.max(height, measureChildCollapseMargins(r7.mSubtitleTextView, widthMeasureSpec, width + titleHorizMargins, heightMeasureSpec, titleHeight + i, collapsingMargins));
            titleHeight += r7.mSubtitleTextView.getMeasuredHeight() + getVerticalMargins(r7.mSubtitleTextView);
            marginStartIndex2 = View.combineMeasuredStates(marginStartIndex2, r7.mSubtitleTextView.getMeasuredState());
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max((width + height) + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), widthMeasureSpec, ViewCompat.MEASURED_STATE_MASK & marginStartIndex2), shouldCollapse() ? 0 : View.resolveSizeAndState(Math.max(Math.max(childState2, titleHeight) + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), heightMeasureSpec, marginStartIndex2 << 16));
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Toolbar$LayoutParams contentInsetLeft;
        int paddingRight;
        int alignmentHeight;
        int width;
        int paddingLeft;
        int leftViewsCount;
        int centerViewsCount;
        int i;
        int leftViewsCount2;
        boolean titleHasWidth;
        int left;
        Toolbar toolbar = this;
        boolean isRtl = ViewCompat.getLayoutDirection(this) == 1;
        int width2 = getWidth();
        int height = getHeight();
        int paddingLeft2 = getPaddingLeft();
        int paddingRight2 = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int left2 = paddingLeft2;
        int right = width2 - paddingRight2;
        int[] collapsingMargins = toolbar.mTempMargins;
        collapsingMargins[1] = 0;
        collapsingMargins[0] = 0;
        int minHeight = ViewCompat.getMinimumHeight(this);
        int alignmentHeight2 = minHeight >= 0 ? Math.min(minHeight, b - t) : 0;
        if (shouldLayout(toolbar.mNavButtonView)) {
            if (isRtl) {
                right = layoutChildRight(toolbar.mNavButtonView, right, collapsingMargins, alignmentHeight2);
            } else {
                left2 = layoutChildLeft(toolbar.mNavButtonView, left2, collapsingMargins, alignmentHeight2);
            }
        }
        if (shouldLayout(toolbar.mCollapseButtonView)) {
            if (isRtl) {
                right = layoutChildRight(toolbar.mCollapseButtonView, right, collapsingMargins, alignmentHeight2);
            } else {
                left2 = layoutChildLeft(toolbar.mCollapseButtonView, left2, collapsingMargins, alignmentHeight2);
            }
        }
        if (shouldLayout(toolbar.mMenuView)) {
            if (isRtl) {
                left2 = layoutChildLeft(toolbar.mMenuView, left2, collapsingMargins, alignmentHeight2);
            } else {
                right = layoutChildRight(toolbar.mMenuView, right, collapsingMargins, alignmentHeight2);
            }
        }
        int contentInsetLeft2 = getCurrentContentInsetLeft();
        int contentInsetRight = getCurrentContentInsetRight();
        collapsingMargins[0] = Math.max(0, contentInsetLeft2 - left2);
        collapsingMargins[1] = Math.max(0, contentInsetRight - ((width2 - paddingRight2) - right));
        left2 = Math.max(left2, contentInsetLeft2);
        right = Math.min(right, (width2 - paddingRight2) - contentInsetRight);
        if (shouldLayout(toolbar.mExpandedActionView)) {
            if (isRtl) {
                right = layoutChildRight(toolbar.mExpandedActionView, right, collapsingMargins, alignmentHeight2);
            } else {
                left2 = layoutChildLeft(toolbar.mExpandedActionView, left2, collapsingMargins, alignmentHeight2);
            }
        }
        if (shouldLayout(toolbar.mLogoView)) {
            if (isRtl) {
                right = layoutChildRight(toolbar.mLogoView, right, collapsingMargins, alignmentHeight2);
            } else {
                left2 = layoutChildLeft(toolbar.mLogoView, left2, collapsingMargins, alignmentHeight2);
            }
        }
        boolean layoutTitle = shouldLayout(toolbar.mTitleTextView);
        boolean layoutSubtitle = shouldLayout(toolbar.mSubtitleTextView);
        int titleHeight = 0;
        if (layoutTitle) {
            contentInsetLeft = (Toolbar$LayoutParams) toolbar.mTitleTextView.getLayoutParams();
            paddingRight = paddingRight2;
            titleHeight = 0 + ((contentInsetLeft.topMargin + toolbar.mTitleTextView.getMeasuredHeight()) + contentInsetLeft.bottomMargin);
        } else {
            paddingRight = paddingRight2;
        }
        if (layoutSubtitle) {
            contentInsetLeft = (Toolbar$LayoutParams) toolbar.mSubtitleTextView.getLayoutParams();
            titleHeight += (contentInsetLeft.topMargin + toolbar.mSubtitleTextView.getMeasuredHeight()) + contentInsetLeft.bottomMargin;
        }
        if (!layoutTitle) {
            if (!layoutSubtitle) {
                boolean z = isRtl;
                alignmentHeight = alignmentHeight2;
                width = width2;
                int i2 = height;
                paddingLeft = paddingLeft2;
                int i3 = paddingTop;
                addCustomViewsWithGravity(toolbar.mTempViews, 3);
                leftViewsCount = toolbar.mTempViews.size();
                for (alignmentHeight2 = 0; alignmentHeight2 < leftViewsCount; alignmentHeight2++) {
                    left2 = layoutChildLeft((View) toolbar.mTempViews.get(alignmentHeight2), left2, collapsingMargins, alignmentHeight);
                }
                width2 = alignmentHeight;
                addCustomViewsWithGravity(toolbar.mTempViews, 5);
                alignmentHeight2 = toolbar.mTempViews.size();
                for (contentInsetLeft2 = 0; contentInsetLeft2 < alignmentHeight2; contentInsetLeft2++) {
                    right = layoutChildRight((View) toolbar.mTempViews.get(contentInsetLeft2), right, collapsingMargins, width2);
                }
                addCustomViewsWithGravity(toolbar.mTempViews, 1);
                contentInsetLeft2 = getViewListMeasuredWidth(toolbar.mTempViews, collapsingMargins);
                paddingRight2 = (paddingLeft + (((width - paddingLeft) - paddingRight) / 2)) - (contentInsetLeft2 / 2);
                paddingTop = paddingRight2 + contentInsetLeft2;
                if (paddingRight2 >= left2) {
                    paddingRight2 = left2;
                } else if (paddingTop > right) {
                    paddingRight2 -= paddingTop - right;
                }
                centerViewsCount = toolbar.mTempViews.size();
                i = 0;
                while (true) {
                    leftViewsCount2 = leftViewsCount;
                    leftViewsCount = i;
                    if (leftViewsCount >= centerViewsCount) {
                        int rightViewsCount = alignmentHeight2;
                        paddingRight2 = layoutChildLeft((View) toolbar.mTempViews.get(leftViewsCount), paddingRight2, collapsingMargins, width2);
                        i = leftViewsCount + 1;
                        leftViewsCount = leftViewsCount2;
                        alignmentHeight2 = rightViewsCount;
                    } else {
                        toolbar.mTempViews.clear();
                        return;
                    }
                }
            }
        }
        View topChild = layoutTitle ? toolbar.mTitleTextView : toolbar.mSubtitleTextView;
        View bottomChild = layoutSubtitle ? toolbar.mSubtitleTextView : toolbar.mTitleTextView;
        Toolbar$LayoutParams toplp = (Toolbar$LayoutParams) topChild.getLayoutParams();
        contentInsetLeft = (Toolbar$LayoutParams) bottomChild.getLayoutParams();
        if (layoutTitle) {
            if (toolbar.mTitleTextView.getMeasuredWidth() <= 0) {
            }
            titleHasWidth = true;
            width = width2;
            width2 = toolbar.mGravity & 112;
            paddingLeft = paddingLeft2;
            if (width2 != 48) {
                alignmentHeight = alignmentHeight2;
                left = left2;
                alignmentHeight2 = (getPaddingTop() + toplp.topMargin) + toolbar.mTitleMarginTop;
            } else if (width2 != 80) {
                width2 = (height - paddingTop) - paddingBottom;
                paddingLeft2 = (width2 - titleHeight) / 2;
                alignmentHeight = alignmentHeight2;
                if (paddingLeft2 < toplp.topMargin + toolbar.mTitleMarginTop) {
                    paddingLeft2 = toplp.topMargin + toolbar.mTitleMarginTop;
                    left = left2;
                } else {
                    alignmentHeight2 = (((height - paddingBottom) - titleHeight) - paddingLeft2) - paddingTop;
                    left = left2;
                    if (alignmentHeight2 < toplp.bottomMargin + toolbar.mTitleMarginBottom) {
                        paddingLeft2 = Math.max(0, paddingLeft2 - ((contentInsetLeft.bottomMargin + toolbar.mTitleMarginBottom) - alignmentHeight2));
                    }
                }
                alignmentHeight2 = paddingTop + paddingLeft2;
            } else {
                alignmentHeight = alignmentHeight2;
                left = left2;
                alignmentHeight2 = (((height - paddingBottom) - contentInsetLeft.bottomMargin) - toolbar.mTitleMarginBottom) - titleHeight;
            }
            if (isRtl) {
                width2 = (titleHasWidth ? toolbar.mTitleMarginStart : 0) - collapsingMargins[1];
                right -= Math.max(0, width2);
                collapsingMargins[1] = Math.max(0, -width2);
                isRtl = right;
                paddingLeft2 = right;
                if (layoutTitle) {
                    Toolbar$LayoutParams lp = (Toolbar$LayoutParams) toolbar.mTitleTextView.getLayoutParams();
                    contentInsetLeft = isRtl - toolbar.mTitleTextView.getMeasuredWidth();
                    width2 = toolbar.mTitleTextView.getMeasuredHeight() + alignmentHeight2;
                    toolbar.mTitleTextView.layout(contentInsetLeft, alignmentHeight2, isRtl, width2);
                    isRtl = contentInsetLeft - toolbar.mTitleMarginEnd;
                    alignmentHeight2 = width2 + lp.bottomMargin;
                } else {
                    int i4 = width2;
                    i2 = height;
                }
                if (layoutSubtitle) {
                    contentInsetLeft = (Toolbar$LayoutParams) toolbar.mSubtitleTextView.getLayoutParams();
                    alignmentHeight2 += contentInsetLeft.topMargin;
                    height = toolbar.mSubtitleTextView.getMeasuredHeight() + alignmentHeight2;
                    toolbar.mSubtitleTextView.layout(paddingLeft2 - toolbar.mSubtitleTextView.getMeasuredWidth(), alignmentHeight2, paddingLeft2, height);
                    paddingLeft2 -= toolbar.mTitleMarginEnd;
                    alignmentHeight2 = height + contentInsetLeft.bottomMargin;
                }
                if (titleHasWidth) {
                    right = Math.min(isRtl, paddingLeft2);
                }
                i3 = paddingTop;
                left2 = left;
            } else {
                Toolbar$LayoutParams toolbar$LayoutParams = contentInsetLeft;
                i2 = height;
                contentInsetLeft2 = (titleHasWidth ? toolbar.mTitleMarginStart : 0) - collapsingMargins[0];
                left2 = left + Math.max(0, contentInsetLeft2);
                collapsingMargins[0] = Math.max(0, -contentInsetLeft2);
                width2 = left2;
                height = left2;
                if (layoutTitle) {
                    Toolbar$LayoutParams lp2 = (Toolbar$LayoutParams) toolbar.mTitleTextView.getLayoutParams();
                    leftViewsCount = toolbar.mTitleTextView.getMeasuredWidth() + width2;
                    contentInsetLeft2 = toolbar.mTitleTextView.getMeasuredHeight() + alignmentHeight2;
                    toolbar.mTitleTextView.layout(width2, alignmentHeight2, leftViewsCount, contentInsetLeft2);
                    width2 = leftViewsCount + toolbar.mTitleMarginEnd;
                    alignmentHeight2 = contentInsetLeft2 + lp2.bottomMargin;
                } else {
                    i3 = paddingTop;
                }
                if (layoutSubtitle) {
                    Toolbar$LayoutParams lp3 = (Toolbar$LayoutParams) toolbar.mSubtitleTextView.getLayoutParams();
                    alignmentHeight2 += lp3.topMargin;
                    contentInsetLeft2 = toolbar.mSubtitleTextView.getMeasuredWidth() + height;
                    paddingLeft2 = toolbar.mSubtitleTextView.getMeasuredHeight() + alignmentHeight2;
                    toolbar.mSubtitleTextView.layout(height, alignmentHeight2, contentInsetLeft2, paddingLeft2);
                    height = contentInsetLeft2 + toolbar.mTitleMarginEnd;
                    alignmentHeight2 = paddingLeft2 + lp3.bottomMargin;
                }
                if (titleHasWidth) {
                    left2 = Math.max(width2, height);
                }
            }
            addCustomViewsWithGravity(toolbar.mTempViews, 3);
            leftViewsCount = toolbar.mTempViews.size();
            for (alignmentHeight2 = 0; alignmentHeight2 < leftViewsCount; alignmentHeight2++) {
                left2 = layoutChildLeft((View) toolbar.mTempViews.get(alignmentHeight2), left2, collapsingMargins, alignmentHeight);
            }
            width2 = alignmentHeight;
            addCustomViewsWithGravity(toolbar.mTempViews, 5);
            alignmentHeight2 = toolbar.mTempViews.size();
            for (contentInsetLeft2 = 0; contentInsetLeft2 < alignmentHeight2; contentInsetLeft2++) {
                right = layoutChildRight((View) toolbar.mTempViews.get(contentInsetLeft2), right, collapsingMargins, width2);
            }
            addCustomViewsWithGravity(toolbar.mTempViews, 1);
            contentInsetLeft2 = getViewListMeasuredWidth(toolbar.mTempViews, collapsingMargins);
            paddingRight2 = (paddingLeft + (((width - paddingLeft) - paddingRight) / 2)) - (contentInsetLeft2 / 2);
            paddingTop = paddingRight2 + contentInsetLeft2;
            if (paddingRight2 >= left2) {
                paddingRight2 = left2;
            } else if (paddingTop > right) {
                paddingRight2 -= paddingTop - right;
            }
            centerViewsCount = toolbar.mTempViews.size();
            i = 0;
            while (true) {
                leftViewsCount2 = leftViewsCount;
                leftViewsCount = i;
                if (leftViewsCount >= centerViewsCount) {
                    toolbar.mTempViews.clear();
                    return;
                }
                int rightViewsCount2 = alignmentHeight2;
                paddingRight2 = layoutChildLeft((View) toolbar.mTempViews.get(leftViewsCount), paddingRight2, collapsingMargins, width2);
                i = leftViewsCount + 1;
                leftViewsCount = leftViewsCount2;
                alignmentHeight2 = rightViewsCount2;
            }
        }
        if (!layoutSubtitle || toolbar.mSubtitleTextView.getMeasuredWidth() <= 0) {
            titleHasWidth = false;
            width = width2;
            width2 = toolbar.mGravity & 112;
            paddingLeft = paddingLeft2;
            if (width2 != 48) {
                alignmentHeight = alignmentHeight2;
                left = left2;
                alignmentHeight2 = (getPaddingTop() + toplp.topMargin) + toolbar.mTitleMarginTop;
            } else if (width2 != 80) {
                alignmentHeight = alignmentHeight2;
                left = left2;
                alignmentHeight2 = (((height - paddingBottom) - contentInsetLeft.bottomMargin) - toolbar.mTitleMarginBottom) - titleHeight;
            } else {
                width2 = (height - paddingTop) - paddingBottom;
                paddingLeft2 = (width2 - titleHeight) / 2;
                alignmentHeight = alignmentHeight2;
                if (paddingLeft2 < toplp.topMargin + toolbar.mTitleMarginTop) {
                    alignmentHeight2 = (((height - paddingBottom) - titleHeight) - paddingLeft2) - paddingTop;
                    left = left2;
                    if (alignmentHeight2 < toplp.bottomMargin + toolbar.mTitleMarginBottom) {
                        paddingLeft2 = Math.max(0, paddingLeft2 - ((contentInsetLeft.bottomMargin + toolbar.mTitleMarginBottom) - alignmentHeight2));
                    }
                } else {
                    paddingLeft2 = toplp.topMargin + toolbar.mTitleMarginTop;
                    left = left2;
                }
                alignmentHeight2 = paddingTop + paddingLeft2;
            }
            if (isRtl) {
                Toolbar$LayoutParams toolbar$LayoutParams2 = contentInsetLeft;
                i2 = height;
                if (titleHasWidth) {
                }
                contentInsetLeft2 = (titleHasWidth ? toolbar.mTitleMarginStart : 0) - collapsingMargins[0];
                left2 = left + Math.max(0, contentInsetLeft2);
                collapsingMargins[0] = Math.max(0, -contentInsetLeft2);
                width2 = left2;
                height = left2;
                if (layoutTitle) {
                    i3 = paddingTop;
                } else {
                    Toolbar$LayoutParams lp22 = (Toolbar$LayoutParams) toolbar.mTitleTextView.getLayoutParams();
                    leftViewsCount = toolbar.mTitleTextView.getMeasuredWidth() + width2;
                    contentInsetLeft2 = toolbar.mTitleTextView.getMeasuredHeight() + alignmentHeight2;
                    toolbar.mTitleTextView.layout(width2, alignmentHeight2, leftViewsCount, contentInsetLeft2);
                    width2 = leftViewsCount + toolbar.mTitleMarginEnd;
                    alignmentHeight2 = contentInsetLeft2 + lp22.bottomMargin;
                }
                if (layoutSubtitle) {
                    Toolbar$LayoutParams lp32 = (Toolbar$LayoutParams) toolbar.mSubtitleTextView.getLayoutParams();
                    alignmentHeight2 += lp32.topMargin;
                    contentInsetLeft2 = toolbar.mSubtitleTextView.getMeasuredWidth() + height;
                    paddingLeft2 = toolbar.mSubtitleTextView.getMeasuredHeight() + alignmentHeight2;
                    toolbar.mSubtitleTextView.layout(height, alignmentHeight2, contentInsetLeft2, paddingLeft2);
                    height = contentInsetLeft2 + toolbar.mTitleMarginEnd;
                    alignmentHeight2 = paddingLeft2 + lp32.bottomMargin;
                }
                if (titleHasWidth) {
                    left2 = Math.max(width2, height);
                }
            } else {
                if (titleHasWidth) {
                }
                width2 = (titleHasWidth ? toolbar.mTitleMarginStart : 0) - collapsingMargins[1];
                right -= Math.max(0, width2);
                collapsingMargins[1] = Math.max(0, -width2);
                isRtl = right;
                paddingLeft2 = right;
                if (layoutTitle) {
                    int i42 = width2;
                    i2 = height;
                } else {
                    Toolbar$LayoutParams lp4 = (Toolbar$LayoutParams) toolbar.mTitleTextView.getLayoutParams();
                    contentInsetLeft = isRtl - toolbar.mTitleTextView.getMeasuredWidth();
                    width2 = toolbar.mTitleTextView.getMeasuredHeight() + alignmentHeight2;
                    toolbar.mTitleTextView.layout(contentInsetLeft, alignmentHeight2, isRtl, width2);
                    isRtl = contentInsetLeft - toolbar.mTitleMarginEnd;
                    alignmentHeight2 = width2 + lp4.bottomMargin;
                }
                if (layoutSubtitle) {
                    contentInsetLeft = (Toolbar$LayoutParams) toolbar.mSubtitleTextView.getLayoutParams();
                    alignmentHeight2 += contentInsetLeft.topMargin;
                    height = toolbar.mSubtitleTextView.getMeasuredHeight() + alignmentHeight2;
                    toolbar.mSubtitleTextView.layout(paddingLeft2 - toolbar.mSubtitleTextView.getMeasuredWidth(), alignmentHeight2, paddingLeft2, height);
                    paddingLeft2 -= toolbar.mTitleMarginEnd;
                    alignmentHeight2 = height + contentInsetLeft.bottomMargin;
                }
                if (titleHasWidth) {
                    right = Math.min(isRtl, paddingLeft2);
                }
                i3 = paddingTop;
                left2 = left;
            }
            addCustomViewsWithGravity(toolbar.mTempViews, 3);
            leftViewsCount = toolbar.mTempViews.size();
            for (alignmentHeight2 = 0; alignmentHeight2 < leftViewsCount; alignmentHeight2++) {
                left2 = layoutChildLeft((View) toolbar.mTempViews.get(alignmentHeight2), left2, collapsingMargins, alignmentHeight);
            }
            width2 = alignmentHeight;
            addCustomViewsWithGravity(toolbar.mTempViews, 5);
            alignmentHeight2 = toolbar.mTempViews.size();
            for (contentInsetLeft2 = 0; contentInsetLeft2 < alignmentHeight2; contentInsetLeft2++) {
                right = layoutChildRight((View) toolbar.mTempViews.get(contentInsetLeft2), right, collapsingMargins, width2);
            }
            addCustomViewsWithGravity(toolbar.mTempViews, 1);
            contentInsetLeft2 = getViewListMeasuredWidth(toolbar.mTempViews, collapsingMargins);
            paddingRight2 = (paddingLeft + (((width - paddingLeft) - paddingRight) / 2)) - (contentInsetLeft2 / 2);
            paddingTop = paddingRight2 + contentInsetLeft2;
            if (paddingRight2 >= left2) {
                paddingRight2 = left2;
            } else if (paddingTop > right) {
                paddingRight2 -= paddingTop - right;
            }
            centerViewsCount = toolbar.mTempViews.size();
            i = 0;
            while (true) {
                leftViewsCount2 = leftViewsCount;
                leftViewsCount = i;
                if (leftViewsCount >= centerViewsCount) {
                    int rightViewsCount22 = alignmentHeight2;
                    paddingRight2 = layoutChildLeft((View) toolbar.mTempViews.get(leftViewsCount), paddingRight2, collapsingMargins, width2);
                    i = leftViewsCount + 1;
                    leftViewsCount = leftViewsCount2;
                    alignmentHeight2 = rightViewsCount22;
                } else {
                    toolbar.mTempViews.clear();
                    return;
                }
            }
        }
        titleHasWidth = true;
        width = width2;
        width2 = toolbar.mGravity & 112;
        paddingLeft = paddingLeft2;
        if (width2 != 48) {
            alignmentHeight = alignmentHeight2;
            left = left2;
            alignmentHeight2 = (getPaddingTop() + toplp.topMargin) + toolbar.mTitleMarginTop;
        } else if (width2 != 80) {
            width2 = (height - paddingTop) - paddingBottom;
            paddingLeft2 = (width2 - titleHeight) / 2;
            alignmentHeight = alignmentHeight2;
            if (paddingLeft2 < toplp.topMargin + toolbar.mTitleMarginTop) {
                paddingLeft2 = toplp.topMargin + toolbar.mTitleMarginTop;
                left = left2;
            } else {
                alignmentHeight2 = (((height - paddingBottom) - titleHeight) - paddingLeft2) - paddingTop;
                left = left2;
                if (alignmentHeight2 < toplp.bottomMargin + toolbar.mTitleMarginBottom) {
                    paddingLeft2 = Math.max(0, paddingLeft2 - ((contentInsetLeft.bottomMargin + toolbar.mTitleMarginBottom) - alignmentHeight2));
                }
            }
            alignmentHeight2 = paddingTop + paddingLeft2;
        } else {
            alignmentHeight = alignmentHeight2;
            left = left2;
            alignmentHeight2 = (((height - paddingBottom) - contentInsetLeft.bottomMargin) - toolbar.mTitleMarginBottom) - titleHeight;
        }
        if (isRtl) {
            if (titleHasWidth) {
            }
            width2 = (titleHasWidth ? toolbar.mTitleMarginStart : 0) - collapsingMargins[1];
            right -= Math.max(0, width2);
            collapsingMargins[1] = Math.max(0, -width2);
            isRtl = right;
            paddingLeft2 = right;
            if (layoutTitle) {
                Toolbar$LayoutParams lp42 = (Toolbar$LayoutParams) toolbar.mTitleTextView.getLayoutParams();
                contentInsetLeft = isRtl - toolbar.mTitleTextView.getMeasuredWidth();
                width2 = toolbar.mTitleTextView.getMeasuredHeight() + alignmentHeight2;
                toolbar.mTitleTextView.layout(contentInsetLeft, alignmentHeight2, isRtl, width2);
                isRtl = contentInsetLeft - toolbar.mTitleMarginEnd;
                alignmentHeight2 = width2 + lp42.bottomMargin;
            } else {
                int i422 = width2;
                i2 = height;
            }
            if (layoutSubtitle) {
                contentInsetLeft = (Toolbar$LayoutParams) toolbar.mSubtitleTextView.getLayoutParams();
                alignmentHeight2 += contentInsetLeft.topMargin;
                height = toolbar.mSubtitleTextView.getMeasuredHeight() + alignmentHeight2;
                toolbar.mSubtitleTextView.layout(paddingLeft2 - toolbar.mSubtitleTextView.getMeasuredWidth(), alignmentHeight2, paddingLeft2, height);
                paddingLeft2 -= toolbar.mTitleMarginEnd;
                alignmentHeight2 = height + contentInsetLeft.bottomMargin;
            }
            if (titleHasWidth) {
                right = Math.min(isRtl, paddingLeft2);
            }
            i3 = paddingTop;
            left2 = left;
        } else {
            Toolbar$LayoutParams toolbar$LayoutParams22 = contentInsetLeft;
            i2 = height;
            if (titleHasWidth) {
            }
            contentInsetLeft2 = (titleHasWidth ? toolbar.mTitleMarginStart : 0) - collapsingMargins[0];
            left2 = left + Math.max(0, contentInsetLeft2);
            collapsingMargins[0] = Math.max(0, -contentInsetLeft2);
            width2 = left2;
            height = left2;
            if (layoutTitle) {
                Toolbar$LayoutParams lp222 = (Toolbar$LayoutParams) toolbar.mTitleTextView.getLayoutParams();
                leftViewsCount = toolbar.mTitleTextView.getMeasuredWidth() + width2;
                contentInsetLeft2 = toolbar.mTitleTextView.getMeasuredHeight() + alignmentHeight2;
                toolbar.mTitleTextView.layout(width2, alignmentHeight2, leftViewsCount, contentInsetLeft2);
                width2 = leftViewsCount + toolbar.mTitleMarginEnd;
                alignmentHeight2 = contentInsetLeft2 + lp222.bottomMargin;
            } else {
                i3 = paddingTop;
            }
            if (layoutSubtitle) {
                Toolbar$LayoutParams lp322 = (Toolbar$LayoutParams) toolbar.mSubtitleTextView.getLayoutParams();
                alignmentHeight2 += lp322.topMargin;
                contentInsetLeft2 = toolbar.mSubtitleTextView.getMeasuredWidth() + height;
                paddingLeft2 = toolbar.mSubtitleTextView.getMeasuredHeight() + alignmentHeight2;
                toolbar.mSubtitleTextView.layout(height, alignmentHeight2, contentInsetLeft2, paddingLeft2);
                height = contentInsetLeft2 + toolbar.mTitleMarginEnd;
                alignmentHeight2 = paddingLeft2 + lp322.bottomMargin;
            }
            if (titleHasWidth) {
                left2 = Math.max(width2, height);
            }
        }
        addCustomViewsWithGravity(toolbar.mTempViews, 3);
        leftViewsCount = toolbar.mTempViews.size();
        for (alignmentHeight2 = 0; alignmentHeight2 < leftViewsCount; alignmentHeight2++) {
            left2 = layoutChildLeft((View) toolbar.mTempViews.get(alignmentHeight2), left2, collapsingMargins, alignmentHeight);
        }
        width2 = alignmentHeight;
        addCustomViewsWithGravity(toolbar.mTempViews, 5);
        alignmentHeight2 = toolbar.mTempViews.size();
        for (contentInsetLeft2 = 0; contentInsetLeft2 < alignmentHeight2; contentInsetLeft2++) {
            right = layoutChildRight((View) toolbar.mTempViews.get(contentInsetLeft2), right, collapsingMargins, width2);
        }
        addCustomViewsWithGravity(toolbar.mTempViews, 1);
        contentInsetLeft2 = getViewListMeasuredWidth(toolbar.mTempViews, collapsingMargins);
        paddingRight2 = (paddingLeft + (((width - paddingLeft) - paddingRight) / 2)) - (contentInsetLeft2 / 2);
        paddingTop = paddingRight2 + contentInsetLeft2;
        if (paddingRight2 >= left2) {
            paddingRight2 = left2;
        } else if (paddingTop > right) {
            paddingRight2 -= paddingTop - right;
        }
        centerViewsCount = toolbar.mTempViews.size();
        i = 0;
        while (true) {
            leftViewsCount2 = leftViewsCount;
            leftViewsCount = i;
            if (leftViewsCount >= centerViewsCount) {
                toolbar.mTempViews.clear();
                return;
            }
            int rightViewsCount222 = alignmentHeight2;
            paddingRight2 = layoutChildLeft((View) toolbar.mTempViews.get(leftViewsCount), paddingRight2, collapsingMargins, width2);
            i = leftViewsCount + 1;
            leftViewsCount = leftViewsCount2;
            alignmentHeight2 = rightViewsCount222;
        }
    }

    private int getViewListMeasuredWidth(List<View> views, int[] collapsingMargins) {
        int collapseLeft = collapsingMargins[0];
        int collapseRight = collapsingMargins[1];
        int count = views.size();
        int width = 0;
        int collapseRight2 = collapseRight;
        collapseRight = collapseLeft;
        for (collapseLeft = 0; collapseLeft < count; collapseLeft++) {
            View v = (View) views.get(collapseLeft);
            Toolbar$LayoutParams lp = (Toolbar$LayoutParams) v.getLayoutParams();
            int l = lp.leftMargin - collapseRight;
            int r = lp.rightMargin - collapseRight2;
            int leftMargin = Math.max(0, l);
            int rightMargin = Math.max(0, r);
            collapseRight = Math.max(0, -l);
            collapseRight2 = Math.max(0, -r);
            width += (v.getMeasuredWidth() + leftMargin) + rightMargin;
        }
        return width;
    }

    private int layoutChildLeft(View child, int left, int[] collapsingMargins, int alignmentHeight) {
        Toolbar$LayoutParams lp = (Toolbar$LayoutParams) child.getLayoutParams();
        int l = lp.leftMargin - collapsingMargins[0];
        left += Math.max(0, l);
        collapsingMargins[0] = Math.max(0, -l);
        int top = getChildTop(child, alignmentHeight);
        int childWidth = child.getMeasuredWidth();
        child.layout(left, top, left + childWidth, child.getMeasuredHeight() + top);
        return left + (lp.rightMargin + childWidth);
    }

    private int layoutChildRight(View child, int right, int[] collapsingMargins, int alignmentHeight) {
        Toolbar$LayoutParams lp = (Toolbar$LayoutParams) child.getLayoutParams();
        int r = lp.rightMargin - collapsingMargins[1];
        right -= Math.max(0, r);
        collapsingMargins[1] = Math.max(0, -r);
        int top = getChildTop(child, alignmentHeight);
        int childWidth = child.getMeasuredWidth();
        child.layout(right - childWidth, top, right, child.getMeasuredHeight() + top);
        return right - (lp.leftMargin + childWidth);
    }

    private int getChildTop(View child, int alignmentHeight) {
        Toolbar$LayoutParams lp = (Toolbar$LayoutParams) child.getLayoutParams();
        int childHeight = child.getMeasuredHeight();
        int alignmentOffset = alignmentHeight > 0 ? (childHeight - alignmentHeight) / 2 : 0;
        int childVerticalGravity = getChildVerticalGravity(lp.gravity);
        if (childVerticalGravity == 48) {
            return getPaddingTop() - alignmentOffset;
        }
        if (childVerticalGravity == 80) {
            return (((getHeight() - getPaddingBottom()) - childHeight) - lp.bottomMargin) - alignmentOffset;
        }
        childVerticalGravity = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int spaceAbove = (((height - childVerticalGravity) - paddingBottom) - childHeight) / 2;
        if (spaceAbove < lp.topMargin) {
            spaceAbove = lp.topMargin;
        } else {
            int spaceBelow = (((height - paddingBottom) - childHeight) - spaceAbove) - childVerticalGravity;
            if (spaceBelow < lp.bottomMargin) {
                spaceAbove = Math.max(0, spaceAbove - (lp.bottomMargin - spaceBelow));
            }
        }
        return childVerticalGravity + spaceAbove;
    }

    private int getChildVerticalGravity(int gravity) {
        int vgrav = gravity & 112;
        if (vgrav == 16 || vgrav == 48 || vgrav == 80) {
            return vgrav;
        }
        return this.mGravity & 112;
    }

    private void addCustomViewsWithGravity(List<View> views, int gravity) {
        boolean z = true;
        if (ViewCompat.getLayoutDirection(this) != 1) {
            z = false;
        }
        boolean isRtl = z;
        int childCount = getChildCount();
        int absGrav = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this));
        views.clear();
        int i;
        View child;
        Toolbar$LayoutParams lp;
        if (isRtl) {
            for (i = childCount - 1; i >= 0; i--) {
                child = getChildAt(i);
                lp = (Toolbar$LayoutParams) child.getLayoutParams();
                if (lp.mViewType == 0 && shouldLayout(child) && getChildHorizontalGravity(lp.gravity) == absGrav) {
                    views.add(child);
                }
            }
            return;
        }
        for (i = 0; i < childCount; i++) {
            child = getChildAt(i);
            lp = (Toolbar$LayoutParams) child.getLayoutParams();
            if (lp.mViewType == 0 && shouldLayout(child) && getChildHorizontalGravity(lp.gravity) == absGrav) {
                views.add(child);
            }
        }
    }

    private int getChildHorizontalGravity(int gravity) {
        int ld = ViewCompat.getLayoutDirection(this);
        int hGrav = GravityCompat.getAbsoluteGravity(gravity, ld) & 7;
        if (hGrav != 1) {
            int i = 3;
            if (!(hGrav == 3 || hGrav == 5)) {
                if (ld == 1) {
                    i = 5;
                }
                return i;
            }
        }
        return hGrav;
    }

    private boolean shouldLayout(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    private int getHorizontalMargins(View v) {
        MarginLayoutParams mlp = (MarginLayoutParams) v.getLayoutParams();
        return MarginLayoutParamsCompat.getMarginStart(mlp) + MarginLayoutParamsCompat.getMarginEnd(mlp);
    }

    private int getVerticalMargins(View v) {
        MarginLayoutParams mlp = (MarginLayoutParams) v.getLayoutParams();
        return mlp.topMargin + mlp.bottomMargin;
    }

    public Toolbar$LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new Toolbar$LayoutParams(getContext(), attrs);
    }

    protected Toolbar$LayoutParams generateLayoutParams(LayoutParams p) {
        if (p instanceof Toolbar$LayoutParams) {
            return new Toolbar$LayoutParams((Toolbar$LayoutParams) p);
        }
        if (p instanceof ActionBar.LayoutParams) {
            return new Toolbar$LayoutParams((ActionBar.LayoutParams) p);
        }
        if (p instanceof MarginLayoutParams) {
            return new Toolbar$LayoutParams((MarginLayoutParams) p);
        }
        return new Toolbar$LayoutParams(p);
    }

    protected Toolbar$LayoutParams generateDefaultLayoutParams() {
        return new Toolbar$LayoutParams(-2, -2);
    }

    protected boolean checkLayoutParams(LayoutParams p) {
        return super.checkLayoutParams(p) && (p instanceof Toolbar$LayoutParams);
    }

    private static boolean isCustomView(View child) {
        return ((Toolbar$LayoutParams) child.getLayoutParams()).mViewType == 0;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public DecorToolbar getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }

    void removeChildrenForExpandedActionView() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (!(((Toolbar$LayoutParams) child.getLayoutParams()).mViewType == 2 || child == this.mMenuView)) {
                removeViewAt(i);
                this.mHiddenViews.add(child);
            }
        }
    }

    void addChildrenForExpandedActionView() {
        for (int i = this.mHiddenViews.size() - 1; i >= 0; i--) {
            addView((View) this.mHiddenViews.get(i));
        }
        this.mHiddenViews.clear();
    }

    private boolean isChildOrHidden(View child) {
        if (child.getParent() != this) {
            if (!this.mHiddenViews.contains(child)) {
                return false;
            }
        }
        return true;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setCollapsible(boolean collapsible) {
        this.mCollapsible = collapsible;
        requestLayout();
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setMenuCallbacks(Callback pcb, MenuBuilder.Callback mcb) {
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
        if (this.mMenuView != null) {
            this.mMenuView.setMenuCallbacks(pcb, mcb);
        }
    }

    private void ensureContentInsets() {
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
    }

    ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.mOuterActionMenuPresenter;
    }

    Context getPopupContext() {
        return this.mPopupContext;
    }
}
