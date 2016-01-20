package cc.chen.mytestapi.test2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;
import cc.chen.mytestapi.logger.Log;

@SuppressLint("InlinedApi")
public class BasicImmersiveModeTest extends BaseActivity {
	public static final String FRAGTAG = "BasicImmersiveModeFragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_immersive_mode_main);

		if (getSupportFragmentManager().findFragmentByTag(FRAGTAG) == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			BasicImmersiveModeFragment fragment = new BasicImmersiveModeFragment();
			transaction.add(fragment, FRAGTAG);
			transaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.basic_immersive_mode_menu, menu);
		return true;
	}

	public class BasicImmersiveModeFragment extends Fragment {

		public static final String TAG = "BasicImmersiveModeFragment";

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			final View decorView = getActivity().getWindow().getDecorView();
			decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
				@Override
				public void onSystemUiVisibilityChange(int i) {
					int height = decorView.getHeight();
					Log.i(TAG, "Current height: " + height);
				}
			});
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == R.id.sample_action) {
				toggleHideyBar();
			}
			return true;
		}

		/**
		 * Detects and toggles immersive mode.
		 */
		public void toggleHideyBar() {
			// BEGIN_INCLUDE (get_current_ui_flags)
			// The UI options currently enabled are represented by a bitfield.
			// getSystemUiVisibility() gives us that bitfield.
			int uiOptions = getActivity().getWindow().getDecorView().getSystemUiVisibility();
			int newUiOptions = uiOptions;
			// END_INCLUDE (get_current_ui_flags)
			// BEGIN_INCLUDE (toggle_ui_flags)
			boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
			if (isImmersiveModeEnabled) {
				Log.i(TAG, "Turning immersive mode mode off. ");
			} else {
				Log.i(TAG, "Turning immersive mode mode on.");
			}

			// Immersive mode: Backward compatible to KitKat (API 19).
			// Note that this flag doesn't do anything by itself, it only
			// augments the behavior
			// of HIDE_NAVIGATION and FLAG_FULLSCREEN. For the purposes of this
			// sample
			// all three flags are being toggled together.
			// This sample uses the "sticky" form of immersive mode, which will
			// let the user swipe
			// the bars back in again, but will automatically make them
			// disappear a few seconds later.
			newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
			newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
			newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			getActivity().getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
			// END_INCLUDE (set_ui_flags)
		}
	}
}
