package com.grocs.sensors;

import static com.grocs.sensors.common.SensorConstants.DEF_PRECISION_STR;
import static com.grocs.sensors.common.SensorConstants.PROP_PRECISION;

import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.hardware.Sensor;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.grocs.sensors.common.AbstractSensorDataManager;
import com.grocs.sensors.common.ISensorData;
import com.grocs.sensors.common.ISensorDescription;

public class EntryAdapter0 extends ArrayAdapter<ISensorData> implements
    OnSharedPreferenceChangeListener {
  private final String TAG = this.getClass().getSimpleName();
  // Shared preferences
  private final SharedPreferences fPrefs;
  // Formatter
  private FloatFormatter fFormatter = new FloatFormatter();

  /**
   * @param context
   * @param textViewResourceId
   * @param fSM
   *          .getSensors()
   * @param fSM
   */
  public EntryAdapter0(Context context, int textViewResourceId,
      AbstractSensorDataManager sm) {
    super(context, textViewResourceId, sm.getSensors());
    // get the prefs / register ourselves as listener
    fPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    fPrefs.registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final ISensorData sensorData = getItem(position);
    final Sensor sensor = sensorData.getSensor();
    final ISensorDescription description = sensorData.getDescription();
    final String sensorName = sensor.getName();
    View row = convertView;
    // first time entry for a given listItem.
    if (null == row) {
      LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
          Context.LAYOUT_INFLATER_SERVICE);
      row = vi.inflate(R.layout.sensor_row0, parent, false);
      // construct/tag the static fields
      row.setTag(R.id.sensorName, row.findViewById(R.id.sensorName));
      row.setTag(R.id.sensorType, row.findViewById(R.id.sensorType));
      // construct/tag the dynamic fields
      row.setTag(R.id.values, row.findViewById(R.id.values));
    }
    final String detail = sensorData.getDescription().getType();
    // the static part
    final TextView sensorNameV = (TextView) row.getTag(R.id.sensorName);
    final TextView sensorTypeV = (TextView) row.getTag(R.id.sensorType);
    final TextView sensorValV = (TextView) row.getTag(R.id.values);
    // fill in the values
    sensorNameV.setText(sensorName);
    final String unit = description.getUnit();
    sensorTypeV.setText(detail + (unit.length() == 0 ? "" : " (" + unit + ")"));
    // the dynamic part
    final float[] values = sensorData.getValues();
    final String[] fStringValues = new String[values.length];
    for (int j = 0; j < values.length; ++j) {
      fStringValues[j] = fFormatter.doConvert(values[j]);
    }
    sensorValV.setText(Arrays.toString(fStringValues));
    return row;
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
      String key) {
    if (key.equals(PROP_PRECISION)) {
      final int precision = Integer.parseInt(fPrefs.getString(PROP_PRECISION,
          DEF_PRECISION_STR));
      fFormatter = new FloatFormatter(precision);
    }
  }
}
