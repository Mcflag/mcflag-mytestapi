package cc.chen.mytestapi.test1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;
import cc.chen.mytestapi.logger.Log;

@SuppressLint("ClickableViewAccessibility")
public class BasicGestureDetect extends BaseActivity {

	private Button clearButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_gesture_detect_main);

		clearButton = (Button) findViewById(R.id.clear_log);
		clearButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearLog();
			}
		});

		View gestureView = findViewById(R.id.sample_output);
		gestureView.setClickable(true);
		gestureView.setFocusable(true);

		GestureDetector.SimpleOnGestureListener gestureListener = new GestureListener();
		final GestureDetector gd = new GestureDetector(this, gestureListener);

		gestureView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				gd.onTouchEvent(motionEvent);
				return false;
			}
		});

	}

	public void clearLog() {
		logFragment.getLogView().setText("");
	}

	public class GestureListener extends GestureDetector.SimpleOnGestureListener {

		public static final String TAG = "GestureListener";

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.i(TAG, "Single Tap Up");
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			Log.i(TAG, "Long Press");
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			Log.i(TAG, "Scroll");
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.i(TAG, "Fling");
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			Log.i(TAG, "Show Press");
		}

		@Override
		public boolean onDown(MotionEvent e) {
			Log.i(TAG, "Down");
			return false;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.i(TAG, "Double tap");
			return false;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			Log.i(TAG, "Event within double tap");
			return false;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			Log.i(TAG, "Single tap confirmed");
			return false;
		}
	}
}
