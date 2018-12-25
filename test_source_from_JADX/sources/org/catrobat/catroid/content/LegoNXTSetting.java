package org.catrobat.catroid.content;

import java.io.Serializable;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor.Sensor;

public class LegoNXTSetting implements Setting {
    private static final long serialVersionUID = 1;
    private NXTPort[] portSensorMapping;

    public static class NXTPort implements Serializable {
        private int number;
        private Sensor sensor;

        public NXTPort(int number, Sensor sensor) {
            this.number = number;
            this.sensor = sensor;
        }

        public Sensor getSensor() {
            return this.sensor;
        }

        public void setSensor(Sensor sensor) {
            this.sensor = sensor;
        }

        public int getNumber() {
            return this.number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    public LegoNXTSetting(Sensor[] sensorMapping) {
        this.portSensorMapping = null;
        this.portSensorMapping = new NXTPort[4];
        for (int i = 0; i < this.portSensorMapping.length; i++) {
            this.portSensorMapping[i] = new NXTPort(i, sensorMapping[i]);
        }
    }

    public void updateMapping(Sensor[] sensorMapping) {
        for (int i = 0; i < this.portSensorMapping.length; i++) {
            this.portSensorMapping[i].setNumber(i);
            this.portSensorMapping[i].setSensor(sensorMapping[i]);
        }
    }

    public Sensor[] getSensorMapping() {
        Sensor[] sensorMapping = new Sensor[4];
        for (int i = 0; i < this.portSensorMapping.length; i++) {
            sensorMapping[i] = this.portSensorMapping[i].getSensor();
        }
        return sensorMapping;
    }
}
