package com.grocs.sensors;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
  private static final Class<?>[] ACTIVITIES = getChildActivities();

  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setListAdapter(new ArrayAdapter<Class<?>>(this,
        android.R.layout.simple_list_item_1, ACTIVITIES));
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    startActivity(new Intent(this, ACTIVITIES[position]));
  }

  private static Class<?>[] getChildActivities() {
    return new Class<?>[] { Activity0.class, Activity1.class };
  }
}
