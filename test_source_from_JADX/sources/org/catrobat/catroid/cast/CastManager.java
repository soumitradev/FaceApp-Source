package org.catrobat.catroid.cast;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.support.v7.media.MediaRouter.Callback;
import android.support.v7.media.MediaRouter.RouteInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.CastRemoteDisplayLocalService;
import com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks;
import com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings;
import com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings.Builder;
import com.google.android.gms.common.api.Status;
import java.util.ArrayList;
import java.util.EnumMap;
import javax.jmdns.impl.constants.DNSConstants;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.bricks.CameraBrick;
import org.catrobat.catroid.content.bricks.ChooseCameraBrick;
import org.catrobat.catroid.content.bricks.FlashBrick;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.adapter.CastDevicesAdapter;
import org.catrobat.catroid.ui.dialogs.SelectCastDialog;
import org.catrobat.catroid.utils.ToastUtil;

public final class CastManager {
    private static final CastManager INSTANCE = new CastManager();
    public static ArrayList<Class<?>> unsupportedBricks = new C17541();
    private MyMediaRouterCallback callback;
    private MenuItem castButton;
    private ArrayAdapter<RouteInfo> deviceAdapter;
    private StageActivity gamepadActivity;
    private AppCompatActivity initializingActivity;
    private boolean isCastDeviceAvailable;
    private boolean isConnected = false;
    private EnumMap<Sensors, Boolean> isGamepadButtonPressed = new EnumMap(Sensors.class);
    private MediaRouteSelector mediaRouteSelector;
    private MediaRouter mediaRouter;
    private boolean pausedScreenShowing = false;
    private RelativeLayout pausedView = null;
    private RelativeLayout remoteLayout;
    private final ArrayList<RouteInfo> routeInfos = new ArrayList();
    private CastDevice selectedDevice;
    private GLSurfaceView20 stageViewDisplayedOnCast;

    /* renamed from: org.catrobat.catroid.cast.CastManager$1 */
    static class C17541 extends ArrayList<Class<?>> {
        C17541() {
            add(CameraBrick.class);
            add(ChooseCameraBrick.class);
            add(FlashBrick.class);
        }
    }

    /* renamed from: org.catrobat.catroid.cast.CastManager$2 */
    class C17552 implements OnClickListener {
        C17552() {
        }

        public void onClick(View v) {
            v.performHapticFeedback(1);
            CastManager.this.gamepadActivity.onBackPressed();
        }
    }

    /* renamed from: org.catrobat.catroid.cast.CastManager$3 */
    class C17563 implements OnTouchListener {
        C17563() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            CastManager.this.handleGamepadTouch((ImageButton) v, event);
            return true;
        }
    }

    private class MyMediaRouterCallback extends Callback {
        private long lastConnectionTry;

        /* renamed from: org.catrobat.catroid.cast.CastManager$MyMediaRouterCallback$1 */
        class C17571 implements Runnable {
            C17571() {
            }

            public void run() {
                synchronized (this) {
                    if (CastManager.this.currentlyConnecting() && CastManager.this.isCastDeviceAvailable) {
                        CastRemoteDisplayLocalService.stopService();
                        ToastUtil.showError(CastManager.this.initializingActivity, CastManager.this.initializingActivity.getString(R.string.cast_connection_timout_msg));
                    }
                }
            }
        }

        private MyMediaRouterCallback() {
        }

        public void onRouteAdded(MediaRouter router, RouteInfo info) {
            synchronized (this) {
                for (int i = 0; i < CastManager.this.routeInfos.size(); i++) {
                    if (((RouteInfo) CastManager.this.routeInfos.get(i)).equals(info)) {
                        CastManager.this.routeInfos.remove(i);
                    }
                }
                CastManager.this.routeInfos.add(info);
                CastManager.this.castButton.setVisible(CastManager.this.mediaRouter.isRouteAvailable(CastManager.this.mediaRouteSelector, 2));
                CastManager.this.deviceAdapter.notifyDataSetChanged();
            }
        }

        public void onRouteRemoved(MediaRouter router, RouteInfo info) {
            synchronized (this) {
                for (int i = 0; i < CastManager.this.routeInfos.size(); i++) {
                    if (((RouteInfo) CastManager.this.routeInfos.get(i)).equals(info)) {
                        CastManager.this.routeInfos.remove(i);
                        if (CastManager.this.routeInfos.size() == 0) {
                            CastManager.this.castButton.setVisible(CastManager.this.mediaRouter.isRouteAvailable(CastManager.this.mediaRouteSelector, 2));
                        }
                        CastManager.this.deviceAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

        public void onRouteSelected(MediaRouter router, RouteInfo info) {
            synchronized (this) {
                CastManager.this.selectedDevice = CastDevice.getFromBundle(info.getExtras());
                startCastService(CastManager.this.initializingActivity);
                this.lastConnectionTry = System.currentTimeMillis();
                CastManager castManager = CastManager.this;
                boolean z = CastRemoteDisplayLocalService.getInstance() != null && System.currentTimeMillis() - this.lastConnectionTry >= DNSConstants.CLOSE_TIMEOUT;
                castManager.isCastDeviceAvailable = z;
                new Handler().postDelayed(new C17571(), DNSConstants.CLOSE_TIMEOUT);
            }
        }

        public void onRouteUnselected(MediaRouter router, RouteInfo info) {
            onCastStop();
        }

        public synchronized void onCastStop() {
            if (CastManager.this.stageViewDisplayedOnCast != null) {
                CastManager.this.gamepadActivity.onBackPressed();
            }
            CastManager.this.stageViewDisplayedOnCast = null;
            CastManager.this.setIsConnected(false);
            CastManager.this.selectedDevice = null;
            CastManager.this.gamepadActivity = null;
            CastManager.this.remoteLayout = null;
            CastManager.this.pausedView = null;
            CastManager.this.pausedScreenShowing = false;
            CastRemoteDisplayLocalService.stopService();
        }

        public void startCastService(final AppCompatActivity activity) {
            Intent intent = new Intent(activity, activity.getClass());
            intent.setFlags(603979776);
            NotificationSettings settings = new Builder().setNotificationPendingIntent(PendingIntent.getActivity(activity, 0, intent, 0)).build();
            Callbacks callbacks = new Callbacks() {
                public void onServiceCreated(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
                }

                public void onRemoteDisplaySessionStarted(CastRemoteDisplayLocalService service) {
                }

                public void onRemoteDisplaySessionError(Status errorReason) {
                    MyMediaRouterCallback.this.onCastStop();
                    activity.finish();
                }

                public void onRemoteDisplaySessionEnded(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
                }
            };
            Context context = activity;
            CastRemoteDisplayLocalService.startService(context, CastService.class, Constants.REMOTE_DISPLAY_APP_ID, CastManager.this.selectedDevice, settings, callbacks);
        }
    }

    private CastManager() {
        this.isGamepadButtonPressed.put(Sensors.GAMEPAD_A_PRESSED, Boolean.valueOf(false));
        this.isGamepadButtonPressed.put(Sensors.GAMEPAD_B_PRESSED, Boolean.valueOf(false));
        this.isGamepadButtonPressed.put(Sensors.GAMEPAD_LEFT_PRESSED, Boolean.valueOf(false));
        this.isGamepadButtonPressed.put(Sensors.GAMEPAD_RIGHT_PRESSED, Boolean.valueOf(false));
        this.isGamepadButtonPressed.put(Sensors.GAMEPAD_UP_PRESSED, Boolean.valueOf(false));
        this.isGamepadButtonPressed.put(Sensors.GAMEPAD_DOWN_PRESSED, Boolean.valueOf(false));
    }

    public static CastManager getInstance() {
        return INSTANCE;
    }

    public synchronized void initializeGamepadActivity(StageActivity gamepadActivity) {
        this.gamepadActivity = gamepadActivity;
        initGamepadListeners();
    }

    public synchronized void setIsConnected(boolean isConnected) {
        this.castButton.setIcon(isConnected ? R.drawable.ic_cast_connected_white : R.drawable.ic_cast_white);
        this.isConnected = isConnected;
        this.initializingActivity.invalidateOptionsMenu();
    }

    public void startCastButtonAnimation() {
        this.castButton.setIcon(R.drawable.animation_cast_button_connecting);
        ((AnimationDrawable) this.castButton.getIcon()).start();
    }

    public synchronized boolean isConnected() {
        return this.isConnected;
    }

    public MediaRouter getMediaRouter() {
        return this.mediaRouter;
    }

    public ArrayAdapter<RouteInfo> getDeviceAdapter() {
        return this.deviceAdapter;
    }

    public ArrayList<RouteInfo> getRouteInfos() {
        return this.routeInfos;
    }

    public boolean isButtonPressed(Sensors btnSensor) {
        return ((Boolean) this.isGamepadButtonPressed.get(btnSensor)).booleanValue();
    }

    public void setButtonPress(Sensors btn, boolean b) {
        this.isGamepadButtonPressed.put(btn, Boolean.valueOf(b));
    }

    public CastDevice getSelectedDevice() {
        return this.selectedDevice;
    }

    public synchronized void initializeCast(AppCompatActivity activity) {
        this.initializingActivity = activity;
        if (this.mediaRouter == null) {
            this.deviceAdapter = new CastDevicesAdapter(activity, R.layout.fragment_cast_device_list_item, this.routeInfos);
            this.mediaRouter = MediaRouter.getInstance(activity.getApplicationContext());
            this.mediaRouteSelector = new MediaRouteSelector.Builder().addControlCategory(CastMediaControlIntent.categoryForCast(Constants.REMOTE_DISPLAY_APP_ID)).build();
            setCallback();
        }
    }

    public void addCallback() {
        this.callback = new MyMediaRouterCallback();
        this.mediaRouter.addCallback(this.mediaRouteSelector, this.callback, 4);
    }

    public synchronized void setCallback() {
        setCallback(4);
    }

    public synchronized void setCallback(int callbackFlag) {
        if (this.callback == null) {
            this.callback = new MyMediaRouterCallback();
        }
        this.mediaRouter.addCallback(this.mediaRouteSelector, this.callback, callbackFlag);
    }

    public void openDeviceSelectorOrDisconnectDialog() {
        openDeviceSelectorOrDisconnectDialog(this.initializingActivity);
    }

    private void initGamepadListeners() {
        this.gamepadActivity.findViewById(R.id.gamepadPauseButton).setOnClickListener(new C17552());
        OnTouchListener otl = new C17563();
        gamepadButtons = new ImageButton[6];
        int i = 0;
        gamepadButtons[0] = (ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonA);
        gamepadButtons[1] = (ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonB);
        gamepadButtons[2] = (ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonUp);
        gamepadButtons[3] = (ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonDown);
        gamepadButtons[4] = (ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonLeft);
        gamepadButtons[5] = (ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonRight);
        int length = gamepadButtons.length;
        while (i < length) {
            gamepadButtons[i].setOnTouchListener(otl);
            i++;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void handleGamepadTouch(android.widget.ImageButton r6, android.view.MotionEvent r7) {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = r7.getAction();	 Catch:{ all -> 0x00a6 }
        r1 = 1;
        if (r0 == 0) goto L_0x0010;
    L_0x0008:
        r0 = r7.getAction();	 Catch:{ all -> 0x00a6 }
        if (r0 == r1) goto L_0x0010;
    L_0x000e:
        monitor-exit(r5);
        return;
    L_0x0010:
        r0 = r5.gamepadActivity;	 Catch:{ all -> 0x00a6 }
        if (r0 != 0) goto L_0x0016;
    L_0x0014:
        monitor-exit(r5);
        return;
    L_0x0016:
        r0 = r7.getAction();	 Catch:{ all -> 0x00a6 }
        if (r0 != 0) goto L_0x001e;
    L_0x001c:
        r0 = 1;
        goto L_0x001f;
    L_0x001e:
        r0 = 0;
    L_0x001f:
        r2 = r6.getId();	 Catch:{ all -> 0x00a6 }
        switch(r2) {
            case 2131362494: goto L_0x0072;
            case 2131362495: goto L_0x005a;
            case 2131362496: goto L_0x004e;
            case 2131362497: goto L_0x0042;
            case 2131362498: goto L_0x0036;
            case 2131362499: goto L_0x002a;
            default: goto L_0x0026;
        };	 Catch:{ all -> 0x00a6 }
    L_0x0026:
        r1 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x00a6 }
        goto L_0x00a0;
    L_0x002a:
        r2 = r5.gamepadActivity;	 Catch:{ all -> 0x00a6 }
        r3 = 2131820849; // 0x7f110131 float:1.9274425E38 double:1.0532594446E-314;
        r2 = r2.getString(r3);	 Catch:{ all -> 0x00a6 }
        r3 = org.catrobat.catroid.formulaeditor.Sensors.GAMEPAD_UP_PRESSED;	 Catch:{ all -> 0x00a6 }
        goto L_0x008a;
    L_0x0036:
        r2 = r5.gamepadActivity;	 Catch:{ all -> 0x00a6 }
        r3 = 2131820848; // 0x7f110130 float:1.9274423E38 double:1.053259444E-314;
        r2 = r2.getString(r3);	 Catch:{ all -> 0x00a6 }
        r3 = org.catrobat.catroid.formulaeditor.Sensors.GAMEPAD_RIGHT_PRESSED;	 Catch:{ all -> 0x00a6 }
        goto L_0x008a;
    L_0x0042:
        r2 = r5.gamepadActivity;	 Catch:{ all -> 0x00a6 }
        r3 = 2131820847; // 0x7f11012f float:1.927442E38 double:1.0532594436E-314;
        r2 = r2.getString(r3);	 Catch:{ all -> 0x00a6 }
        r3 = org.catrobat.catroid.formulaeditor.Sensors.GAMEPAD_LEFT_PRESSED;	 Catch:{ all -> 0x00a6 }
        goto L_0x008a;
    L_0x004e:
        r2 = r5.gamepadActivity;	 Catch:{ all -> 0x00a6 }
        r3 = 2131820846; // 0x7f11012e float:1.9274418E38 double:1.053259443E-314;
        r2 = r2.getString(r3);	 Catch:{ all -> 0x00a6 }
        r3 = org.catrobat.catroid.formulaeditor.Sensors.GAMEPAD_DOWN_PRESSED;	 Catch:{ all -> 0x00a6 }
        goto L_0x008a;
    L_0x005a:
        if (r0 == 0) goto L_0x0060;
    L_0x005c:
        r2 = 2131230987; // 0x7f08010b float:1.8078042E38 double:1.052968014E-314;
        goto L_0x0063;
    L_0x0060:
        r2 = 2131230986; // 0x7f08010a float:1.807804E38 double:1.0529680135E-314;
    L_0x0063:
        r6.setImageResource(r2);	 Catch:{ all -> 0x00a6 }
        r2 = r5.gamepadActivity;	 Catch:{ all -> 0x00a6 }
        r3 = 2131820845; // 0x7f11012d float:1.9274416E38 double:1.0532594426E-314;
        r2 = r2.getString(r3);	 Catch:{ all -> 0x00a6 }
        r3 = org.catrobat.catroid.formulaeditor.Sensors.GAMEPAD_B_PRESSED;	 Catch:{ all -> 0x00a6 }
        goto L_0x008a;
    L_0x0072:
        if (r0 == 0) goto L_0x0078;
    L_0x0074:
        r2 = 2131230985; // 0x7f080109 float:1.8078038E38 double:1.052968013E-314;
        goto L_0x007b;
    L_0x0078:
        r2 = 2131230984; // 0x7f080108 float:1.8078036E38 double:1.0529680125E-314;
    L_0x007b:
        r6.setImageResource(r2);	 Catch:{ all -> 0x00a6 }
        r2 = r5.gamepadActivity;	 Catch:{ all -> 0x00a6 }
        r3 = 2131820844; // 0x7f11012c float:1.9274414E38 double:1.053259442E-314;
        r2 = r2.getString(r3);	 Catch:{ all -> 0x00a6 }
        r3 = org.catrobat.catroid.formulaeditor.Sensors.GAMEPAD_A_PRESSED;	 Catch:{ all -> 0x00a6 }
        r5.setButtonPress(r3, r0);	 Catch:{ all -> 0x00a6 }
        if (r0 == 0) goto L_0x009e;
    L_0x0090:
        r4 = r5.gamepadActivity;	 Catch:{ all -> 0x00a6 }
        r4 = r4.getApplicationListener();	 Catch:{ all -> 0x00a6 }
        r4 = (org.catrobat.catroid.stage.StageListener) r4;	 Catch:{ all -> 0x00a6 }
        r4.gamepadPressed(r2);	 Catch:{ all -> 0x00a6 }
        r6.performHapticFeedback(r1);	 Catch:{ all -> 0x00a6 }
    L_0x009e:
        monitor-exit(r5);
        return;
    L_0x00a0:
        r2 = "Unknown button pressed";
        r1.<init>(r2);	 Catch:{ all -> 0x00a6 }
        throw r1;	 Catch:{ all -> 0x00a6 }
    L_0x00a6:
        r6 = move-exception;
        monitor-exit(r5);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.cast.CastManager.handleGamepadTouch(android.widget.ImageButton, android.view.MotionEvent):void");
    }

    public synchronized void addStageViewToLayout(GLSurfaceView20 stageView) {
        this.stageViewDisplayedOnCast = stageView;
        this.remoteLayout.setBackgroundColor(ContextCompat.getColor(this.initializingActivity, 17170443));
        this.remoteLayout.removeAllViews();
        this.remoteLayout.addView(this.stageViewDisplayedOnCast);
        Project project = ProjectManager.getInstance().getCurrentProject();
        stageView.surfaceChanged(stageView.getHolder(), 0, project.getXmlHeader().getVirtualScreenWidth(), project.getXmlHeader().getVirtualScreenHeight());
    }

    public synchronized boolean currentlyConnecting() {
        boolean z;
        z = (this.isConnected || this.selectedDevice == null) ? false : true;
        return z;
    }

    public synchronized void openDeviceSelectorOrDisconnectDialog(AppCompatActivity activity) {
        new SelectCastDialog().show(activity.getSupportFragmentManager(), SelectCastDialog.TAG);
    }

    public synchronized void setCastButton(MenuItem castButton) {
        this.castButton = castButton;
        castButton.setVisible(this.mediaRouter.isRouteAvailable(this.mediaRouteSelector, 2));
        setIsConnected(this.isConnected);
    }

    public void selectRoute(RouteInfo routeInfo) {
        this.mediaRouter.selectRoute(routeInfo);
    }

    public synchronized void setRemoteLayout(RelativeLayout remoteLayout) {
        this.remoteLayout = remoteLayout;
    }

    public synchronized void setRemoteLayoutToIdleScreen(Context context) {
        this.remoteLayout.removeAllViews();
        this.remoteLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.idle_screen_1));
    }

    @SuppressLint({"InflateParams"})
    public synchronized void setRemoteLayoutToPauseScreen(Context context) {
        if (this.remoteLayout != null) {
            if (this.pausedView == null && !this.pausedScreenShowing) {
                this.pausedView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.cast_pause_screen, null);
                this.remoteLayout.addView(this.pausedView);
                LayoutParams layoutParams = (LayoutParams) this.pausedView.getLayoutParams();
                Project p = ProjectManager.getInstance().getCurrentProject();
                layoutParams.height = p.getXmlHeader().getVirtualScreenHeight();
                layoutParams.width = p.getXmlHeader().getVirtualScreenWidth();
                this.pausedView.setLayoutParams(layoutParams);
                this.pausedView.setBackgroundColor(Constants.CAST_IDLE_BACKGROUND_COLOR);
                this.pausedScreenShowing = true;
            }
            this.pausedView.setVisibility(0);
            this.pausedScreenShowing = true;
        }
    }

    public synchronized void resumeRemoteLayoutFromPauseScreen() {
        if (!(this.remoteLayout == null || this.pausedView == null)) {
            this.pausedView.setVisibility(8);
            this.pausedScreenShowing = false;
        }
    }

    public synchronized void onStageDestroyed() {
        if (this.isConnected) {
            setRemoteLayoutToIdleScreen(this.initializingActivity);
        }
        this.stageViewDisplayedOnCast = null;
        this.pausedView = null;
        this.pausedScreenShowing = false;
    }
}
