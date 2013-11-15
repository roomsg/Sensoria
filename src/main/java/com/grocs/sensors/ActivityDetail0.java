package com.grocs.sensors;

import static com.grocs.sensors.common.SensorConstants.DEF_PRECISION_STR;
import static com.grocs.sensors.common.SensorConstants.DEF_REFRESH_RATE_STR;
import static com.grocs.sensors.common.SensorConstants.PROP_PRECISION;
import static com.grocs.sensors.common.SensorConstants.PROP_REFRESH_RATE;
import static com.grocs.sensors.common.SensorConstants.PROP_SENSOR_NAME;

import java.util.Arrays;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.grocs.sensors.common.AbstractSensorDataManager;
import com.grocs.sensors.common.FloatConvertor;
import com.grocs.sensors.common.ISensorData;
import com.grocs.sensors.common.SensorDataManagerListener;
import com.grocs.sensors.common.SingleSensorDataManager;

public class ActivityDetail0 extends Activity implements
    SensorDataManagerListener {
  //
  private final String TAG = this.getClass().getSimpleName();
  private AbstractSensorDataManager fSM;
  // Activity preferences
  private SharedPreferences fPrefs;
  FloatConvertor fConverter;
  //
  private ISensorData fSensor;
  private TextView fWIP;
  private TextView fTxtName;
  private TextView fTxtValues;
  // private float[] fCurrAdapted;
  private FloatFormatter fFormatter = new FloatFormatter();
  private final static String WIP = "Watch out for the next release since "
      + "only then you wil see something \'sensible\' here !\n\n"
      + "Expect all details for the given sensor ..\n\n";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.i(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    // get prefs
    fPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    // get the sensor name from the intent
    final String name = getIntent().getStringExtra(PROP_SENSOR_NAME);
    // create our model object
    fSM = new SingleSensorDataManager(
        (SensorManager) getSystemService(SENSOR_SERVICE), name);
    fSensor = SensorUtils.retrieveSensor(fSM.getSensors(), name);
    // Get the sensor we're supposed to show the details from.
    setContentView(R.layout.activity_sensor_detail0);
    fWIP = (TextView) findViewById(R.id.textView0);
    fTxtName = (TextView) findViewById(R.id.textName);
    // fTxtValues = (TextView) findViewById(R.id.textValues);
  }

  @Override
  protected void onResume() {
    Log.i(TAG, "onResume");
    super.onResume();
    // update the refresh rate - might have changed in the meantime...
    final String strDelay = fPrefs.getString(PROP_REFRESH_RATE,
        DEF_REFRESH_RATE_STR);
    fSM.setRefreshRate(Integer.parseInt(strDelay));
    // update the precision - might have changed in the meantime...
    final int precision = Integer.parseInt(fPrefs.getString(PROP_PRECISION,
        DEF_PRECISION_STR));
    fFormatter = new FloatFormatter(precision);
    //
    fSM.addListener(this);
    //
    fWIP.setText(WIP);
    fTxtName.setText(fSensor.getSensor().getName());
  }

  @Override
  protected void onPause() {
    Log.i(TAG, "onPause");
    fSM.removeListener(this);
    super.onPause();
  }

  @Override
  protected void onDestroy() {
    Log.i(TAG, "onDestroy");
    super.onDestroy();
  }

  @Override
  public void onUpdate(ISensorData[] datae) {
    for (ISensorData data : datae) {
      if (data.equals(fSensor)) {
        final float[] values = data.getValues();
        refreshUI(values);
      }
    }
  }

  private void refreshUI(float[] newValues) {
    final String out = Arrays.toString(newValues);
    Log.i(TAG, "new values : " + out);
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        // fTxtValues.setText(out);
      }
    });
  }
}
