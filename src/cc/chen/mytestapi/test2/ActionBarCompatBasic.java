package cc.chen.mytestapi.test2;

import cc.chen.mytestapi.R;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

@SuppressWarnings("deprecation")
public class ActionBarCompatBasic extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actionbar_compat_basic_main);
	}

	/**
	 * Use this method to instantiate your menu, and add your items to it. You
	 * should return true if you have added items to it and want the menu to be
	 * displayed.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate our menu from the resources by using the menu inflater.
		getMenuInflater().inflate(R.menu.actionbar_compat_basic_menu, menu);

		// It is also possible add items here. Use a generated id from
		// resources (ids.xml) to ensure that all menu ids are distinct.
		MenuItem locationItem = menu.add(0, R.id.menu_location, 0, R.string.menu_location);
		locationItem.setIcon(R.drawable.ic_action_location);

		// Need to use MenuItemCompat methods to call any action item related
		// methods
		MenuItemCompat.setShowAsAction(locationItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	/**
	 * This method is called when one of the menu items to selected. These items
	 * can be on the Action Bar, the overflow menu, or the standard options
	 * menu. You should return true if you handle the selection.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			// Here we might start a background refresh task
			return true;

		case R.id.menu_location:
			// Here we might call LocationManager.requestLocationUpdates()
			return true;

		case R.id.menu_settings:
			// Here we would open up our settings activity
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
