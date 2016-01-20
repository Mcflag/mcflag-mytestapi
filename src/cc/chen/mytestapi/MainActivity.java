package cc.chen.mytestapi;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class MainActivity extends Activity {

	private ExpandableListView mainlistview = null;
	private List<String> parentList = null;
	private Map<String, List<String>> mDataSet = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainlistview = (ExpandableListView) this.findViewById(R.id.main_expandablelistview);

		parentList = ParentAndChildrenNames.getParentNames();
		mDataSet = ParentAndChildrenNames.getMap();

		mainlistview.setAdapter(new MyAdapter(mDataSet));
		mainlistview.setGroupIndicator(null);

		mainlistview.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return true;
			}
		});
		for (int i = 0; i < parentList.size(); i++) {
			mainlistview.expandGroup(i);
		}

		mainlistview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				String key = parentList.get(groupPosition);
				String className = "cc.chen.mytestapi.test" + groupPosition + "." + mDataSet.get(key).get(childPosition);

				try {
					Intent intent = new Intent(MainActivity.this, Class.forName(className));
					startActivity(intent);
				} catch (ClassNotFoundException e) {
				}
				return false;
			}
		});

	}

	class MyAdapter extends BaseExpandableListAdapter {

		Map<String, List<String>> map;

		public MyAdapter(Map<String, List<String>> map) {
			this.map = map;
		}

		// 得到子item需要关联的数据
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			String key = parentList.get(groupPosition);
			return (map.get(key).get(childPosition));
		}

		// 得到子item的ID
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		// 设置子item的组件
		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			String key = MainActivity.this.parentList.get(groupPosition);
			String info = map.get(key).get(childPosition);
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.layout_children, null);
			}

			TextView tv = (TextView) convertView.findViewById(R.id.second_textview);
			tv.setText("        " + info);
			return tv;
		}

		// 获取当前父item下的子item的个数
		@Override
		public int getChildrenCount(int groupPosition) {
			String key = parentList.get(groupPosition);
			int size = 0;
			if (map.get(key) != null) {
				size = map.get(key).size();
			}
			return size;
		}

		// 获取当前父item的数据
		@Override
		public Object getGroup(int groupPosition) {
			return parentList.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return parentList.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		// 设置父item组件
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.layout_parent, null);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.parent_textview);
			tv.setText(MainActivity.this.parentList.get(groupPosition));
			return tv;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

}
