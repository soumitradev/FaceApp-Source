package org.catrobat.catroid.devices.raspberrypi;

import android.util.Log;
import com.crashlytics.android.beta.Beta;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.RaspiInterruptScript;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;

public final class RaspberryPiService {
    private static final String TAG = RaspberryPiService.class.getSimpleName();
    private static RaspberryPiService instance;
    public RPiSocketConnection connection = null;
    private Map<String, GpioVersionType> gpioVersionMap = new HashMap();
    private Set<Integer> pinInterrupts = null;

    private enum GpioVersionType {
        SMALL_GPIO,
        BIG_GPIO,
        COMPUTE_MODULE
    }

    public static RaspberryPiService getInstance() {
        if (instance == null) {
            instance = new RaspberryPiService();
        }
        return instance;
    }

    private RaspberryPiService() {
        initGpioVersionMap();
        this.pinInterrupts = new HashSet();
    }

    public void addPinInterrupt(int pin) {
        this.pinInterrupts.add(Integer.valueOf(pin));
    }

    public boolean connect(String host, int port) {
        try {
            AsyncRPiTaskRunner rpi = new AsyncRPiTaskRunner();
            rpi.connect(host, port);
            this.connection = rpi.getConnection();
            if (rpi.getConnection().isConnected()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("connecting to ");
            stringBuilder.append(host);
            stringBuilder.append(":");
            stringBuilder.append(port);
            stringBuilder.append(" failed");
            stringBuilder.append(e);
            Log.e(str, stringBuilder.toString());
            return false;
        }
    }

    public void disconnect() {
        if (this.connection != null) {
            try {
                this.connection.disconnect();
                this.connection = null;
            } catch (IOException e) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception during disconnect: ");
                stringBuilder.append(e);
                Log.e(str, stringBuilder.toString());
            }
        }
        this.pinInterrupts.clear();
    }

    public ArrayList<Integer> getGpioList(String revision) {
        GpioVersionType version = (GpioVersionType) this.gpioVersionMap.get(revision);
        ArrayList<Integer> availableGPIOs = new ArrayList();
        if (version == GpioVersionType.SMALL_GPIO) {
            availableGPIOs.add(Integer.valueOf(3));
            availableGPIOs.add(Integer.valueOf(5));
            availableGPIOs.add(Integer.valueOf(7));
            availableGPIOs.add(Integer.valueOf(8));
            availableGPIOs.add(Integer.valueOf(10));
            availableGPIOs.add(Integer.valueOf(11));
            availableGPIOs.add(Integer.valueOf(12));
            availableGPIOs.add(Integer.valueOf(13));
            availableGPIOs.add(Integer.valueOf(15));
            availableGPIOs.add(Integer.valueOf(16));
            availableGPIOs.add(Integer.valueOf(18));
            availableGPIOs.add(Integer.valueOf(19));
            availableGPIOs.add(Integer.valueOf(21));
            availableGPIOs.add(Integer.valueOf(22));
            availableGPIOs.add(Integer.valueOf(23));
            availableGPIOs.add(Integer.valueOf(24));
            availableGPIOs.add(Integer.valueOf(26));
        } else if (version == GpioVersionType.BIG_GPIO) {
            availableGPIOs.add(Integer.valueOf(3));
            availableGPIOs.add(Integer.valueOf(5));
            availableGPIOs.add(Integer.valueOf(7));
            availableGPIOs.add(Integer.valueOf(8));
            availableGPIOs.add(Integer.valueOf(10));
            availableGPIOs.add(Integer.valueOf(11));
            availableGPIOs.add(Integer.valueOf(12));
            availableGPIOs.add(Integer.valueOf(13));
            availableGPIOs.add(Integer.valueOf(15));
            availableGPIOs.add(Integer.valueOf(16));
            availableGPIOs.add(Integer.valueOf(18));
            availableGPIOs.add(Integer.valueOf(19));
            availableGPIOs.add(Integer.valueOf(21));
            availableGPIOs.add(Integer.valueOf(22));
            availableGPIOs.add(Integer.valueOf(23));
            availableGPIOs.add(Integer.valueOf(24));
            availableGPIOs.add(Integer.valueOf(26));
            availableGPIOs.add(Integer.valueOf(29));
            availableGPIOs.add(Integer.valueOf(31));
            availableGPIOs.add(Integer.valueOf(32));
            availableGPIOs.add(Integer.valueOf(33));
            availableGPIOs.add(Integer.valueOf(35));
            availableGPIOs.add(Integer.valueOf(36));
            availableGPIOs.add(Integer.valueOf(37));
            availableGPIOs.add(Integer.valueOf(38));
            availableGPIOs.add(Integer.valueOf(40));
        } else {
            availableGPIOs.add(Integer.valueOf(3));
            availableGPIOs.add(Integer.valueOf(5));
            availableGPIOs.add(Integer.valueOf(7));
            availableGPIOs.add(Integer.valueOf(8));
            availableGPIOs.add(Integer.valueOf(10));
            availableGPIOs.add(Integer.valueOf(11));
            availableGPIOs.add(Integer.valueOf(12));
            availableGPIOs.add(Integer.valueOf(13));
            availableGPIOs.add(Integer.valueOf(15));
            availableGPIOs.add(Integer.valueOf(16));
            availableGPIOs.add(Integer.valueOf(18));
            availableGPIOs.add(Integer.valueOf(19));
            availableGPIOs.add(Integer.valueOf(21));
            availableGPIOs.add(Integer.valueOf(22));
            availableGPIOs.add(Integer.valueOf(23));
            availableGPIOs.add(Integer.valueOf(24));
            availableGPIOs.add(Integer.valueOf(26));
        }
        return availableGPIOs;
    }

    private void initGpioVersionMap() {
        this.gpioVersionMap.put("a01041", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("a21041", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("0013", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("0012", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("0011", GpioVersionType.COMPUTE_MODULE);
        this.gpioVersionMap.put("0010", GpioVersionType.BIG_GPIO);
        this.gpioVersionMap.put("000f", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("000e", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("000d", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0009", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0008", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0007", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0006", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0005", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0004", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0003", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put("0002", GpioVersionType.SMALL_GPIO);
        this.gpioVersionMap.put(Beta.TAG, GpioVersionType.SMALL_GPIO);
    }

    public void enableRaspberryInterruptPinsForProject(Project project) {
        for (Scene scene : project.getSceneList()) {
            for (Sprite sprite : scene.getSpriteList()) {
                for (Script script : sprite.getScriptList()) {
                    if (script instanceof RaspiInterruptScript) {
                        getInstance().addPinInterrupt(Integer.parseInt(((RaspiInterruptScript) script).getPin()));
                    }
                }
            }
        }
    }

    public boolean isValidPin(String revision, int pinNumber) {
        return getGpioList(revision).contains(Integer.valueOf(pinNumber));
    }

    public Set<Integer> getPinInterrupts() {
        return this.pinInterrupts;
    }
}
