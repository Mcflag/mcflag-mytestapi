package cc.chen.mytestapi.test2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewAnimator;
import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;
import cc.chen.mytestapi.logger.Log;
import cc.chen.mytestapi.view.SlidingTabLayout;

public class SlidingTabsBasicTest extends BaseActivity {
	// Whether the Log Fragment is currently shown
	private boolean mLogShown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_tabs_basic_main);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
			transaction.replace(R.id.sample_content_fragment, fragment);
			transaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sliding_tabs_basic_menu, menu);
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

	public class SlidingTabsBasicFragment extends Fragment {

		static final String LOG_TAG = "SlidingTabsBasicFragment";

		/**
		 * A custom {@link ViewPager} title strip which looks much like Tabs
		 * present in Android v4.0 and above, but is designed to give continuous
		 * feedback to the user when scrolling.
		 */
		private SlidingTabLayout mSlidingTabLayout;

		/**
		 * A {@link ViewPager} which will be used in conjunction with the
		 * {@link SlidingTabLayout} above.
		 */
		private ViewPager mViewPager;

		/**
		 * Inflates the {@link View} which will be displayed by this
		 * {@link Fragment}, from the app's resources.
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.sliding_tabs_basic_fragment_sample, container, false);
		}

		// BEGIN_INCLUDE (fragment_onviewcreated)
		/**
		 * This is called after the
		 * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has
		 * finished. Here we can pick out the {@link View}s we need to configure
		 * from the content view.
		 * 
		 * We set the {@link ViewPager}'s adapter to be an instance of
		 * {@link SamplePagerAdapter}. The {@link SlidingTabLayout} is then
		 * given the {@link ViewPager} so that it can populate itself.
		 * 
		 * @param view
		 *            View created in
		 *            {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
		 */
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			// BEGIN_INCLUDE (setup_viewpager)
			// Get the ViewPager and set it's PagerAdapter so that it can
			// display items
			mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
			mViewPager.setAdapter(new SamplePagerAdapter());
			// END_INCLUDE (setup_viewpager)

			// BEGIN_INCLUDE (setup_slidingtablayout)
			// Give the SlidingTabLayout the ViewPager, this must be done AFTER
			// the ViewPager has had
			// it's PagerAdapter set.
			mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
			mSlidingTabLayout.setViewPager(mViewPager);
			// END_INCLUDE (setup_slidingtablayout)
		}

		// END_INCLUDE (fragment_onviewcreated)

		/**
		 * The {@link android.support.v4.view.PagerAdapter} used to display
		 * pages in this sample. The individual pages are simple and just
		 * display two lines of text. The important section of this class is the
		 * {@link #getPageTitle(int)} method which controls what is displayed in
		 * the {@link SlidingTabLayout}.
		 */
		class SamplePagerAdapter extends PagerAdapter {

			/**
			 * @return the number of pages to display
			 */
			@Override
			public int getCount() {
				return 10;
			}

			/**
			 * @return true if the value returned from
			 *         {@link #instantiateItem(ViewGroup, int)} is the same
			 *         object as the {@link View} added to the {@link ViewPager}
			 *         .
			 */
			@Override
			public boolean isViewFromObject(View view, Object o) {
				return o == view;
			}

			// BEGIN_INCLUDE (pageradapter_getpagetitle)
			/**
			 * Return the title of the item at {@code position}. This is
			 * important as what this method returns is what is displayed in the
			 * {@link SlidingTabLayout}.
			 * <p>
			 * Here we construct one using the position value, but for real
			 * application the title should refer to the item's contents.
			 */
			@Override
			public CharSequence getPageTitle(int position) {
				return "Item " + (position + 1);
			}

			// END_INCLUDE (pageradapter_getpagetitle)

			/**
			 * Instantiate the {@link View} which should be displayed at
			 * {@code position}. Here we inflate a layout from the apps
			 * resources and then change the text view to signify the position.
			 */
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// Inflate a new layout from our resources
				View view = getActivity().getLayoutInflater().inflate(R.layout.sliding_tabs_basic_pager_item,
						container, false);
				// Add the newly created View to the ViewPager
				container.addView(view);

				// Retrieve a TextView from the inflated View, and update it's
				// text
				TextView title = (TextView) view.findViewById(R.id.item_title);
				title.setText(String.valueOf(position + 1));

				Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");

				// Return the View
				return view;
			}

			/**
			 * Destroy the item from the {@link ViewPager}. In our case this is
			 * simply removing the {@link View}.
			 */
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
				Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
			}

		}
	}
}
