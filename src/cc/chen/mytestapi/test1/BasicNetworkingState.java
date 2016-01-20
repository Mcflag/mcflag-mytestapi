package cc.chen.mytestapi.test1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;
import cc.chen.mytestapi.fragments.SimpleTextFragment;
import cc.chen.mytestapi.logger.Log;

public class BasicNetworkingState extends BaseActivity {
	// Whether there is a Wi-Fi connection.
	private static boolean wifiConnected = false;
	// Whether there is a mobile connection.
	private static boolean mobileConnected = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_networking_main);

		// Initialize text fragment that displays intro text.
		SimpleTextFragment introFragment = (SimpleTextFragment) getSupportFragmentManager().findFragmentById(
				R.id.intro_fragment);
		introFragment.setText(R.string.basic_networking_intro);
		introFragment.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0f);

		// Initialize the logging framework.
		initializeLogging();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.basic_networking_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// When the user clicks TEST, display the connection status.
		case R.id.test_action:
			checkNetworkConnection();
			return true;
			// Clear the log view fragment.
		case R.id.clear_action:
			logFragment.getLogView().setText("");
			return true;
		}
		return false;
	}

	/**
	 * Check whether the device is connected, and if so, whether the connection
	 * is wifi or mobile (it could be something else).
	 */
	private void checkNetworkConnection() {

		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
		if (activeInfo != null && activeInfo.isConnected()) {
			wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
			mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
			if (wifiConnected) {
				Log.i(TAG, getString(R.string.wifi_connection));
			} else if (mobileConnected) {
				Log.i(TAG, getString(R.string.mobile_connection));
			}
		} else {
			Log.i(TAG, getString(R.string.no_wifi_or_mobile));
		}

	}

}
