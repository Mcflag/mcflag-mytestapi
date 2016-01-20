package cc.chen.mytestapi.test2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;
import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;
import cc.chen.mytestapi.logger.Log;
import cc.chen.mytestapi.view.FloatingActionButton;

public class FloatingActionButtonBasicTest extends BaseActivity {
	// Whether the Log Fragment is currently shown
	private boolean mLogShown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.floating_action_button_basic_main);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			FloatingActionButtonBasicFragment fragment = new FloatingActionButtonBasicFragment();
			transaction.replace(R.id.sample_content_fragment, fragment);
			transaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.floating_action_button_basic_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
		logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
		logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_toggle_log:
			mLogShown = !mLogShown;
			ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
			if (mLogShown) {
				output.setDisplayedChild(1);
			} else {
				output.setDisplayedChild(0);
			}
			supportInvalidateOptionsMenu();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class FloatingActionButtonBasicFragment extends Fragment implements
			FloatingActionButton.OnCheckedChangeListener {

		private final static String TAG = "FloatingActionButtonBasicFragment";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			View rootView = inflater.inflate(R.layout.floating_action_button_basic_fab_layout, container, false);

			// Make this {@link Fragment} listen for changes in both FABs.
			FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab_1);
			fab1.setOnCheckedChangeListener(this);
			FloatingActionButton fab2 = (FloatingActionButton) rootView.findViewById(R.id.fab_2);
			fab2.setOnCheckedChangeListener(this);
			return rootView;
		}

		@Override
		public void onCheckedChanged(FloatingActionButton fabView, boolean isChecked) {
			// When a FAB is toggled, log the action.
			switch (fabView.getId()) {
			case R.id.fab_1:
				Log.d(TAG, String.format("FAB 1 was %s.", isChecked ? "checked" : "unchecked"));
				break;
			case R.id.fab_2:
				Log.d(TAG, String.format("FAB 2 was %s.", isChecked ? "checked" : "unchecked"));
				break;
			default:
				break;
			}
		}
	}
}
