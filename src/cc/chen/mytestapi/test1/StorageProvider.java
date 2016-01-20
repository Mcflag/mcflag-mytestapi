package cc.chen.mytestapi.test1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;
import cc.chen.mytestapi.logger.Log;

@SuppressLint("NewApi")
public class StorageProvider extends BaseActivity {
	public static final String FRAGTAG = "MyCloudFragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.storage_provider_main);

		if (getSupportFragmentManager().findFragmentByTag(FRAGTAG) == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			MyCloudFragment fragment = new MyCloudFragment();
			transaction.add(fragment, FRAGTAG);
			transaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.storage_provider_menu, menu);
		return true;
	}

	/**
	 * Toggles the user's login status via a login menu option, and
	 * enables/disables the cloud storage content provider.
	 */
	public class MyCloudFragment extends Fragment {

		private static final String TAG = "MyCloudFragment";
		private static final String AUTHORITY = "cc.chen.mytestapi.documents";
		private boolean mLoggedIn = false;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mLoggedIn = readLoginValue();

			setHasOptionsMenu(true);
		}

		@Override
		public void onPrepareOptionsMenu(Menu menu) {
			super.onPrepareOptionsMenu(menu);
			MenuItem item = menu.findItem(R.id.sample_action);
			item.setTitle(mLoggedIn ? R.string.log_out : R.string.log_in);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == R.id.sample_action) {
				toggleLogin();
				item.setTitle(mLoggedIn ? R.string.log_out : R.string.log_in);

				// Notify the system that the status of our roots has changed.
				// This will trigger
				// a call to MyCloudProvider.queryRoots() and force a refresh of
				// the system
				// picker UI. It's important to call this or stale results may
				// persist.
				getActivity().getContentResolver()
						.notifyChange(DocumentsContract.buildRootsUri(AUTHORITY), null, false);

			}
			return true;
		}

		/**
		 * Dummy function to change the user's authorization status.
		 */
		private void toggleLogin() {
			// Replace this with your standard method of authentication to
			// determine if your app
			// should make the user's documents available.
			mLoggedIn = !mLoggedIn;
			writeLoginValue(mLoggedIn);
			Log.i(TAG, getString(mLoggedIn ? R.string.logged_in_info : R.string.logged_out_info));
		}

		/**
		 * Dummy function to save whether the user is logged in.
		 */
		private void writeLoginValue(boolean loggedIn) {
			final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
					getString(R.string.app_name), Activity.MODE_PRIVATE);
			sharedPreferences.edit().putBoolean(getString(R.string.key_logged_in), loggedIn).commit();
		}

		/**
		 * Dummy function to determine whether the user is logged in.
		 */
		private boolean readLoginValue() {
			final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
					getString(R.string.app_name), Activity.MODE_PRIVATE);
			return sharedPreferences.getBoolean(getString(R.string.key_logged_in), false);
		}

	}
}
