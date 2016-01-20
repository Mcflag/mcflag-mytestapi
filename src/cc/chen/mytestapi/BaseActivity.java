package cc.chen.mytestapi;

import cc.chen.mytestapi.logger.Log;
import cc.chen.mytestapi.logger.LogFragment;
import cc.chen.mytestapi.logger.LogWrapper;
import cc.chen.mytestapi.logger.MessageOnlyLogFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	public static final String TAG = "BaseActivity";

	protected LogWrapper logWrapper;
	protected MessageOnlyLogFilter msgFilter;
	protected LogFragment logFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		initializeLogging();
	}

	public void initializeLogging() {
		logWrapper = new LogWrapper();
		Log.setLogNode(logWrapper);

		msgFilter = new MessageOnlyLogFilter();
		logWrapper.setNext(msgFilter);
		try {
			logFragment = (LogFragment) getSupportFragmentManager().findFragmentById(R.id.log_fragment);
			msgFilter.setNext(logFragment.getLogView());
			logFragment.getLogView().setTextAppearance(this, R.style.Log);
			logFragment.getLogView().setBackgroundColor(Color.WHITE);

			Log.i(TAG, "Ready");
		} catch (Exception e) {
		}

	}

}
