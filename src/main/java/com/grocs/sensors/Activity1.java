package com.grocs.sensors;

import static com.grocs.sensors.common.SensorConstants.DEF_PRECISION_STR;
import static com.grocs.sensors.common.SensorConstants.PROP_PRECISION;
import static com.grocs.sensors.common.SensorConstants.PROP_SENSOR_NAME;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.grocs.sensors.common.AbstractSensorDataManager;
import com.grocs.sensors.common.AllSensorDataManager;
import com.grocs.sensors.common.ISensorData;
import com.grocs.sensors.common.ISensorDescription;
import com.grocs.sensors.common.SensorConstants;
import com.grocs.sensors.common.SensorDataManagerListener;
import com.grocs.sensors.common.SensorEntry1;
import com.grocs.sensors.common.SensorEntryComparator;
import com.grocs.sensors.common.SensorUtilsInt;

/**
 */
public class Activity1 extends ListActivity implements
    SensorDataManagerListener {
  private static final boolean TRACE = false;
  private final String TAG = this.getClass().getSimpleName();
  private final int MENU_PREFS = 666;
  //
  private AbstractSensorDataManager fSM;
  // List adapter
  private EntryAdapter1 fAdapter;
  // Activity preferences
  private SharedPreferences fPrefs;
  /** array containing all sensorData */
  SensorEntry1[] fEntries;
  /** bitmap used to track the sensor(indices) that need to be refreshed. */
  boolean[] fRefreshMatrix;
  /** array containing string representations of all sensorData */
  String[][] fStringValues;
  /** listview as it is used by our ListActivity. */
  ListView fListView;
  /** . */
  private FloatFormatter fFormatter = new FloatFormatter();
  private String[] fFinalStringValues;

  @Override
  public void onCreate(Bundle bundle) {
    Log.i(TAG, "onCreate");
    super.onCreate(bundle);
    // get the listview
    fListView = getListView();
    // get the prefs
    fPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    // create our model object
    fSM = new AllSensorDataManager(
        (SensorManager) getSystemService(SENSOR_SERVICE));
    fEntries = createEntries(fSM.getSensors());
    // create the adapter
    fAdapter = new EntryAdapter1(this, fEntries);
    // sort the adapter: first by type, then by name
    fAdapter.sort(new SensorEntryComparator());
    // set the adapter (once !)
    setListAdapter(fAdapter);
    // init our other class members
    fRefreshMatrix = new boolean[fAdapter.getCount()];
    fStringValues = createStringValues();
    fFinalStringValues = new String[fAdapter.getCount()];
    // for test
    if (TRACE)
      android.os.Debug.startMethodTracing(TAG);
  }

  @Override
  protected void onResume() {
    Log.i(TAG, "onResume");
    super.onResume();
    // update the refresh rate - might have changed in the meantime...
    final String strDelay = fPrefs.getString(SensorConstants.PROP_REFRESH_RATE,
        SensorConstants.DEF_REFRESH_RATE_STR);
    fSM.setRefreshRate(Integer.parseInt(strDelay));
    // update the precision - might have changed in the meantime...
    final int precision = Integer.parseInt(fPrefs.getString(PROP_PRECISION,
        DEF_PRECISION_STR));
    fFormatter = new FloatFormatter(precision);
    //
    fSM.setPrecision(precision);
    // start listening
    fSM.addListener(this);
    // trigger a refresh to be sure all (pref) changes are applied
    fAdapter.notifyDataSetChanged();
  }

  @Override
  protected void onPause() {
    Log.i(TAG, "onPause");
    // stop listening
    fSM.removeListener(this);
    super.onPause();
  }

  @Override
  protected void onDestroy() {
    // Log.i(TAG, "onDestroy");
    if (TRACE)
      android.os.Debug.stopMethodTracing();
    super.onDestroy();
  }

  /***
   * MENU handling.
   ***/
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(Menu.NONE, MENU_PREFS, Menu.NONE, "Settings");
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case MENU_PREFS:
      startActivity(new Intent(this, SensorPreferenceActivity.class));
      return true;
    default:
      return super.onOptionsItemSelected(item);
    }
  }

  /***
   * SELECTION handling.
   ***/
  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    // fetch and prepare data
    final SensorEntry1 entry = fAdapter.getItem(position);
    if (!entry.isSection()) {
      final Bundle bundle = new Bundle();
      bundle.putString(PROP_SENSOR_NAME, entry.getSensorData().getSensor()
          .getName());
      // create and sent intent
      final Intent intent = new Intent(this, ActivityDetail1.class);
      intent.putExtras(bundle);
      startActivity(intent);
    }
  }

  /***
   * SENSOR handling.
   ***/
  @Override
  public void onUpdate(ISensorData[] data) {
    // Log.i(TAG, "onUpdate: " + data);
    final int first = fListView.getFirstVisiblePosition();
    final int last = fListView.getLastVisiblePosition();
    //
    updateRefreshMatrix(data, first, last);
    updateStrValues();
    //
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < fRefreshMatrix.length; ++i) {
          if (true == fRefreshMatrix[i]) {
            final int visualIndex = i - first;
            final View view = fListView.getChildAt(visualIndex);
            if (null != view) {
              TextView values = (TextView) view.getTag(R.id.values);
              if (null != values) {
                values.setText(fFinalStringValues[i]);
              }
            }
          }
        }
      }
    });
  }

  /***
   * PRIVATE section.
   ***/
  /** Assumes fAdapter is initialized & Sorted !!! */
  private String[][] createStringValues() {
    final int count = fAdapter.getCount();
    final String[][] res = new String[count][];
    for (int i = 0; i < count; ++i) {
      final SensorEntry1 entry = fAdapter.getItem(i);
      if (!entry.isSection()) {
        res[i] = new String[getNrOfExpectedValues(entry.getSensorData())];
      }
    }
    return res;
  }

  private void updateRefreshMatrix(ISensorData[] data, int start, int stop) {
    Arrays.fill(fRefreshMatrix, false);
    for (int i = 0; i < fEntries.length; ++i) {
      for (int j = 0; j < data.length; ++j) {
        if (data[j].equals(fEntries[i].getSensorData())) {
          if ((i >= start) && (i <= stop)) {
            fRefreshMatrix[i] = true;
          }
          break;
        }
      }
    }
  }

  private void updateStrValues() {
    for (int i = 0; i < fRefreshMatrix.length; ++i) {
      if (true == fRefreshMatrix[i]) {
        final SensorEntry1 entry = fAdapter.getItem(i);
        final float[] values = entry.getSensorData().getValues();
        for (int j = 0; j < fStringValues[i].length; ++j) {
          fStringValues[i][j] = fFormatter.doConvert(values[j]);
        }
      }
      fFinalStringValues[i] = Arrays.toString(fStringValues[i]);
    }
  }

  private int getNrOfExpectedValues(ISensorData data) {
    return data.getDescription().getValueDescriptions().length;
  }

  private SensorEntry1[] createEntries(ISensorData[] datae) {
    Set<SensorEntry1> entries = new HashSet<SensorEntry1>();
    Set<ISensorDescription> types = new HashSet<ISensorDescription>();
    for (ISensorData data : datae) {
      entries.add(new SensorEntry1(data));
      types.add(SensorUtilsInt.getDescription(data.getSensor().getType()));
    }
    Log.i(TAG, "typeEntries: " + types);
    for (ISensorDescription descr : types) {
      entries.add(new SensorEntry1(descr));
    }
    Log.i(TAG, "sensorEntries: " + entries);
    return entries.toArray(new SensorEntry1[entries.size()]);
  }
}
