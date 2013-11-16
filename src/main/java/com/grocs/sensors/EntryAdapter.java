package com.grocs.sensors;

import static com.grocs.sensors.common.SensorConstants.DEF_PRECISION_STR;
import static com.grocs.sensors.common.SensorConstants.PROP_PRECISION;

import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.grocs.sensors.common.ISensorData;
import com.grocs.sensors.common.SensorEntry;

public class EntryAdapter extends ArrayAdapter<SensorEntry> implements
    OnSharedPreferenceChangeListener {
  private static final int ITEM_TYPE_SECTION = 0;
  private static final int ITEM_TYPE_SENSOR = 1;
  private static final int ITEM_TYPE_COUNT = 2;

  // private final String TAG = this.getClass().getSimpleName();
  // Shared preferences
  private final SharedPreferences fPrefs;
  // Formatter
  private FloatFormatter fFormatter = new FloatFormatter();
  //
  LayoutInflater fVi;

  /**
   * @param context
   * @param fSM
   *          .getSensors()
   * @param fSM
   */
  public EntryAdapter(Context context, SensorEntry[] fEntries) {
    super(context, 0, fEntries);
    // get the prefs / register ourselves as listener
    fPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    fPrefs.registerOnSharedPreferenceChangeListener(this);
    //
    fVi = (LayoutInflater) getContext().getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final SensorEntry sensorEntry = getItem(position);
    final int type = getItemViewType(position);
    View row = convertView;
    if (null == row) {
      if (ITEM_TYPE_SECTION == type) {
        row = fVi.inflate(R.layout.sensor_section1, parent, false);
        row.setTag(R.id.sensorTypeName, row.findViewById(R.id.sensorTypeName));
        row.setTag(R.id.sensorUnit, row.findViewById(R.id.sensorUnit));
      } else {
        row = fVi.inflate(R.layout.sensor_row1, parent, false);
        row.setTag(R.id.sensorName, row.findViewById(R.id.sensorName));
        row.setTag(R.id.values, row.findViewById(R.id.values));
      }
    }

    if (ITEM_TYPE_SECTION == type) {
      final TextView sensorTypeName = (TextView) row
          .getTag(R.id.sensorTypeName);
      final TextView sensorUnit = (TextView) row.getTag(R.id.sensorUnit);
      //
      sensorTypeName.setText(sensorEntry.getName());
      final String unit = sensorEntry.getSensorDescription().getUnit();
      sensorUnit.setText(unit.length() == 0 ? "" : " (" + unit + ")");
    } else {
      final TextView sensorNameV = (TextView) row.getTag(R.id.sensorName);
      final TextView sensorValV = (TextView) row.getTag(R.id.values);
      //
      final ISensorData data = sensorEntry.getSensorData();
      final String sensorName = data.getSensor().getName();
      sensorNameV.setText(sensorName);
      final float[] values = data.getValues();
      final String[] fStringValues = new String[values.length];
      for (int j = 0; j < values.length; ++j) {
        fStringValues[j] = fFormatter.doConvert(values[j]);
      }
      sensorValV.setText(Arrays.toString(fStringValues));
    }
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

  @Override
  public int getItemViewType(int position) {
    return getItem(position).isSection() ? ITEM_TYPE_SECTION : ITEM_TYPE_SENSOR;
  }

  @Override
  public int getViewTypeCount() {
    return ITEM_TYPE_COUNT;
  }

  @Override
  public boolean isEnabled(int position) {
    return !getItem(position).isSection();
  }
}
