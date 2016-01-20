package cc.chen.mytestapi.test2;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
import cc.chen.mytestapi.view.SlidingTabLayout;

public class SlidingTabsColorsTest extends BaseActivity {
	// Whether the Log Fragment is currently shown
	private boolean mLogShown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_tabs_colors_main);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			SlidingTabsColorsFragment fragment = new SlidingTabsColorsFragment();
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

	public class SlidingTabsColorsFragment extends Fragment {

		/**
		 * This class represents a tab to be displayed by {@link ViewPager} and
		 * it's associated {@link SlidingTabLayout}.
		 */
		class SamplePagerItem {
			private final CharSequence mTitle;
			private final int mIndicatorColor;
			private final int mDividerColor;

			SamplePagerItem(CharSequence title, int indicatorColor, int dividerColor) {
				mTitle = title;
				mIndicatorColor = indicatorColor;
				mDividerColor = dividerColor;
			}

			/**
			 * @return A new {@link Fragment} to be displayed by a
			 *         {@link ViewPager}
			 */
			Fragment createFragment() {
				return ContentFragment.newInstance(mTitle, mIndicatorColor, mDividerColor);
			}

			/**
			 * @return the title which represents this tab. In this sample this
			 *         is used directly by
			 *         {@link android.support.v4.view.PagerAdapter#getPageTitle(int)}
			 */
			CharSequence getTitle() {
				return mTitle;
			}

			/**
			 * @return the color to be used for indicator on the
			 *         {@link SlidingTabLayout}
			 */
			int getIndicatorColor() {
				return mIndicatorColor;
			}

			/**
			 * @return the color to be used for right divider on the
			 *         {@link SlidingTabLayout}
			 */
			int getDividerColor() {
				return mDividerColor;
			}
		}

		static final String LOG_TAG = "SlidingTabsColorsFragment";

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
		 * List of {@link SamplePagerItem} which represent this sample's tabs.
		 */
		private List<SamplePagerItem> mTabs = new ArrayList<SamplePagerItem>();

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// BEGIN_INCLUDE (populate_tabs)
			/**
			 * Populate our tab list with tabs. Each item contains a title,
			 * indicator color and divider color, which are used by
			 * {@link SlidingTabLayout}.
			 */
			mTabs.add(new SamplePagerItem(getString(R.string.tab_stream), // Title
					Color.BLUE, // Indicator color
					Color.GRAY // Divider color
			));

			mTabs.add(new SamplePagerItem(getString(R.string.tab_messages), // Title
					Color.RED, // Indicator color
					Color.GRAY // Divider color
			));

			mTabs.add(new SamplePagerItem(getString(R.string.tab_photos), // Title
					Color.YELLOW, // Indicator color
					Color.GRAY // Divider color
			));

			mTabs.add(new SamplePagerItem(getString(R.string.tab_notifications), // Title
					Color.GREEN, // Indicator color
					Color.GRAY // Divider color
			));
			// END_INCLUDE (populate_tabs)
		}

		/**
		 * Inflates the {@link View} which will be displayed by this
		 * {@link Fragment}, from the app's resources.
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.sliding_tabs_colors_fragment_sample, container, false);
		}

		// BEGIN_INCLUDE (fragment_onviewcreated)
		/**
		 * This is called after the
		 * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has
		 * finished. Here we can pick out the {@link View}s we need to configure
		 * from the content view.
		 * 
		 * We set the {@link ViewPager}'s adapter to be an instance of
		 * {@link SampleFragmentPagerAdapter}. The {@link SlidingTabLayout} is
		 * then given the {@link ViewPager} so that it can populate itself.
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
			mViewPager.setAdapter(new SampleFragmentPagerAdapter(getChildFragmentManager()));
			// END_INCLUDE (setup_viewpager)

			// BEGIN_INCLUDE (setup_slidingtablayout)
			// Give the SlidingTabLayout the ViewPager, this must be done AFTER
			// the ViewPager has had
			// it's PagerAdapter set.
			mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
			mSlidingTabLayout.setViewPager(mViewPager);

			// BEGIN_INCLUDE (tab_colorizer)
			// Set a TabColorizer to customize the indicator and divider colors.
			// Here we just retrieve
			// the tab at the position, and return it's set color
			mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

				@Override
				public int getIndicatorColor(int position) {
					return mTabs.get(position).getIndicatorColor();
				}

				@Override
				public int getDividerColor(int position) {
					return mTabs.get(position).getDividerColor();
				}

			});
			// END_INCLUDE (tab_colorizer)
			// END_INCLUDE (setup_slidingtablayout)
		}

		// END_INCLUDE (fragment_onviewcreated)

		/**
		 * The {@link FragmentPagerAdapter} used to display pages in this
		 * sample. The individual pages are instances of {@link ContentFragment}
		 * which just display three lines of text. Each page is created by the
		 * relevant {@link SamplePagerItem} for the requested position.
		 * <p>
		 * The important section of this class is the {@link #getPageTitle(int)}
		 * method which controls what is displayed in the
		 * {@link SlidingTabLayout}.
		 */
		class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

			SampleFragmentPagerAdapter(FragmentManager fm) {
				super(fm);
			}

			/**
			 * Return the {@link android.support.v4.app.Fragment} to be
			 * displayed at {@code position}.
			 * <p>
			 * Here we return the value returned from
			 * {@link SamplePagerItem#createFragment()}.
			 */
			@Override
			public Fragment getItem(int i) {
				return mTabs.get(i).createFragment();
			}

			@Override
			public int getCount() {
				return mTabs.size();
			}

			// BEGIN_INCLUDE (pageradapter_getpagetitle)
			/**
			 * Return the title of the item at {@code position}. This is
			 * important as what this method returns is what is displayed in the
			 * {@link SlidingTabLayout}.
			 * <p>
			 * Here we return the value returned from
			 * {@link SamplePagerItem#getTitle()}.
			 */
			@Override
			public CharSequence getPageTitle(int position) {
				return mTabs.get(position).getTitle();
			}
			// END_INCLUDE (pageradapter_getpagetitle)

		}

	}

	public static class ContentFragment extends Fragment {

		private static final String KEY_TITLE = "title";
		private static final String KEY_INDICATOR_COLOR = "indicator_color";
		private static final String KEY_DIVIDER_COLOR = "divider_color";

		/**
		 * @return a new instance of {@link ContentFragment}, adding the
		 *         parameters into a bundle and setting them as arguments.
		 */
		public static ContentFragment newInstance(CharSequence title, int indicatorColor, int dividerColor) {
			Bundle bundle = new Bundle();
			bundle.putCharSequence(KEY_TITLE, title);
			bundle.putInt(KEY_INDICATOR_COLOR, indicatorColor);
			bundle.putInt(KEY_DIVIDER_COLOR, dividerColor);

			ContentFragment fragment = new ContentFragment();
			fragment.setArguments(bundle);

			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.sliding_tabs_colors_pager_item, container, false);
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			Bundle args = getArguments();

			if (args != null) {
				TextView title = (TextView) view.findViewById(R.id.item_title);
				title.setText("Title: " + args.getCharSequence(KEY_TITLE));

				int indicatorColor = args.getInt(KEY_INDICATOR_COLOR);
				TextView indicatorColorView = (TextView) view.findViewById(R.id.item_indicator_color);
				indicatorColorView.setText("Indicator: #" + Integer.toHexString(indicatorColor));
				indicatorColorView.setTextColor(indicatorColor);

				int dividerColor = args.getInt(KEY_DIVIDER_COLOR);
				TextView dividerColorView = (TextView) view.findViewById(R.id.item_divider_color);
				dividerColorView.setText("Divider: #" + Integer.toHexString(dividerColor));
				dividerColorView.setTextColor(dividerColor);
			}
		}
	}
}
