package cc.chen.mytestapi.test1;

import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;
import cc.chen.mytestapi.logger.Log;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlarmManagerTest extends BaseActivity {

	private final static int FIFTEEN_SEC = 15000;

	private Context context;

	private Button setButton;
	private Button disButton;

	private int alarmType = AlarmManager.ELAPSED_REALTIME;
	private AlarmManager alarmManager;
	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_manager_test_main);
		context = this;

		Intent intent = new Intent(context, AlarmManagerTest.class);
		intent.setAction(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

		pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

		alarmType = AlarmManager.ELAPSED_REALTIME;

		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		setButton = (Button) findViewById(R.id.set_alarm_button);
		setButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alarmManager.setRepeating(alarmType, SystemClock.elapsedRealtime() + FIFTEEN_SEC, FIFTEEN_SEC, pendingIntent);
				Log.e(TAG, "Set Alarm!");
			}
		});

		disButton = (Button) findViewById(R.id.dis_alarm_button);
		disButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alarmManager.cancel(pendingIntent);
				Log.e(TAG, "Disable Alarm!");
			}
		});

	}
}
