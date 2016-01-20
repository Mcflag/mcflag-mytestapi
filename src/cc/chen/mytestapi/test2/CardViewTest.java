package cc.chen.mytestapi.test2;

import cc.chen.mytestapi.R;
import cc.chen.mytestapi.logger.Log;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

@SuppressLint("NewApi")
public class CardViewTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_view_test_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, CardViewFragment.newInstance()).commit();
		}
	}

	public static class CardViewFragment extends Fragment {

		private final String TAG = CardViewFragment.class.getSimpleName();

		/** The CardView widget. */
		// @VisibleForTesting
		CardView mCardView;

		/**
		 * SeekBar that changes the cornerRadius attribute for the
		 * {@link #mCardView} widget.
		 */
		// @VisibleForTesting
		SeekBar mRadiusSeekBar;

		/**
		 * SeekBar that changes the Elevation attribute for the
		 * {@link #mCardView} widget.
		 */
		// @VisibleForTesting
		SeekBar mElevationSeekBar;

		/**
		 * Use this factory method to create a new instance of this fragment
		 * using the provided parameters.
		 * 
		 * @return A new instance of fragment NotificationFragment.
		 */
		public static CardViewFragment newInstance() {
			CardViewFragment fragment = new CardViewFragment();
			fragment.setRetainInstance(true);
			return fragment;
		}

		public CardViewFragment() {
			// Required empty public constructor
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			return inflater.inflate(R.layout.card_view_test_fragment, container, false);
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			mCardView = (CardView) view.findViewById(R.id.cardview);
			mRadiusSeekBar = (SeekBar) view.findViewById(R.id.cardview_radius_seekbar);
			mRadiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					Log.d(TAG, String.format("SeekBar Radius progress : %d", progress));
					mCardView.setRadius(progress);
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// Do nothing
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// Do nothing
				}
			});

			mElevationSeekBar = (SeekBar) view.findViewById(R.id.cardview_elevation_seekbar);
			mElevationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					Log.d(TAG, String.format("SeekBar Elevation progress : %d", progress));
					mCardView.setElevation(progress);
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// Do nothing
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// Do nothing
				}
			});
		}
	}
}
