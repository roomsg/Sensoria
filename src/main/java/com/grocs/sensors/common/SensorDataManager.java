package com.grocs.sensors.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * This class is a wrapper class on top of the 'standard' SensorManager. Main
 * differences:
 * <ul>
 * <li>accuracy of sensor values is reduced/editable</li>
 * <li>refresh rate is reduced/editable</li>
 * <li>listeners will only be triggered upon actual *changes* in sensor values</li>
 * </ul>
 *
 * @author ladmin
 */
public class SensorDataManager implements SensorEventListener {
    final String TAG = "SensorDataManager";
    // final, main members
    private final SensorManager sm;
    private final SensorData[] fSensors;
    private final SensorDataManagerListener listener;
    private final float[] fTempValues;
    private final List<SensorData> fTempEvents;
    private final Timer timer = new Timer();
    private TimerTask task;
    // some class values to be constantly reused (avoiding a lot of 'new')
    private int fRefreshDelay = SensorConstants.DEF_REFRESH_RATE;
    private FloatConvertor fConvertor;
    private final Object listenerMutex = new Object();
    /**
     * Constructor
     *
     * @param sm SensorManager
     */
    public SensorDataManager(final SensorManager sm, SensorFilter filter, SensorDataManagerListener listener) {
        this.sm = sm;
        this.listener = listener;
        fConvertor = new FloatConvertor();
        fSensors = new SensorCollector(this.sm, filter).getSensors();
        fTempEvents = new ArrayList<SensorData>(fSensors.length);
        fTempValues = new float[SensorUtils.getMaxNrOfExpectedValues()];
    }

    public ISensorData[] getSensors() {
        return fSensors;
    }

    public void setRefreshRate(final int delay) {
        fRefreshDelay = Math.max(delay, 1);
    }

    public void setPrecision(final int precision) {
        fConvertor = new FloatConvertor(precision);
    }

    public synchronized void start() {
        Log.i(TAG, "start");
        // start handling data
        task = new DataHandler();
        timer.schedule(task, 0, fRefreshDelay);
        // register listeners
        doRegisterListeners();
    }

    public synchronized void stop() {
        Log.i(TAG, "stop");
        // stop our timer(task)
        task.cancel();
        // unregister listeners
        doUnregisterListeners();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final SensorData data = retrieveSensor(event.sensor);
        if (null != data) {
            convert(event.values, fTempValues);
            data.update(fTempValues);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        final SensorData data = retrieveSensor(sensor);
        if (null != data) {
            data.update(accuracy);
        }
    }

    // we're handling this async since registering listeners seemingly can take quite some time (since > 4.3 ?)
    private void doRegisterListeners() {
        final SensorEventListener listener = this;
        Log.i(TAG, "doRegisterListeners");
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (listenerMutex) {
                    for (ISensorData data : fSensors) {
                        Log.i(TAG, "doRegisterListeners on " + data.getDescription());
                        sm.registerListener(listener, data.getSensor(),
                                SensorManager.SENSOR_DELAY_NORMAL);
                    }
                }
            }
        }).start();
    }

    // we're handling this async since unregistering listeners seemingly can take quite some time (since > 4.3 ?)
    private void doUnregisterListeners() {
        final SensorEventListener listener = this;
        Log.i(TAG, "doUnregisterListeners-1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (listenerMutex) {
                    for (ISensorData data : fSensors) {
                        Log.i(TAG, "doUnregisterListeners on " + data.getDescription());
                        sm.unregisterListener(listener, data.getSensor());
                    }
                }
            }
        }).start();
        Log.i(TAG, "doUnregisterListeners-2");
    }

    private void processEvents() {
        // clear our member collection
        fTempEvents.clear();
        //
        for (SensorData data : fSensors) {
            if (data.getAndSetDirty(false)) {
                fTempEvents.add(data);
            }
        }
        if (!fTempEvents.isEmpty()) {
            final SensorData[] actuals = fTempEvents
                    .toArray(new SensorData[fTempEvents.size()]);
            listener.onUpdate(actuals);
        }
    }

    private SensorData retrieveSensor(Sensor sensor) {
        return SensorUtils.retrieveSensor(fSensors, sensor);
    }

    private void convert(float[] valuesIn, float[] valuesOut) {
        for (int i = 0; i < valuesIn.length && i < valuesOut.length; ++i) {
            valuesOut[i] = fConvertor.doConvert(valuesIn[i]);
        }
    }

    /**
     * TimerTask doing the processing of all data.
     */
    class DataHandler extends TimerTask {
        @Override
        public void run() {
            processEvents();
        }
    }
}

