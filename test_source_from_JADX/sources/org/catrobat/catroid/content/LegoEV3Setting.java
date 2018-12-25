package org.catrobat.catroid.content;

import java.io.Serializable;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor.Sensor;

public class LegoEV3Setting implements Setting {
    private static final long serialVersionUID = 1;
    private EV3Port[] portSensorMapping;

    public static class EV3Port implements Serializable {
        private int number;
        private Sensor sensor;

        public EV3Port(int number, Sensor sensor) {
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

    public LegoEV3Setting(Sensor[] sensorMapping) {
        this.portSensorMapping = null;
        this.portSensorMapping = new EV3Port[4];
        for (int i = 0; i < this.portSensorMapping.length; i++) {
            this.portSensorMapping[i] = new EV3Port(i, sensorMapping[i]);
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
