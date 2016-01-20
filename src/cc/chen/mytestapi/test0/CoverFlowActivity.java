package cc.chen.mytestapi.test0;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import cc.chen.mytestapi.R;
import cc.chen.mytestapi.coverflowlib.FeatureCoverFlow;

public class CoverFlowActivity extends Activity {

	private FeatureCoverFlow mCoverFlow;
	private CoverFlowAdapter mAdapter;
	private ArrayList<GameEntity> mData = new ArrayList<>(0);
	private TextSwitcher mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coverflow_activity_main);

		mData.add(new GameEntity(R.drawable.image_1, R.string.title1));
		mData.add(new GameEntity(R.drawable.image_2, R.string.title2));
		mData.add(new GameEntity(R.drawable.image_3, R.string.title3));
		mData.add(new GameEntity(R.drawable.image_4, R.string.title4));

		mTitle = (TextSwitcher) findViewById(R.id.title);
		mTitle.setFactory(new ViewSwitcher.ViewFactory() {
			@Override
			public View makeView() {
				LayoutInflater inflater = LayoutInflater.from(CoverFlowActivity.this);
				TextView textView = (TextView) inflater.inflate(R.layout.coverflow_item_title, null);
				return textView;
			}
		});
		Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
		Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
		mTitle.setInAnimation(in);
		mTitle.setOutAnimation(out);

		mAdapter = new CoverFlowAdapter(this);
		mAdapter.setData(mData);
		mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
		mCoverFlow.setAdapter(mAdapter);

		mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(CoverFlowActivity.this, getResources().getString(mData.get(position).titleResId),
						Toast.LENGTH_SHORT).show();
			}
		});

		mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
			@Override
			public void onScrolledToPosition(int position) {
				mTitle.setText(getResources().getString(mData.get(position).titleResId));
			}

			@Override
			public void onScrolling() {
				mTitle.setText("");
			}
		});

	}

	public class CoverFlowAdapter extends BaseAdapter {

		private ArrayList<GameEntity> mData = new ArrayList<>(0);
		private Context mContext;

		public CoverFlowAdapter(Context context) {
			mContext = context;
		}

		public void setData(ArrayList<GameEntity> data) {
			mData = data;
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int pos) {
			return mData.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View rowView = convertView;

			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.coverflow_item, null);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.text = (TextView) rowView.findViewById(R.id.label);
				viewHolder.image = (ImageView) rowView.findViewById(R.id.image);
				rowView.setTag(viewHolder);
			}

			ViewHolder holder = (ViewHolder) rowView.getTag();

			holder.image.setImageResource(mData.get(position).imageResId);
			holder.text.setText(mData.get(position).titleResId);

			return rowView;
		}

		class ViewHolder {
			public TextView text;
			public ImageView image;
		}
	}

	public class GameEntity {
		public int imageResId;
		public int titleResId;

		public GameEntity(int imageResId, int titleResId) {
			this.imageResId = imageResId;
			this.titleResId = titleResId;
		}
	}
}
