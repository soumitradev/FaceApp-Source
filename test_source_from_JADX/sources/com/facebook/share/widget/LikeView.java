package com.facebook.share.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.C0410R;
import com.facebook.FacebookException;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.internal.LikeActionController.CreationCallback;
import com.facebook.share.internal.LikeBoxCountView;
import com.facebook.share.internal.LikeBoxCountView.LikeBoxCountViewCaretPosition;
import com.facebook.share.internal.LikeButton;

public class LikeView extends FrameLayout {
    private static final int NO_FOREGROUND_COLOR = -1;
    private AuxiliaryViewPosition auxiliaryViewPosition = AuxiliaryViewPosition.DEFAULT;
    private BroadcastReceiver broadcastReceiver;
    private LinearLayout containerView;
    private LikeActionControllerCreationCallback creationCallback;
    private int edgePadding;
    private boolean explicitlyDisabled;
    private int foregroundColor = -1;
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.DEFAULT;
    private int internalPadding;
    private LikeActionController likeActionController;
    private LikeBoxCountView likeBoxCountView;
    private LikeButton likeButton;
    private Style likeViewStyle = Style.DEFAULT;
    private String objectId;
    private ObjectType objectType;
    private OnErrorListener onErrorListener;
    private FragmentWrapper parentFragment;
    private TextView socialSentenceView;

    /* renamed from: com.facebook.share.widget.LikeView$1 */
    class C05021 implements OnClickListener {
        C05021() {
        }

        public void onClick(View v) {
            LikeView.this.toggleLike();
        }
    }

    public enum AuxiliaryViewPosition {
        BOTTOM("bottom", 0),
        INLINE("inline", 1),
        TOP("top", 2);
        
        static AuxiliaryViewPosition DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = BOTTOM;
        }

        static AuxiliaryViewPosition fromInt(int enumValue) {
            for (AuxiliaryViewPosition auxViewPosition : values()) {
                if (auxViewPosition.getValue() == enumValue) {
                    return auxViewPosition;
                }
            }
            return null;
        }

        private AuxiliaryViewPosition(String stringValue, int value) {
            this.stringValue = stringValue;
            this.intValue = value;
        }

        public String toString() {
            return this.stringValue;
        }

        private int getValue() {
            return this.intValue;
        }
    }

    public enum HorizontalAlignment {
        CENTER("center", 0),
        LEFT("left", 1),
        RIGHT("right", 2);
        
        static HorizontalAlignment DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = CENTER;
        }

        static HorizontalAlignment fromInt(int enumValue) {
            for (HorizontalAlignment horizontalAlignment : values()) {
                if (horizontalAlignment.getValue() == enumValue) {
                    return horizontalAlignment;
                }
            }
            return null;
        }

        private HorizontalAlignment(String stringValue, int value) {
            this.stringValue = stringValue;
            this.intValue = value;
        }

        public String toString() {
            return this.stringValue;
        }

        private int getValue() {
            return this.intValue;
        }
    }

    private class LikeControllerBroadcastReceiver extends BroadcastReceiver {
        private LikeControllerBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            Bundle extras = intent.getExtras();
            boolean shouldRespond = true;
            if (extras != null) {
                boolean z;
                String broadcastObjectId = extras.getString(LikeActionController.ACTION_OBJECT_ID_KEY);
                if (!Utility.isNullOrEmpty(broadcastObjectId)) {
                    if (!Utility.areObjectsEqual(LikeView.this.objectId, broadcastObjectId)) {
                        z = false;
                        shouldRespond = z;
                    }
                }
                z = true;
                shouldRespond = z;
            }
            if (shouldRespond) {
                if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED.equals(intentAction)) {
                    LikeView.this.updateLikeStateAndLayout();
                } else if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR.equals(intentAction)) {
                    if (LikeView.this.onErrorListener != null) {
                        LikeView.this.onErrorListener.onError(NativeProtocol.getExceptionFromErrorData(extras));
                    }
                } else if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET.equals(intentAction)) {
                    LikeView.this.setObjectIdAndTypeForced(LikeView.this.objectId, LikeView.this.objectType);
                    LikeView.this.updateLikeStateAndLayout();
                }
            }
        }
    }

    public enum ObjectType {
        UNKNOWN("unknown", 0),
        OPEN_GRAPH(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_OPENGRAPH, 1),
        PAGE("page", 2);
        
        public static ObjectType DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = UNKNOWN;
        }

        public static ObjectType fromInt(int enumValue) {
            for (ObjectType objectType : values()) {
                if (objectType.getValue() == enumValue) {
                    return objectType;
                }
            }
            return null;
        }

        private ObjectType(String stringValue, int value) {
            this.stringValue = stringValue;
            this.intValue = value;
        }

        public String toString() {
            return this.stringValue;
        }

        public int getValue() {
            return this.intValue;
        }
    }

    public interface OnErrorListener {
        void onError(FacebookException facebookException);
    }

    public enum Style {
        STANDARD("standard", 0),
        BUTTON("button", 1),
        BOX_COUNT("box_count", 2);
        
        static Style DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = STANDARD;
        }

        static Style fromInt(int enumValue) {
            for (Style style : values()) {
                if (style.getValue() == enumValue) {
                    return style;
                }
            }
            return null;
        }

        private Style(String stringValue, int value) {
            this.stringValue = stringValue;
            this.intValue = value;
        }

        public String toString() {
            return this.stringValue;
        }

        private int getValue() {
            return this.intValue;
        }
    }

    private class LikeActionControllerCreationCallback implements CreationCallback {
        private boolean isCancelled;

        private LikeActionControllerCreationCallback() {
        }

        public void cancel() {
            this.isCancelled = true;
        }

        public void onComplete(LikeActionController likeActionController, FacebookException error) {
            if (!this.isCancelled) {
                if (likeActionController != null) {
                    if (!likeActionController.shouldEnableView()) {
                        error = new FacebookException("Cannot use LikeView. The device may not be supported.");
                    }
                    LikeView.this.associateWithLikeActionController(likeActionController);
                    LikeView.this.updateLikeStateAndLayout();
                }
                if (!(error == null || LikeView.this.onErrorListener == null)) {
                    LikeView.this.onErrorListener.onError(error);
                }
                LikeView.this.creationCallback = null;
            }
        }
    }

    public LikeView(Context context) {
        super(context);
        initialize(context);
    }

    public LikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        initialize(context);
    }

    public void setObjectIdAndType(String objectId, ObjectType objectType) {
        objectId = Utility.coerceValueIfNullOrEmpty(objectId, null);
        objectType = objectType != null ? objectType : ObjectType.DEFAULT;
        if (!Utility.areObjectsEqual(objectId, this.objectId) || objectType != this.objectType) {
            setObjectIdAndTypeForced(objectId, objectType);
            updateLikeStateAndLayout();
        }
    }

    public void setLikeViewStyle(Style likeViewStyle) {
        likeViewStyle = likeViewStyle != null ? likeViewStyle : Style.DEFAULT;
        if (this.likeViewStyle != likeViewStyle) {
            this.likeViewStyle = likeViewStyle;
            updateLayout();
        }
    }

    public void setAuxiliaryViewPosition(AuxiliaryViewPosition auxiliaryViewPosition) {
        auxiliaryViewPosition = auxiliaryViewPosition != null ? auxiliaryViewPosition : AuxiliaryViewPosition.DEFAULT;
        if (this.auxiliaryViewPosition != auxiliaryViewPosition) {
            this.auxiliaryViewPosition = auxiliaryViewPosition;
            updateLayout();
        }
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        horizontalAlignment = horizontalAlignment != null ? horizontalAlignment : HorizontalAlignment.DEFAULT;
        if (this.horizontalAlignment != horizontalAlignment) {
            this.horizontalAlignment = horizontalAlignment;
            updateLayout();
        }
    }

    public void setForegroundColor(int foregroundColor) {
        if (this.foregroundColor != foregroundColor) {
            this.socialSentenceView.setTextColor(foregroundColor);
        }
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    public OnErrorListener getOnErrorListener() {
        return this.onErrorListener;
    }

    public void setFragment(Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    public void setFragment(android.app.Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    public void setEnabled(boolean enabled) {
        this.explicitlyDisabled = enabled ^ 1;
        updateLikeStateAndLayout();
    }

    protected void onDetachedFromWindow() {
        setObjectIdAndType(null, ObjectType.UNKNOWN);
        super.onDetachedFromWindow();
    }

    private void parseAttributes(AttributeSet attrs) {
        if (attrs != null) {
            if (getContext() != null) {
                TypedArray a = getContext().obtainStyledAttributes(attrs, C0410R.styleable.com_facebook_like_view);
                if (a != null) {
                    this.objectId = Utility.coerceValueIfNullOrEmpty(a.getString(C0410R.styleable.com_facebook_like_view_com_facebook_object_id), null);
                    this.objectType = ObjectType.fromInt(a.getInt(C0410R.styleable.com_facebook_like_view_com_facebook_object_type, ObjectType.DEFAULT.getValue()));
                    this.likeViewStyle = Style.fromInt(a.getInt(C0410R.styleable.com_facebook_like_view_com_facebook_style, Style.DEFAULT.getValue()));
                    if (this.likeViewStyle == null) {
                        throw new IllegalArgumentException("Unsupported value for LikeView 'style'");
                    }
                    this.auxiliaryViewPosition = AuxiliaryViewPosition.fromInt(a.getInt(C0410R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position, AuxiliaryViewPosition.DEFAULT.getValue()));
                    if (this.auxiliaryViewPosition == null) {
                        throw new IllegalArgumentException("Unsupported value for LikeView 'auxiliary_view_position'");
                    }
                    this.horizontalAlignment = HorizontalAlignment.fromInt(a.getInt(C0410R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment, HorizontalAlignment.DEFAULT.getValue()));
                    if (this.horizontalAlignment == null) {
                        throw new IllegalArgumentException("Unsupported value for LikeView 'horizontal_alignment'");
                    }
                    this.foregroundColor = a.getColor(C0410R.styleable.com_facebook_like_view_com_facebook_foreground_color, -1);
                    a.recycle();
                }
            }
        }
    }

    private void initialize(Context context) {
        this.edgePadding = getResources().getDimensionPixelSize(C0410R.dimen.com_facebook_likeview_edge_padding);
        this.internalPadding = getResources().getDimensionPixelSize(C0410R.dimen.com_facebook_likeview_internal_padding);
        if (this.foregroundColor == -1) {
            this.foregroundColor = getResources().getColor(C0410R.color.com_facebook_likeview_text_color);
        }
        setBackgroundColor(0);
        this.containerView = new LinearLayout(context);
        this.containerView.setLayoutParams(new LayoutParams(-2, -2));
        initializeLikeButton(context);
        initializeSocialSentenceView(context);
        initializeLikeCountView(context);
        this.containerView.addView(this.likeButton);
        this.containerView.addView(this.socialSentenceView);
        this.containerView.addView(this.likeBoxCountView);
        addView(this.containerView);
        setObjectIdAndTypeForced(this.objectId, this.objectType);
        updateLikeStateAndLayout();
    }

    private void initializeLikeButton(Context context) {
        boolean z = this.likeActionController != null && this.likeActionController.isObjectLiked();
        this.likeButton = new LikeButton(context, z);
        this.likeButton.setOnClickListener(new C05021());
        this.likeButton.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    }

    private void initializeSocialSentenceView(Context context) {
        this.socialSentenceView = new TextView(context);
        this.socialSentenceView.setTextSize(0, getResources().getDimension(C0410R.dimen.com_facebook_likeview_text_size));
        this.socialSentenceView.setMaxLines(2);
        this.socialSentenceView.setTextColor(this.foregroundColor);
        this.socialSentenceView.setGravity(17);
        this.socialSentenceView.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
    }

    private void initializeLikeCountView(Context context) {
        this.likeBoxCountView = new LikeBoxCountView(context);
        this.likeBoxCountView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    }

    private void toggleLike() {
        if (this.likeActionController != null) {
            Activity activity = null;
            if (this.parentFragment == null) {
                activity = getActivity();
            }
            this.likeActionController.toggleLike(activity, this.parentFragment, getAnalyticsParameters());
        }
    }

    private Activity getActivity() {
        Context context = getContext();
        while (!(context instanceof Activity) && (context instanceof ContextWrapper)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        throw new FacebookException("Unable to get Activity.");
    }

    private Bundle getAnalyticsParameters() {
        Bundle params = new Bundle();
        params.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, this.likeViewStyle.toString());
        params.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_AUXILIARY_POSITION, this.auxiliaryViewPosition.toString());
        params.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_HORIZONTAL_ALIGNMENT, this.horizontalAlignment.toString());
        params.putString("object_id", Utility.coerceValueIfNullOrEmpty(this.objectId, ""));
        params.putString("object_type", this.objectType.toString());
        return params;
    }

    private void setObjectIdAndTypeForced(String newObjectId, ObjectType newObjectType) {
        tearDownObjectAssociations();
        this.objectId = newObjectId;
        this.objectType = newObjectType;
        if (!Utility.isNullOrEmpty(newObjectId)) {
            this.creationCallback = new LikeActionControllerCreationCallback();
            if (!isInEditMode()) {
                LikeActionController.getControllerForObjectId(newObjectId, newObjectType, this.creationCallback);
            }
        }
    }

    private void associateWithLikeActionController(LikeActionController likeActionController) {
        this.likeActionController = likeActionController;
        this.broadcastReceiver = new LikeControllerBroadcastReceiver();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        filter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR);
        filter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
        localBroadcastManager.registerReceiver(this.broadcastReceiver, filter);
    }

    private void tearDownObjectAssociations() {
        if (this.broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.broadcastReceiver);
            this.broadcastReceiver = null;
        }
        if (this.creationCallback != null) {
            this.creationCallback.cancel();
            this.creationCallback = null;
        }
        this.likeActionController = null;
    }

    private void updateLikeStateAndLayout() {
        boolean enabled = this.explicitlyDisabled ^ 1;
        if (this.likeActionController == null) {
            this.likeButton.setSelected(false);
            this.socialSentenceView.setText(null);
            this.likeBoxCountView.setText(null);
        } else {
            this.likeButton.setSelected(this.likeActionController.isObjectLiked());
            this.socialSentenceView.setText(this.likeActionController.getSocialSentence());
            this.likeBoxCountView.setText(this.likeActionController.getLikeCountString());
            enabled &= this.likeActionController.shouldEnableView();
        }
        super.setEnabled(enabled);
        this.likeButton.setEnabled(enabled);
        updateLayout();
    }

    private void updateLayout() {
        View auxView;
        LayoutParams containerViewLayoutParams = (LayoutParams) this.containerView.getLayoutParams();
        LinearLayout.LayoutParams buttonLayoutParams = (LinearLayout.LayoutParams) this.likeButton.getLayoutParams();
        int i = 1;
        int viewGravity = this.horizontalAlignment == HorizontalAlignment.LEFT ? 3 : this.horizontalAlignment == HorizontalAlignment.CENTER ? 1 : 5;
        containerViewLayoutParams.gravity = viewGravity | 48;
        buttonLayoutParams.gravity = viewGravity;
        this.socialSentenceView.setVisibility(8);
        this.likeBoxCountView.setVisibility(8);
        if (this.likeViewStyle == Style.STANDARD && this.likeActionController != null && !Utility.isNullOrEmpty(this.likeActionController.getSocialSentence())) {
            auxView = this.socialSentenceView;
        } else if (this.likeViewStyle == Style.BOX_COUNT && this.likeActionController != null && !Utility.isNullOrEmpty(this.likeActionController.getLikeCountString())) {
            updateBoxCountCaretPosition();
            auxView = this.likeBoxCountView;
        } else {
            return;
        }
        auxView.setVisibility(0);
        ((LinearLayout.LayoutParams) auxView.getLayoutParams()).gravity = viewGravity;
        LinearLayout linearLayout = this.containerView;
        if (this.auxiliaryViewPosition == AuxiliaryViewPosition.INLINE) {
            i = 0;
        }
        linearLayout.setOrientation(i);
        if (this.auxiliaryViewPosition != AuxiliaryViewPosition.TOP) {
            if (this.auxiliaryViewPosition != AuxiliaryViewPosition.INLINE || this.horizontalAlignment != HorizontalAlignment.RIGHT) {
                this.containerView.removeView(auxView);
                this.containerView.addView(auxView);
                switch (this.auxiliaryViewPosition) {
                    case TOP:
                        auxView.setPadding(this.edgePadding, this.edgePadding, this.edgePadding, this.internalPadding);
                        break;
                    case BOTTOM:
                        auxView.setPadding(this.edgePadding, this.internalPadding, this.edgePadding, this.edgePadding);
                        break;
                    case INLINE:
                        if (this.horizontalAlignment != HorizontalAlignment.RIGHT) {
                            auxView.setPadding(this.internalPadding, this.edgePadding, this.edgePadding, this.edgePadding);
                            break;
                        } else {
                            auxView.setPadding(this.edgePadding, this.edgePadding, this.internalPadding, this.edgePadding);
                            break;
                        }
                    default:
                        break;
                }
            }
        }
        this.containerView.removeView(this.likeButton);
        this.containerView.addView(this.likeButton);
        switch (this.auxiliaryViewPosition) {
            case TOP:
                auxView.setPadding(this.edgePadding, this.edgePadding, this.edgePadding, this.internalPadding);
                break;
            case BOTTOM:
                auxView.setPadding(this.edgePadding, this.internalPadding, this.edgePadding, this.edgePadding);
                break;
            case INLINE:
                if (this.horizontalAlignment != HorizontalAlignment.RIGHT) {
                    auxView.setPadding(this.edgePadding, this.edgePadding, this.internalPadding, this.edgePadding);
                    break;
                } else {
                    auxView.setPadding(this.internalPadding, this.edgePadding, this.edgePadding, this.edgePadding);
                    break;
                }
            default:
                break;
        }
    }

    private void updateBoxCountCaretPosition() {
        switch (this.auxiliaryViewPosition) {
            case TOP:
                this.likeBoxCountView.setCaretPosition(LikeBoxCountViewCaretPosition.BOTTOM);
                return;
            case BOTTOM:
                this.likeBoxCountView.setCaretPosition(LikeBoxCountViewCaretPosition.TOP);
                return;
            case INLINE:
                this.likeBoxCountView.setCaretPosition(this.horizontalAlignment == HorizontalAlignment.RIGHT ? LikeBoxCountViewCaretPosition.RIGHT : LikeBoxCountViewCaretPosition.LEFT);
                return;
            default:
                return;
        }
    }
}
