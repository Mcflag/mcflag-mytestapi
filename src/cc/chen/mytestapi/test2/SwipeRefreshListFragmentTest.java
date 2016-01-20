package cc.chen.mytestapi.test2;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ViewAnimator;
import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;
import cc.chen.mytestapi.constants.Cheeses;
import cc.chen.mytestapi.logger.Log;

public class SwipeRefreshListFragmentTest extends BaseActivity {
	// Whether the Log Fragment is currently shown
	private boolean mLogShown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swipe_refresh_list_fragment_main);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			SwipeRefreshListFragmentFragment fragment = new SwipeRefreshListFragmentFragment();
			transaction.replace(R.id.sample_content_fragment, fragment);
			transaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.swipe_refresh_list_fragment_menu, menu);
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

	public class SwipeRefreshListFragmentFragment extends SwipeRefreshListFragment {

		private final String LOG_TAG = SwipeRefreshListFragmentFragment.class.getSimpleName();

		private static final int LIST_ITEM_COUNT = 20;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// Notify the system to allow an options menu for this fragment.
			setHasOptionsMenu(true);
		}

		// BEGIN_INCLUDE (setup_views)
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			/**
			 * Create an ArrayAdapter to contain the data for the ListView. Each
			 * item in the ListView uses the system-defined simple_list_item_1
			 * layout that contains one TextView.
			 */
			ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
					android.R.id.text1, Cheeses.randomList(LIST_ITEM_COUNT));

			// Set the adapter between the ListView and its backing data.
			setListAdapter(adapter);

			// BEGIN_INCLUDE (setup_refreshlistener)
			/**
			 * Implement {@link SwipeRefreshLayout.OnRefreshListener}. When
			 * users do the "swipe to refresh" gesture, SwipeRefreshLayout
			 * invokes {@link SwipeRefreshLayout.OnRefreshListener#onRefresh
			 * onRefresh()}. In
			 * {@link SwipeRefreshLayout.OnRefreshListener#onRefresh
			 * onRefresh()}, call a method that refreshes the content. Call the
			 * same method in response to the Refresh action from the action
			 * bar.
			 */
			setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

					initiateRefresh();
				}
			});
			// END_INCLUDE (setup_refreshlistener)
		}

		// END_INCLUDE (setup_views)

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.swipe_refresh_list_fragment_main_menu, menu);
		}

		// BEGIN_INCLUDE (setup_refresh_menu_listener)
		/**
		 * Respond to the user's selection of the Refresh action item. Start the
		 * SwipeRefreshLayout progress bar, then initiate the background task
		 * that refreshes the content.
		 * 
		 * <p>
		 * A color scheme menu item used for demonstrating the use of
		 * SwipeRefreshLayout's color scheme functionality. This kind of menu
		 * item should not be incorporated into your app, it just to demonstrate
		 * the use of color. Instead you should choose a color scheme based off
		 * of your application's branding.
		 */
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.menu_refresh:
				Log.i(LOG_TAG, "Refresh menu item selected");

				// We make sure that the SwipeRefreshLayout is displaying it's
				// refreshing indicator
				if (!isRefreshing()) {
					setRefreshing(true);
				}

				// Start our refresh background task
				initiateRefresh();
				return true;

			case R.id.menu_color_scheme_1:
				Log.i(LOG_TAG, "setColorScheme #1");
				item.setChecked(true);

				// Change the colors displayed by the SwipeRefreshLayout by
				// providing it with 4
				// color resource ids
				setColorScheme(R.color.color_scheme_1_1, R.color.color_scheme_1_2, R.color.color_scheme_1_3,
						R.color.color_scheme_1_4);
				return true;

			case R.id.menu_color_scheme_2:
				Log.i(LOG_TAG, "setColorScheme #2");
				item.setChecked(true);

				// Change the colors displayed by the SwipeRefreshLayout by
				// providing it with 4
				// color resource ids
				setColorScheme(R.color.color_scheme_2_1, R.color.color_scheme_2_2, R.color.color_scheme_2_3,
						R.color.color_scheme_2_4);
				return true;

			case R.id.menu_color_scheme_3:
				Log.i(LOG_TAG, "setColorScheme #3");
				item.setChecked(true);

				// Change the colors displayed by the SwipeRefreshLayout by
				// providing it with 4
				// color resource ids
				setColorScheme(R.color.color_scheme_3_1, R.color.color_scheme_3_2, R.color.color_scheme_3_3,
						R.color.color_scheme_3_4);
				return true;
			}

			return super.onOptionsItemSelected(item);
		}

		// END_INCLUDE (setup_refresh_menu_listener)

		// BEGIN_INCLUDE (initiate_refresh)
		/**
		 * By abstracting the refresh process to a single method, the app allows
		 * both the SwipeGestureLayout onRefresh() method and the Refresh action
		 * item to refresh the content.
		 */
		private void initiateRefresh() {
			Log.i(LOG_TAG, "initiateRefresh");

			/**
			 * Execute the background task, which uses
			 * {@link android.os.AsyncTask} to load the data.
			 */
			new DummyBackgroundTask().execute();
		}

		// END_INCLUDE (initiate_refresh)

		// BEGIN_INCLUDE (refresh_complete)
		/**
		 * When the AsyncTask finishes, it calls onRefreshComplete(), which
		 * updates the data in the ListAdapter and turns off the progress bar.
		 */
		private void onRefreshComplete(List<String> result) {
			Log.i(LOG_TAG, "onRefreshComplete");

			// Remove all items from the ListAdapter, and then replace them with
			// the new items
			ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
			adapter.clear();
			for (String cheese : result) {
				adapter.add(cheese);
			}

			// Stop the refreshing indicator
			setRefreshing(false);
		}

		// END_INCLUDE (refresh_complete)

		/**
		 * Dummy {@link AsyncTask} which simulates a long running task to fetch
		 * new cheeses.
		 */
		private class DummyBackgroundTask extends AsyncTask<Void, Void, List<String>> {

			static final int TASK_DURATION = 3 * 1000; // 3 seconds

			@Override
			protected List<String> doInBackground(Void... params) {
				// Sleep for a small amount of time to simulate a
				// background-task
				try {
					Thread.sleep(TASK_DURATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Return a new random list of cheeses
				return Cheeses.randomList(LIST_ITEM_COUNT);
			}

			@Override
			protected void onPostExecute(List<String> result) {
				super.onPostExecute(result);

				// Tell the Fragment that the refresh has completed
				onRefreshComplete(result);
			}

		}

	}

	public class SwipeRefreshListFragment extends ListFragment {

		private SwipeRefreshLayout mSwipeRefreshLayout;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			// Create the list fragment's content view by calling the super
			// method
			final View listFragmentView = super.onCreateView(inflater, container, savedInstanceState);

			// Now create a SwipeRefreshLayout to wrap the fragment's content
			// view
			mSwipeRefreshLayout = new ListFragmentSwipeRefreshLayout(container.getContext());

			// Add the list fragment's content view to the SwipeRefreshLayout,
			// making sure that it fills
			// the SwipeRefreshLayout
			mSwipeRefreshLayout.addView(listFragmentView, ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);

			// Make sure that the SwipeRefreshLayout will fill the fragment
			mSwipeRefreshLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));

			// Now return the SwipeRefreshLayout as this fragment's content view
			return mSwipeRefreshLayout;
		}

		/**
		 * Set the
		 * {@link android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener}
		 * to listen for initiated refreshes.
		 * 
		 * @see android.support.v4.widget.SwipeRefreshLayout#setOnRefreshListener(android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener)
		 */
		public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
			mSwipeRefreshLayout.setOnRefreshListener(listener);
		}

		/**
		 * Returns whether the
		 * {@link android.support.v4.widget.SwipeRefreshLayout} is currently
		 * refreshing or not.
		 * 
		 * @see android.support.v4.widget.SwipeRefreshLayout#isRefreshing()
		 */
		public boolean isRefreshing() {
			return mSwipeRefreshLayout.isRefreshing();
		}

		/**
		 * Set whether the {@link android.support.v4.widget.SwipeRefreshLayout}
		 * should be displaying that it is refreshing or not.
		 * 
		 * @see android.support.v4.widget.SwipeRefreshLayout#setRefreshing(boolean)
		 */
		public void setRefreshing(boolean refreshing) {
			mSwipeRefreshLayout.setRefreshing(refreshing);
		}

		/**
		 * Set the color scheme for the
		 * {@link android.support.v4.widget.SwipeRefreshLayout}.
		 * 
		 * @see android.support.v4.widget.SwipeRefreshLayout#setColorScheme(int,
		 *      int, int, int)
		 */
		public void setColorScheme(int colorRes1, int colorRes2, int colorRes3, int colorRes4) {
			mSwipeRefreshLayout.setColorScheme(colorRes1, colorRes2, colorRes3, colorRes4);
		}

		/**
		 * @return the fragment's
		 *         {@link android.support.v4.widget.SwipeRefreshLayout} widget.
		 */
		public SwipeRefreshLayout getSwipeRefreshLayout() {
			return mSwipeRefreshLayout;
		}

		/**
		 * Sub-class of {@link android.support.v4.widget.SwipeRefreshLayout} for
		 * use in this {@link android.support.v4.app.ListFragment}. The reason
		 * that this is needed is because
		 * {@link android.support.v4.widget.SwipeRefreshLayout} only supports a
		 * single child, which it expects to be the one which triggers
		 * refreshes. In our case the layout's child is the content view
		 * returned from
		 * {@link android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
		 * which is a {@link android.view.ViewGroup}.
		 * 
		 * <p>
		 * To enable 'swipe-to-refresh' support via the
		 * {@link android.widget.ListView} we need to override the default
		 * behavior and properly signal when a gesture is possible. This is done
		 * by overriding {@link #canChildScrollUp()}.
		 */
		private class ListFragmentSwipeRefreshLayout extends SwipeRefreshLayout {

			public ListFragmentSwipeRefreshLayout(Context context) {
				super(context);
			}

			/**
			 * As mentioned above, we need to override this method to properly
			 * signal when a 'swipe-to-refresh' is possible.
			 * 
			 * @return true if the {@link android.widget.ListView} is visible
			 *         and can scroll up.
			 */
			@Override
			public boolean canChildScrollUp() {
				final ListView listView = getListView();
				if (listView.getVisibility() == View.VISIBLE) {
					return canListViewScrollUp(listView);
				} else {
					return false;
				}
			}

		}

		// BEGIN_INCLUDE (check_list_can_scroll)
		/**
		 * Utility method to check whether a {@link ListView} can scroll up from
		 * it's current position. Handles platform version differences,
		 * providing backwards compatible functionality where needed.
		 */
		private boolean canListViewScrollUp(ListView listView) {
			if (android.os.Build.VERSION.SDK_INT >= 14) {
				// For ICS and above we can call canScrollVertically() to
				// determine this
				return ViewCompat.canScrollVertically(listView, -1);
			} else {
				// Pre-ICS we need to manually check the first visible item and
				// the child view's top
				// value
				return listView.getChildCount() > 0
						&& (listView.getFirstVisiblePosition() > 0 || listView.getChildAt(0).getTop() < listView
								.getPaddingTop());
			}
		}
		// END_INCLUDE (check_list_can_scroll)

	}

}
