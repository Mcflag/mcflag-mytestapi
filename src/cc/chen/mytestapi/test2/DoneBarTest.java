package cc.chen.mytestapi.test2;

import cc.chen.mytestapi.R;
import cc.chen.mytestapi.activities.DoneBarActivity;
import cc.chen.mytestapi.activities.DoneButtonActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class DoneBarTest extends Activity implements AdapterView.OnItemClickListener {
	private Sample[] mSamples;
	private GridView mGridView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.done_bar_main);

		// Prepare list of samples in this dashboard.
		mSamples = new Sample[] {
				new Sample(R.string.donebaractivity_title, R.string.donebaractivity_description, DoneBarActivity.class),
				new Sample(R.string.donebuttonactivity_title, R.string.donebuttonactivity_description,
						DoneButtonActivity.class), };

		// Prepare the GridView
		mGridView = (GridView) findViewById(android.R.id.list);
		mGridView.setAdapter(new SampleAdapter());
		mGridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> container, View view, int position, long id) {
		startActivity(mSamples[position].intent);
	}

	private class SampleAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return mSamples.length;
		}

		@Override
		public Object getItem(int position) {
			return mSamples[position];
		}

		@Override
		public long getItemId(int position) {
			return mSamples[position].hashCode();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup container) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.sample_dashboard_item, container, false);
			}

			((TextView) convertView.findViewById(android.R.id.text1)).setText(mSamples[position].titleResId);
			((TextView) convertView.findViewById(android.R.id.text2)).setText(mSamples[position].descriptionResId);
			return convertView;
		}
	}

	private class Sample {
		int titleResId;
		int descriptionResId;
		Intent intent;

		private Sample(int titleResId, int descriptionResId, Intent intent) {
			this.intent = intent;
			this.titleResId = titleResId;
			this.descriptionResId = descriptionResId;
		}

		private Sample(int titleResId, int descriptionResId, Class<? extends Activity> activityClass) {
			this(titleResId, descriptionResId, new Intent(DoneBarTest.this, activityClass));
		}
	}
}
