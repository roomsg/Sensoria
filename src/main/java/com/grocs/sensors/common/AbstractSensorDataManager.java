package com.grocs.sensors.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

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
public abstract class AbstractSensorDataManager implements SensorEventListener {
  final String TAG = this.getClass().getSimpleName();
  // final, main members
  private final SensorManager fSM;
  private final SensorData[] fSensors;
  private final Set<SensorDataManagerListener> fListeners;
  // TODO - checkout this mechanism !
  private final AtomicBoolean fStopRequest = new AtomicBoolean();
  private final float[] fTempValues;
  private final List<SensorData> fTempEvents;
  private Thread fWorker;
  // some class values to be constantly reused (avoiding a lot of 'new')
  private int fRefreshDelay = SensorConstants.DEF_REFRESH_RATE;
  private FloatConvertor fConvertor;

  /**
   * Constructor
   * 
   * @param sm
   *          SensorManager
   */
  public AbstractSensorDataManager(final SensorManager sm) {
    fSM = sm;
    fListeners = new CopyOnWriteArraySet<SensorDataManagerListener>();
    fConvertor = new FloatConvertor();
    fSensors = new SensorCollector(fSM).getSensors();
    fTempEvents = new ArrayList<SensorData>(fSensors.length);
    fTempValues = new float[SensorUtilsInt.getMaxNrOfExpectedValues()];
  }

  public ISensorData[] getSensors() {
    return fSensors;
  }

  public void setRefreshRate(final int delay) {
    fRefreshDelay = delay;
  }

  public void setPrecision(final int precision) {
    fConvertor = new FloatConvertor(precision);
  }

  public synchronized void addListener(final SensorDataManagerListener listener) {
    boolean hadListeners = anyListeners();
    if (!hadListeners && fListeners.add(listener)) {
      startListening();
      startWorkerThread();
    }
  }

  public synchronized void removeListener(SensorDataManagerListener listener) {
    fListeners.remove(listener);
    if (!anyListeners()) {
      stopListening();
      stopWorkerThread();
    }
  }

  /**
   * Implement this filter to define which sensor(s) you want to be traced.
   * 
   * @param data
   *          SensorData
   * @return true if needed to follow, false if not
   */
  abstract boolean filter(ISensorData data);

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

  private void startListening() {
    for (ISensorData data : fSensors) {
      if (filter(data)) {
        fSM.registerListener(this, data.getSensor(),
            SensorManager.SENSOR_DELAY_NORMAL);
      }
    }
  }

  private void stopListening() {
    for (ISensorData data : fSensors) {
      fSM.unregisterListener(this, data.getSensor());
    }
  }

  private synchronized boolean anyListeners() {
    return !fListeners.isEmpty();
  }

  private synchronized void startWorkerThread() {
    Log.i(TAG, "startWorkerThread");
    if (null != fWorker) {
      return;
    }
    fWorker = new Thread(new Runnable() {
      @Override
      public void run() {
        Log.i(TAG, "start to run !");
        do {
          // deliver all in one step
          processEvents();
          // let's take some time off..
          try {
            Thread.sleep(fRefreshDelay);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } while (!fStopRequest.get());
        fWorker = null;
        fStopRequest.set(false);
        Log.i(TAG, "stop to run !");
      }
    });
    fWorker.start();
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
      triggerListeners(actuals);
    }
  }

  private synchronized void stopWorkerThread() {
    Log.i(TAG, "stopWorkerThread");
    if (null == fWorker) {
      return;
    }
    fStopRequest.set(true);
    fWorker = null;
  }

  private SensorData retrieveSensor(Sensor sensor) {
    return SensorUtilsInt.retrieveSensor(Arrays.asList(fSensors), sensor);
  }

  private void convert(float[] valuesIn, float[] valuesOut) {
    for (int i = 0; i < valuesIn.length && i < valuesOut.length; ++i) {
      valuesOut[i] = fConvertor.doConvert(valuesIn[i]);
    }
  }

  private synchronized void triggerListeners(ISensorData[] actuals) {
    // Log.i(TAG, "trigger " + actuals.length + " listeners.");
    for (SensorDataManagerListener listener : fListeners) {
      listener.onUpdate(actuals);
    }
  }

  /**
   * only usefull ico all sensor values are required when not all sensors have
   * listeners... TODO - to evaluate/revise
   */
  // private void startup() {
  // addListener(new SensorDataManagerListener() {
  // @Override
  // public void onUpdate(ISensorData[] datae) {
  // boolean bOKToUnregister = true;
  // // check if all sensors have actual values
  // for (ISensorData data : fSensors) {
  // // TODO - not part of the contract, yet empty array depicts
  // // uninitialised values
  // float[] values = data.getValues();
  // if (values.length == 0) {
  // bOKToUnregister = false;
  // break;
  // }
  // }
  // if (bOKToUnregister) {
  // removeListener(this);
  // }
  // }
  // });
  // }
}
