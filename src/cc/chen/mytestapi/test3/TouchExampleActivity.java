/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.chen.mytestapi.test3;

import cc.chen.mytestapi.R;
import cc.chen.mytestapi.widgets.VersionedGestureDetector;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

public class TouchExampleActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TouchExampleView view = new TouchExampleView(this);
		view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		setContentView(view);
	}

	public class TouchExampleView extends View {
		private Drawable mIcon;
		private float mPosX;
		private float mPosY;

		private VersionedGestureDetector mDetector;
		private float mScaleFactor = 1.f;

		public TouchExampleView(Context context) {
			this(context, null, 0);
		}

		public TouchExampleView(Context context, AttributeSet attrs) {
			this(context, attrs, 0);
		}

		public TouchExampleView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			mIcon = context.getResources().getDrawable(R.drawable.ic_launcher);
			mIcon.setBounds(0, 0, mIcon.getIntrinsicWidth(), mIcon.getIntrinsicHeight());

			mDetector = VersionedGestureDetector.newInstance(context, new GestureCallback());
		}

		@Override
		public boolean onTouchEvent(MotionEvent ev) {
			mDetector.onTouchEvent(ev);
			return true;
		}

		@Override
		public void onDraw(Canvas canvas) {
			super.onDraw(canvas);

			canvas.save();
			canvas.translate(mPosX, mPosY);
			canvas.scale(mScaleFactor, mScaleFactor);
			mIcon.draw(canvas);
			canvas.restore();
		}

		private class GestureCallback implements VersionedGestureDetector.OnGestureListener {
			public void onDrag(float dx, float dy) {
				mPosX += dx;
				mPosY += dy;
				invalidate();
			}

			public void onScale(float scaleFactor) {
				mScaleFactor *= scaleFactor;

				// Don't let the object get too small or too large.
				mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

				invalidate();
			}
		}
	}
}
