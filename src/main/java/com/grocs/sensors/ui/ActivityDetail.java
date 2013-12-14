package com.grocs.sensors.ui;

import static com.grocs.sensors.common.SensorConstants.PROP_SENSOR_NAME;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.grocs.sensors.common.SensorDataManager;
import com.grocs.sensors.common.ISensorData;
import com.grocs.sensors.common.ISensorDescription;
import com.grocs.sensors.common.SensorUtils;
import com.grocs.sensors.common.SensorFilter;

public class ActivityDetail extends ListActivity {
    private static final boolean TRACE = false;
    private final String TAG = this.getClass().getSimpleName();
    private final int MENU_PREFS = 666;
    // List adapter
    private ArrayAdapter<String> fAdapter;
    /**
     * array containing all sensorDetails
     */
    String[] fDetails;
    /**
     * listview as it is used by our ListActivity.
     */
    ListView fListView;
    private SensorDataManager fSM;

    @Override
    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate");
        super.onCreate(bundle);
        // get the listview
        fListView = getListView();
        // get the sensor name from the intent
        final String name = getIntent().getStringExtra(PROP_SENSOR_NAME);
        // create our model object
        fSM = new SensorDataManager(
                (SensorManager) getSystemService(SENSOR_SERVICE), new SensorFilter.NameSensorFilter(name), null);
        ISensorData[] sensors = fSM.getSensors();
        ISensorData sensor = null;
        for (ISensorData sensordata : sensors) {
            if (sensordata.getSensor().getName().equals(name)) {
                sensor = sensordata;
                break;
            }
        }
        fDetails = createDetails(sensor);
        // create the adapter
        fAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, fDetails);
        // set the adapter (once !)
        setListAdapter(fAdapter);
        // for test
        if (TRACE)
            android.os.Debug.startMethodTracing(TAG);
    }

    private String[] createDetails(ISensorData sensorData) {
        final Sensor sensor = sensorData.getSensor();
        List<String> details = new ArrayList<String>();
        details.add("Name: " + sensor.getName());
        details.add("Vendor: " + sensor.getVendor());
        details.add("Version: " + sensor.getVersion());
        final ISensorDescription description = SensorUtils.getDescription(sensor
                .getType());
        details.add("Type: " + description.getType());
        details.add("RawType: " + String.valueOf(sensor.getType()));
        details.add("Default: " + sensorData.isDefault());
        details.add("Unit: " + description.getUnit());
        // description.getValueDescriptions()

        // TODO - variable, I guess
        details.add("Power: " + sensor.getPower());
        // details.add("Minimum Delay: " + sensor.getMinDelay());
        details.add("Maximum Range: " + sensor.getMaximumRange());
        return details.toArray(new String[details.size()]);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    /***
     * MENU handling.
     ***/
    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    // menu.add(Menu.NONE, MENU_PREFS, Menu.NONE, "Settings");
    // return super.onCreateOptionsMenu(menu);
    // }
    //
    // @Override
    // public boolean onOptionsItemSelected(MenuItem item) {
    // switch (item.getItemId()) {
    // case MENU_PREFS:
    // startActivity(new Intent(this, SensorPreferenceActivity.class));
    // return true;
    // default:
    // return super.onOptionsItemSelected(item);
    // }
    // }

    /***
     * SENSOR handling.
     ***/
    // @Override
    // public void onUpdate(ISensorData[] data) {
    // // Log.i(TAG, "onUpdate: " + data);
    // final int first = fListView.getFirstVisiblePosition();
    // final int last = fListView.getLastVisiblePosition();
    // //
    // updateRefreshMatrix(data, first, last);
    // updateStrValues();
    // //
    // runOnUiThread(new Runnable() {
    // @Override
    // public void run() {
    // for (int i = 0; i < fRefreshMatrix.length; ++i) {
    // if (true == fRefreshMatrix[i]) {
    // final int visualIndex = i - first;
    // final View view = fListView.getChildAt(visualIndex);
    // if (null != view) {
    // TextView values = (TextView) view.getTag(R.id.values);
    // if (null != values) {
    // values.setText(fFinalStringValues[i]);
    // }
    // }
    // }
    // }
    // }
    // });
    // }

    /***
     * PRIVATE section.
     ***/
    /** Assumes fAdapter is initialized & Sorted !!! */
    // private String[][] createStringValues() {
    // final int count = fAdapter.getCount();
    // final String[][] res = new String[count][];
    // for (int i = 0; i < count; ++i) {
    // final SensorEntry1 entry = fAdapter.getItem(i);
    // if (!entry.isSection()) {
    // res[i] = new String[getNrOfExpectedValues(entry.getSensorData())];
    // }
    // }
    // return res;
    // }

    // private void updateRefreshMatrix(ISensorData[] data, int start, int stop) {
    // Arrays.fill(fRefreshMatrix, false);
    // for (int i = 0; i < fEntries.length; ++i) {
    // for (int j = 0; j < data.length; ++j) {
    // if (data[j].equals(fEntries[i].getSensorData())) {
    // if ((i >= start) && (i <= stop)) {
    // fRefreshMatrix[i] = true;
    // }
    // break;
    // }
    // }
    // }
    // }

    // private void updateStrValues() {
    // for (int i = 0; i < fRefreshMatrix.length; ++i) {
    // if (true == fRefreshMatrix[i]) {
    // final SensorEntry1 entry = fAdapter.getItem(i);
    // final float[] values = entry.getSensorData().getValues();
    // for (int j = 0; j < fStringValues[i].length; ++j) {
    // fStringValues[i][j] = fFormatter.doConvert(values[j]);
    // }
    // }
    // fFinalStringValues[i] = Arrays.toString(fStringValues[i]);
    // }
    // }

    // private int getNrOfExpectedValues(ISensorData data) {
    // return data.getDescription().getValueDescriptions().length;
    // }

    // private SensorEntry1[] createEntries(ISensorData[] datae) {
    // Set<SensorEntry1> entries = new HashSet<SensorEntry1>();
    // Set<Integer> types = new HashSet<Integer>();
    // for (ISensorData data : datae) {
    // entries.add(new SensorEntry1(data));
    // types.add(data.getSensor().getType());
    // }
    // for (Integer type : types) {
    // entries.add(new SensorEntry1(SensorUtils.getDescription(type)));
    // }
    // return entries.toArray(new SensorEntry1[entries.size()]);
    // }
}
