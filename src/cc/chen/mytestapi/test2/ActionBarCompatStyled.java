package cc.chen.mytestapi.test2;

import cc.chen.mytestapi.R;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

@SuppressWarnings("deprecation")
public class ActionBarCompatStyled extends AppCompatActivity implements TabListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actionbar_compat_styled_main);

		// Set the Action Bar to use tabs for navigation
		ActionBar ab = getSupportActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Add three tabs to the Action Bar for display
		ab.addTab(ab.newTab().setText("Tab 1").setTabListener(this));
		ab.addTab(ab.newTab().setText("Tab 2").setTabListener(this));
		ab.addTab(ab.newTab().setText("Tab 3").setTabListener(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu from menu resource (res/menu/main)
		getMenuInflater().inflate(R.menu.actionbar_compat_styled_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	// Implemented from ActionBar.TabListener
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// This is called when a tab is selected.
	}

	// Implemented from ActionBar.TabListener
	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// This is called when a previously selected tab is unselected.
	}

	// Implemented from ActionBar.TabListener
	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// This is called when a previously selected tab is selected again.
	}
}
