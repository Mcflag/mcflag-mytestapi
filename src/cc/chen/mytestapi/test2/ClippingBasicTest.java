package cc.chen.mytestapi.test2;

import android.annotation.SuppressLint;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewAnimator;
import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;
import cc.chen.mytestapi.logger.Log;

@SuppressLint("NewApi")
public class ClippingBasicTest extends BaseActivity {
	// Whether the Log Fragment is currently shown
	private boolean mLogShown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clipping_basic_main);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			ClippingBasicFragment fragment = new ClippingBasicFragment();
			transaction.replace(R.id.sample_content_fragment, fragment);
			transaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.clipping_basic_menu, menu);
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

	public class ClippingBasicFragment extends Fragment {

		private final static String TAG = "ClippingBasicFragment";

		/*
		 * Store the click count so that we can show a different text on every
		 * click.
		 */
		private int mClickCount = 0;

		/* The {@Link Outline} used to clip the image with. */
		private ViewOutlineProvider mOutlineProvider;

		/* An array of texts. */
		private String[] mSampleTexts;

		/*
		 * A reference to a {@Link TextView} that shows different text strings
		 * when clicked.
		 */
		private TextView mTextView;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
			mOutlineProvider = new ClipOutlineProvider();
			mSampleTexts = getResources().getStringArray(R.array.sample_texts);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.clipping_basic_fragment, container, false);
		}

		@Override
		public void onViewCreated(final View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			/* Set the initial text for the TextView. */
			mTextView = (TextView) view.findViewById(R.id.text_view);
			changeText();

			final View clippedView = view.findViewById(R.id.frame);

			/* Sets the OutlineProvider for the View. */
			clippedView.setOutlineProvider(mOutlineProvider);

			/* When the button is clicked, the text is clipped or un-clipped. */
			view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View bt) {
					// Toggle whether the View is clipped to the outline
					if (clippedView.getClipToOutline()) {
						/*
						 * The Outline is set for the View, but disable
						 * clipping.
						 */
						clippedView.setClipToOutline(false);

						Log.d(TAG, String.format("Clipping to outline is disabled"));
						((Button) bt).setText(R.string.clip_button);
					} else {
						/* Enables clipping on the View. */
						clippedView.setClipToOutline(true);

						Log.d(TAG, String.format("Clipping to outline is enabled"));
						((Button) bt).setText(R.string.unclip_button);
					}
				}
			});

			/* When the text is clicked, a new string is shown. */
			view.findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mClickCount++;

					// Update the text in the TextView
					changeText();

					// Invalidate the outline just in case the TextView changed
					// size
					clippedView.invalidateOutline();
				}
			});
		}

		private void changeText() {
			// Compute the position of the string in the array using the number
			// of strings
			// and the number of clicks.
			String newText = mSampleTexts[mClickCount % mSampleTexts.length];

			/* Once the text is selected, change the TextView */
			mTextView.setText(newText);
			Log.d(TAG, String.format("Text was changed."));

		}

		/**
		 * A {@link ViewOutlineProvider} which clips the view with a rounded
		 * rectangle which is inset by 10%
		 */
		private class ClipOutlineProvider extends ViewOutlineProvider {

			@Override
			public void getOutline(View view, Outline outline) {
				final int margin = Math.min(view.getWidth(), view.getHeight()) / 10;
				outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, margin / 2);
			}

		}
	}
}
