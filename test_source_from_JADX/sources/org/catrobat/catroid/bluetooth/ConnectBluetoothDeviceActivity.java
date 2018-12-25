package org.catrobat.catroid.bluetooth;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Set;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection.State;
import org.catrobat.catroid.bluetooth.base.BluetoothConnectionFactory;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceFactory;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.ToastUtil;

public class ConnectBluetoothDeviceActivity extends AppCompatActivity {
    private static final int DEVICE_MAC_ADDRESS_LENGTH = 18;
    public static final String DEVICE_TO_CONNECT = "org.catrobat.catroid.bluetooth.DEVICE";
    public static final String TAG = ConnectBluetoothDeviceActivity.class.getSimpleName();
    private static BluetoothConnectionFactory btConnectionFactory;
    private static BluetoothDeviceFactory btDeviceFactory;
    protected BluetoothDevice btDevice;
    private BluetoothManager btManager;
    private OnItemClickListener deviceClickListener = new C17441();
    private ArrayAdapter<String> newDevicesArrayAdapter;
    private ArrayAdapter<String> pairedDevicesArrayAdapter;
    private final BroadcastReceiver receiver = new C17452();

    /* renamed from: org.catrobat.catroid.bluetooth.ConnectBluetoothDeviceActivity$1 */
    class C17441 implements OnItemClickListener {
        C17441() {
        }

        private String getSelectedBluetoothAddress(View view) {
            String info = ((TextView) view).getText().toString();
            if (info.lastIndexOf(45) != info.length() - 18) {
                return null;
            }
            return info.substring(info.lastIndexOf(45) + 1);
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            String address = getSelectedBluetoothAddress(view);
            if (address != null) {
                ConnectBluetoothDeviceActivity.this.connectDevice(address);
            }
        }
    }

    /* renamed from: org.catrobat.catroid.bluetooth.ConnectBluetoothDeviceActivity$2 */
    class C17452 extends BroadcastReceiver {
        C17452() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.bluetooth.device.action.FOUND".equals(action)) {
                android.bluetooth.BluetoothDevice device = (android.bluetooth.BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (device.getBondState() != 12) {
                    ArrayAdapter access$100 = ConnectBluetoothDeviceActivity.this.newDevicesArrayAdapter;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(device.getName());
                    stringBuilder.append("-");
                    stringBuilder.append(device.getAddress());
                    access$100.add(stringBuilder.toString());
                }
            } else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                ConnectBluetoothDeviceActivity.this.setProgressBarIndeterminateVisibility(false);
                ConnectBluetoothDeviceActivity.this.findViewById(R.id.device_list_progress_bar).setVisibility(8);
                ConnectBluetoothDeviceActivity connectBluetoothDeviceActivity = ConnectBluetoothDeviceActivity.this;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(ConnectBluetoothDeviceActivity.this.getString(R.string.select_device));
                stringBuilder2.append(FormatHelper.SPACE);
                stringBuilder2.append(ConnectBluetoothDeviceActivity.this.btDevice.getName());
                connectBluetoothDeviceActivity.setTitle(stringBuilder2.toString());
                if (ConnectBluetoothDeviceActivity.this.newDevicesArrayAdapter.isEmpty()) {
                    ConnectBluetoothDeviceActivity.this.newDevicesArrayAdapter.add(ConnectBluetoothDeviceActivity.this.getResources().getString(R.string.none_found));
                }
            }
        }
    }

    /* renamed from: org.catrobat.catroid.bluetooth.ConnectBluetoothDeviceActivity$3 */
    class C17463 implements OnClickListener {
        C17463() {
        }

        public void onClick(View view) {
            ConnectBluetoothDeviceActivity.this.doDiscovery();
            view.setVisibility(8);
        }
    }

    private class ConnectDeviceTask extends AsyncTask<String, Void, State> {
        BluetoothConnection btConnection;
        private ProgressDialog connectingProgressDialog;

        private ConnectDeviceTask() {
        }

        protected void onPreExecute() {
            ConnectBluetoothDeviceActivity.this.setVisible(false);
            this.connectingProgressDialog = ProgressDialog.show(ConnectBluetoothDeviceActivity.this, "", ConnectBluetoothDeviceActivity.this.getResources().getString(R.string.connecting_please_wait), true);
        }

        protected State doInBackground(String... addresses) {
            if (ConnectBluetoothDeviceActivity.this.btDevice == null) {
                Log.e(ConnectBluetoothDeviceActivity.TAG, "Try connect to device which is not implemented!");
                return State.NOT_CONNECTED;
            }
            this.btConnection = ConnectBluetoothDeviceActivity.getConnectionFactory().createBTConnectionForDevice(ConnectBluetoothDeviceActivity.this.btDevice.getDeviceType(), addresses[0], ConnectBluetoothDeviceActivity.this.btDevice.getBluetoothDeviceUUID(), ConnectBluetoothDeviceActivity.this.getApplicationContext());
            return this.btConnection.connect();
        }

        protected void onPostExecute(State connectionState) {
            this.connectingProgressDialog.dismiss();
            int result = 0;
            if (connectionState == State.CONNECTED) {
                ConnectBluetoothDeviceActivity.this.btDevice.setConnection(this.btConnection);
                result = -1;
                ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).deviceConnected(ConnectBluetoothDeviceActivity.this.btDevice);
            } else {
                ToastUtil.showError(ConnectBluetoothDeviceActivity.this, (int) R.string.bt_connection_failed);
            }
            ConnectBluetoothDeviceActivity.this.setResult(result);
            ConnectBluetoothDeviceActivity.this.finish();
        }
    }

    private static BluetoothDeviceFactory getDeviceFactory() {
        if (btDeviceFactory == null) {
            btDeviceFactory = new BluetoothDeviceFactoryImpl();
        }
        return btDeviceFactory;
    }

    private static BluetoothConnectionFactory getConnectionFactory() {
        if (btConnectionFactory == null) {
            btConnectionFactory = new BluetoothConnectionFactoryImpl();
        }
        return btConnectionFactory;
    }

    public static void setDeviceFactory(BluetoothDeviceFactory deviceFactory) {
        btDeviceFactory = deviceFactory;
    }

    public static void setConnectionFactory(BluetoothConnectionFactory connectionFactory) {
        btConnectionFactory = connectionFactory;
    }

    public void addPairedDevice(String pairedDevice) {
        if (this.pairedDevicesArrayAdapter != null) {
            this.pairedDevicesArrayAdapter.add(pairedDevice);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAndSetDeviceService();
        setContentView(R.layout.device_list);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.select_device));
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(this.btDevice.getName());
        setTitle(stringBuilder.toString());
        setResult(0);
        ((Button) findViewById(R.id.button_scan)).setOnClickListener(new C17463());
        this.pairedDevicesArrayAdapter = new ArrayAdapter(this, R.layout.device_name);
        this.newDevicesArrayAdapter = new ArrayAdapter(this, R.layout.device_name);
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(this.pairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(this.deviceClickListener);
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(this.newDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(this.deviceClickListener);
        registerReceiver(this.receiver, new IntentFilter("android.bluetooth.device.action.FOUND"));
        registerReceiver(this.receiver, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
        if (activateBluetooth() == 1) {
            listAndSelectDevices();
        }
    }

    private void listAndSelectDevices() {
        Set<android.bluetooth.BluetoothDevice> pairedDevices = this.btManager.getBluetoothAdapter().getBondedDevices();
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(0);
            for (android.bluetooth.BluetoothDevice device : pairedDevices) {
                ArrayAdapter arrayAdapter = this.pairedDevicesArrayAdapter;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(device.getName());
                stringBuilder.append("-");
                stringBuilder.append(device.getAddress());
                arrayAdapter.add(stringBuilder.toString());
            }
        }
        if (pairedDevices.size() == 0) {
            this.pairedDevicesArrayAdapter.add(getResources().getText(R.string.none_paired).toString());
        }
        setVisible(true);
    }

    protected void createAndSetDeviceService() {
        this.btDevice = getDeviceFactory().createDevice((Class) getIntent().getSerializableExtra(DEVICE_TO_CONNECT), getApplicationContext());
    }

    private void connectDevice(String address) {
        this.btManager.getBluetoothAdapter().cancelDiscovery();
        new ConnectDeviceTask().execute(new String[]{address});
    }

    protected void onDestroy() {
        if (!(this.btManager == null || this.btManager.getBluetoothAdapter() == null)) {
            this.btManager.getBluetoothAdapter().cancelDiscovery();
        }
        unregisterReceiver(this.receiver);
        super.onDestroy();
    }

    private void doDiscovery() {
        setProgressBarIndeterminateVisibility(true);
        findViewById(R.id.title_new_devices).setVisibility(0);
        findViewById(R.id.device_list_progress_bar).setVisibility(0);
        if (this.btManager.getBluetoothAdapter().isDiscovering()) {
            this.btManager.getBluetoothAdapter().cancelDiscovery();
        }
        this.btManager.getBluetoothAdapter().startDiscovery();
    }

    private int activateBluetooth() {
        this.btManager = new BluetoothManager(this);
        int bluetoothState = this.btManager.activateBluetooth();
        if (bluetoothState == -1) {
            ToastUtil.showError((Context) this, (int) R.string.notification_blueth_err);
            setResult(0);
            finish();
        }
        return bluetoothState;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "Bluetooth activation activity returned");
        switch (resultCode) {
            case -1:
                listAndSelectDevices();
                return;
            case 0:
                ToastUtil.showError((Context) this, (int) R.string.notification_blueth_err);
                setResult(0);
                finish();
                return;
            default:
                return;
        }
    }
}
