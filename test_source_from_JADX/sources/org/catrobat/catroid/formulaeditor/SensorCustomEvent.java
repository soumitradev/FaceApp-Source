package org.catrobat.catroid.formulaeditor;

public class SensorCustomEvent {
    public Sensors sensor;
    public long timestamp = System.currentTimeMillis();
    public final float[] values;

    public SensorCustomEvent(Sensors sourceSensor, float[] values) {
        this.sensor = sourceSensor;
        this.values = new float[values.length];
        System.arraycopy(values, 0, this.values, 0, values.length);
    }
}
