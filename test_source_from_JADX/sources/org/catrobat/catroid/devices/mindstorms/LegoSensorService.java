package org.catrobat.catroid.devices.mindstorms;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3Command;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandByteCode;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandOpCode;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandByte.EV3CommandParamFormat;
import org.catrobat.catroid.devices.mindstorms.ev3.EV3CommandType;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor.Sensor;
import org.catrobat.catroid.devices.mindstorms.nxt.Command;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandByte;
import org.catrobat.catroid.devices.mindstorms.nxt.CommandType;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensorMode;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensorType;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.PausableScheduledThreadPoolExecutor;

public class LegoSensorService implements CatroidService, OnSharedPreferenceChangeListener {
    private static final int SENSOR_UPDATER_THREAD_COUNT = 2;
    private static final String TAG = LegoSensorService.class.getSimpleName();
    private SharedPreferences preferences;
    private List<OnSensorChangedListener> sensorChangedListeners = new ArrayList();
    private LegoSensorFactory sensorFactory;
    private SensorRegistry sensorRegistry;
    private PausableScheduledThreadPoolExecutor sensorScheduler;
    private int sensorType;

    public interface OnSensorChangedListener {
        void onSensorChanged();
    }

    private class SensorRegistry {
        private static final int INITIAL_DELAY = 500;
        private SparseArray<ScheduledFuture> registeredSensors;

        private SensorRegistry() {
            this.registeredSensors = new SparseArray();
        }

        public synchronized void add(final LegoSensor sensor) {
            remove(sensor.getConnectedPort());
            this.registeredSensors.put(sensor.getConnectedPort(), LegoSensorService.this.sensorScheduler.scheduleWithFixedDelay(new Runnable() {
                public void run() {
                    sensor.updateLastSensorValue();
                }
            }, 500, (long) sensor.getUpdateInterval(), TimeUnit.MILLISECONDS));
        }

        public synchronized void remove(int port) {
            ScheduledFuture updateSchedule = (ScheduledFuture) this.registeredSensors.get(port);
            if (updateSchedule != null) {
                updateSchedule.cancel(false);
            }
            this.registeredSensors.remove(port);
        }
    }

    public LegoSensorService(int sensorType, MindstormsConnection connection, SharedPreferences preferences) {
        if (sensorType == 0 || sensorType == 1) {
            this.sensorType = sensorType;
            this.preferences = preferences;
            this.sensorRegistry = new SensorRegistry();
            this.sensorFactory = new LegoSensorFactory(connection);
            this.sensorScheduler = new PausableScheduledThreadPoolExecutor(2);
            this.preferences.registerOnSharedPreferenceChangeListener(this);
            this.sensorScheduler.pause();
            return;
        }
        throw new IllegalArgumentException("Trying to construct LegoSensorService with invalid sensorType!");
    }

    public LegoSensor createSensor(int port) {
        if (port >= 0) {
            if (port <= 3) {
                Enum sensor = getSensorByPort(port);
                if (sensor != Sensor.NO_SENSOR) {
                    if (sensor != NXTSensor.Sensor.NO_SENSOR) {
                        LegoSensor result = this.sensorFactory.create(sensor, port);
                        this.sensorRegistry.add(result);
                        return result;
                    }
                }
                this.sensorRegistry.remove(port);
                return null;
            }
        }
        throw new IllegalArgumentException("Trying to create sensor with invalid port number!");
    }

    private Enum getSensorByPort(int port) {
        switch (this.sensorType) {
            case 0:
                return NXTSensor.Sensor.getSensorFromSensorCode(this.preferences.getString(SettingsFragment.NXT_SENSORS[port], null));
            case 1:
                return Sensor.getSensorFromSensorCode(this.preferences.getString(SettingsFragment.EV3_SENSORS[port], null));
            default:
                throw new IllegalArgumentException();
        }
    }

    public void pauseSensorUpdate() {
        this.sensorScheduler.pause();
    }

    public void resumeSensorUpdate() {
        this.sensorScheduler.resume();
    }

    public void destroy() {
        this.sensorScheduler.shutdown();
        this.preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    public void deactivateAllSensors(MindstormsConnection connection) {
        if (this.sensorType == 0) {
            deactivateAllNxtSensors(connection);
        } else if (this.sensorType == 1) {
            deactivateAllEv3Sensors(connection);
        }
    }

    private void deactivateAllNxtSensors(MindstormsConnection connection) {
        for (int port = 0; port < 4; port++) {
            Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.SET_INPUT_MODE, false);
            command.append((byte) port);
            command.append(NXTSensorType.NO_SENSOR.getByte());
            command.append(NXTSensorMode.RAW.getByte());
            try {
                connection.send(command);
            } catch (MindstormsException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void deactivateAllEv3Sensors(MindstormsConnection connection) {
        EV3Command command = new EV3Command(connection.getCommandCounter(), EV3CommandType.DIRECT_COMMAND_NO_REPLY, 0, 0, EV3CommandOpCode.OP_INPUT_DEVICE);
        connection.incCommandCounter();
        command.append(EV3CommandByteCode.INPUT_DEVICE_STOP_ALL.getByte());
        command.append(EV3CommandParamFormat.PARAM_FORMAT_SHORT, -1);
        try {
            connection.send(command);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void registerOnSensorChangedListener(OnSensorChangedListener listener) {
        this.sensorChangedListeners.add(listener);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String preference) {
        List<String> sensorSettingKeys = new ArrayList();
        sensorSettingKeys.addAll(Arrays.asList(SettingsFragment.EV3_SENSORS));
        sensorSettingKeys.addAll(Arrays.asList(SettingsFragment.NXT_SENSORS));
        if (sensorSettingKeys.contains(preference)) {
            for (OnSensorChangedListener listener : this.sensorChangedListeners) {
                if (listener != null) {
                    listener.onSensorChanged();
                }
            }
        }
    }
}
